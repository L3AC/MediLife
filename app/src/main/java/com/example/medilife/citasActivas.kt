package com.example.medilife

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

lateinit var miRecyclerView2: RecyclerView
lateinit var volver2:ImageButton
class fila(
    val id: Int
)

val reg = mutableListOf<fila>()
val myData = mutableListOf<String>()

class citasActivas : Fragment() {
    var idCuenta: Int = 0
    var nivelC: Int = 0
    var idCita: Int = 0
    private var conx = Conx()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idCuenta = arguments?.getInt("idcu")!!
            nivelC = arguments?.getInt("nvc")!!
            Log.i("id", idCuenta.toString())
            Log.i("nivel ", nivelC.toString())
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_citas_activas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        miRecyclerView2 = requireView().findViewById(R.id.rcView)
        miRecyclerView2.layoutManager = LinearLayoutManager(context)
        if (nivelC == 1) {
            CargarDatosDoc()
        }
        if (nivelC == 2) {
            CargarDatosSec()
        }
        if (nivelC == 3) {
            CargarDatosCl()
        }

        miRecyclerView2.addOnItemTouchListener(
            historialCitas.RecyclerItemClickListener(requireContext(), miRecyclerView2,
                object : historialCitas.RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        // Acciones a realizar cuando se hace clic en un elemento del RecyclerView
                        val itm = reg[position]
                        idCita = itm.id
                        var bundle = Bundle().apply {
                            putInt("idcu", idCuenta)
                            putInt("idcita", idCita)
                            putInt("nvc", nivelC)
                        }

                        findNavController().navigate(R.id.action_citasActivas_to_infoCita, bundle)
                    }
                })
        )/////////////////
        val miAdapter = historialCitas.misCard(myData)
        miRecyclerView2.adapter = miAdapter

        volver2 = requireView().findViewById(R.id.btnVolver6)


        volver2.setOnClickListener {
            val bundle = Bundle().apply {
                putInt("idcu", idCuenta)
                putInt("nvc", nivelC)
            }
            if (nivelC == 1) {
                findNavController().navigate(R.id.action_citasActivas_to_homeDoctor, bundle)
            }
            if (nivelC == 2) {
                findNavController().navigate(R.id.action_citasActivas_to_homeSecretaria, bundle)
            }
            if (nivelC == 3) {
                findNavController().navigate(R.id.action_citasActivas_to_homeCliente, bundle)
            }

        }
    }
    fun CargarDatosDoc() {
        myData.clear()
        reg.clear()
        try {

            var st: ResultSet
            var cadena: String =
                "select idCita,FORMAT(fechahora,'dd-MM-yyyy') AS fecha,FORMAT(fechahora,'hh:mm tt') " +
                        "as hora,CONCAT(nombres,' ',apellidos) as paciente" +
                        ",estado from tbCitas ci,tbClientes c where ci.idCliente=c.idCliente " +
                        "and estado='Pendiente' and idDoctor=?;"

            val ps: PreparedStatement = conx.dbConn()?.prepareStatement(cadena)!!
            ps.setInt(1, idCuenta)
            st = ps.executeQuery()

            while (st?.next() == true) {

                val col1 = st.getInt("idCita")
                val col2 = st.getString("fecha")
                val col3 = st.getString("hora")
                val col4 = st.getString("paciente")
                val col5 = st.getString("estado")

                reg.add(fila(col1))

                val newElement = "Fecha: $col2  Hora: $col3  Paciente: $col4  Estado: $col5"

                myData.add(newElement)
            }
        } catch (ex: SQLException) {
            Toast.makeText(context, "Error al mostrar", Toast.LENGTH_SHORT).show()
        }
    }
    fun CargarDatosSec() {
        myData.clear()
        reg.clear()
        try {

            var st: ResultSet
            var cadena: String =
                "select idCita,FORMAT(fechahora,'dd-MM-yyyy') AS fecha,FORMAT(fechahora,'hh:mm tt') " +
                        "as hora,CONCAT(nombres,' ',apellidos) as paciente" +
                        ",estado from tbCitas ci,tbClientes c where ci.idCliente=c.idCliente " +
                        "and estado='Pendiente' ;"

            val ps: PreparedStatement = conx.dbConn()?.prepareStatement(cadena)!!
            st = ps.executeQuery()

            while (st?.next() == true) {

                val col1 = st.getInt("idCita")
                val col2 = st.getString("fecha")
                val col3 = st.getString("hora")
                val col4 = st.getString("paciente")
                val col5 = st.getString("estado")

                reg.add(fila(col1))

                val newElement = "Fecha: $col2  Hora: $col3  Paciente: $col4  Estado: $col5"

                myData.add(newElement)
            }
        } catch (ex: SQLException) {
            Toast.makeText(context, "Error al mostrar", Toast.LENGTH_SHORT).show()
        }
    }
    fun CargarDatosCl() {
        myData.clear()
        reg.clear()
        try {

            var st: ResultSet
            var cadena: String =
                "select idCita,FORMAT(fechahora,'dd-MM-yyyy') AS fecha,FORMAT(fechahora,'hh:mm tt') " +
                        "as hora,CONCAT(nombres,' ',apellidos) as paciente" +
                        ",estado from tbCitas ci,tbClientes c where ci.idCliente=c.idCliente " +
                        "and estado='Pendiente' and c.idCliente=? ;"

            val ps: PreparedStatement = conx.dbConn()?.prepareStatement(cadena)!!
            ps.setInt(1, idCuenta)
            st = ps.executeQuery()

            while (st?.next() == true) {

                val col1 = st.getInt("idCita")
                val col2 = st.getString("fecha")
                val col3 = st.getString("hora")
                val col4 = st.getString("paciente")
                val col5 = st.getString("estado")

                reg.add(fila(col1))

                val newElement = "Fecha: $col2  Hora: $col3  Paciente: $col4  Estado: $col5"
                myData.add(newElement)
            }
        } catch (ex: SQLException) {
            Log.i("ol",ex.message.toString())
            Toast.makeText(context, "Error al mostrar cliente", Toast.LENGTH_SHORT).show()
        }
    }

    class misCard(private val Datos: MutableList<String>/*,private val btnClick:(Int)->Unit*/) :
        RecyclerView.Adapter<misCard.MyViewHolder>() {
        class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val textView: TextView = view.findViewById(R.id.txCarta)
            ///val btn:Button=view.findViewById<Button>(R.id.txCarta)
            //   val imageView: ImageView = view.findViewById(R.id.image_view)
        }
        @SuppressLint("MissingInflatedId")
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val vista =
                LayoutInflater.from(parent.context).inflate(R.layout.card, parent, false)
            /*val lol=vista.findViewById<TextView>(R.id.txCarta)
            lol.setOnClickListener(){
                Toast.makeText(parent.context, "PARA UN POCO NENE", Toast.LENGTH_SHORT).show()
            }*/
            return MyViewHolder(vista)
        }

        override fun getItemCount() = Datos.size
        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.textView.setText(Datos[position])
            holder.textView.setOnClickListener(){
                //btnClick(position)
            }
            //Reemplazamos la imagen
            //  holder.imageView.setImageResource(Imagenes[position])
        }

    }
    class RecyclerItemClickListener(
        context: Context,
        recyclerView: RecyclerView,
        private val listener: OnItemClickListener?
    ) : RecyclerView.OnItemTouchListener {

        private val gestureDetector: GestureDetector

        init {
            gestureDetector =
                GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
                    override fun onSingleTapUp(e: MotionEvent): Boolean {
                        return true
                    }
                })
        }

        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
            val childView = rv.findChildViewUnder(e.x, e.y)
            if (childView != null && gestureDetector.onTouchEvent(e)) {
                listener?.onItemClick(childView, rv.getChildAdapterPosition(childView))
                return true
            }
            return false
        }

        override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}

        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}

        interface OnItemClickListener {
            fun onItemClick(view: View, position: Int)
        }
    }

}