package com.example.medilife

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.findNavController
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

lateinit var ListVista1: ListView

class fila(
    val id: Int,
    val fecha: String,
    val hora: String,
    val paciente: String
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
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_citas_activas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ListVista1 = requireView().findViewById(R.id.miLista2)
        if(nivelC==1){
            CargarDatosDoc()
        }
        if(nivelC==2){
            CargarDatosSec()
        }
        if(nivelC==3){
            CargarDatosCl()
        }

        ListVista1.setOnItemClickListener() { parent, view, position, id ->
            val espc = reg[position]
            idCita = espc.id
            var bundle = Bundle().apply {
                putInt("idcu", idCuenta)
                putInt("idcita", idCita)
                putInt("nvc", nivelC)
            }
            findNavController().navigate(R.id.action_citasActivas_to_infoCita, bundle)
        }
    }

    fun CargarDatosDoc() {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, myData)
        ListVista1.adapter = adapter
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

                reg.add(fila(col1, col2, col3, col4))

                val newElement = "Fecha: $col2  Hora: $col3  Paciente: $col4  Estado: $col5"

                myData.add(newElement)
                adapter.notifyDataSetChanged()

            }
            /**/
            ListVista1.visibility = View.VISIBLE
        } catch (ex: SQLException) {
            Toast.makeText(context, "Error al mostrar", Toast.LENGTH_SHORT).show()
        }
    }
    fun CargarDatosSec() {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, myData)
        ListVista1.adapter = adapter
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
            //ps.setInt(1, idCuenta)
            st = ps.executeQuery()

            while (st?.next() == true) {

                val col1 = st.getInt("idCita")
                val col2 = st.getString("fecha")
                val col3 = st.getString("hora")
                val col4 = st.getString("paciente")
                val col5 = st.getString("estado")

                reg.add(fila(col1, col2, col3, col4))

                val newElement = "Fecha: $col2  Hora: $col3  Paciente: $col4  Estado: $col5"

                myData.add(newElement)
                adapter.notifyDataSetChanged()

            }
            ListVista1.visibility = View.VISIBLE
        } catch (ex: SQLException) {
            Toast.makeText(context, "Error al mostrar", Toast.LENGTH_SHORT).show()
        }
    }
    fun CargarDatosCl() {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, myData)
        ListVista1.adapter = adapter
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

                reg.add(fila(col1, col2, col3, col4))

                val newElement = "Fecha: $col2  Hora: $col3  Paciente: $col4  Estado: $col5"

                myData.add(newElement)
                adapter.notifyDataSetChanged()

            }
            ListVista1.visibility = View.VISIBLE
        } catch (ex: SQLException) {
            Toast.makeText(context, "Error al mostrar", Toast.LENGTH_SHORT).show()
        }
    }

}