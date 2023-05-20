package com.example.medilife

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

lateinit var txtNombres: EditText
lateinit var txtApellid2: EditText
lateinit var txtNacimiento: EditText
lateinit var txtSexo: EditText
lateinit var spinSexoIC:Spinner
lateinit var txtIdent: EditText
lateinit var txtTipoSangre: EditText
lateinit var txtTelf: EditText
lateinit var txtPatolog: EditText
lateinit var btnGuardar:Button
lateinit var btnVolver: Button
lateinit var tpsexo2: Spinner

class infoClienteCita : Fragment() {
    var idCuenta: Int = 0
    var idCita: Int = 0
    var nivelC: Int = 0
    var idCliente: Int = 0
    private var conx = Conx()
    val sexo = listOf("Femenino", "Masculino")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idCuenta = arguments?.getInt("idcu")!!
            idCita = arguments?.getInt("idcita")!!
            idCliente = arguments?.getInt("idcl")!!
            nivelC = arguments?.getInt("nvc")!!
            Log.i("IDCUENTA", idCuenta.toString())
            Log.i("IDcita", idCita.toString())
            Log.i("IDCLIENTE", idCliente.toString())

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info_cliente_cita, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        txtNombres = requireView().findViewById(R.id.txtNombres)
        txtApellid2 = requireView().findViewById(R.id.txtApellid2)
        txtNacimiento = requireView().findViewById(R.id.txtNacimiento)
        txtSexo = requireView().findViewById(R.id.txtSexo)
        spinSexoIC=requireView().findViewById(R.id.spinSexoIC)
        txtIdent = requireView().findViewById(R.id.txtIdent)
        txtTipoSangre = requireView().findViewById(R.id.txtTipoSangre)
        txtTelf = requireView().findViewById(R.id.txtTelf)
        txtPatolog = requireView().findViewById(R.id.txtPatolog)
        btnGuardar = requireView().findViewById(R.id.btnGuardar)

        LLenarSpin()
        /*cbNombre.setSelectedItem(st.getString("Nombre"));
        txtNombres = requireView().findViewById(R.id.txtNombres)
        txtApellid2 = requireView().findViewById(R.id.txtApellid2)
        txtNacimiento = requireView().findViewById(R.id.txtNacimiento)
        txtSexo = requireView().findViewById(R.id.txtSexo)
        txtIdent = requireView().findViewById(R.id.txtIdent)
        txtTipoSangre = requireView().findViewById(R.id.txtTipoSangre)
        txtTelf = requireView().findViewById(R.id.txtTelf)
        txtPatolog = requireView().findViewById(R.id.txtPatolog)
        btnGuardar = requireView().findViewById(R.id.btnGuardar)*/

        CargarDatos()
        btnGuardar.setOnClickListener(){

        }

    }
    fun CargarDatos() {
        try {
            val adapti=LLenarSpin()
            var cadena: String =
                "select * from tbClientes where idCliente=?;"
            var st: ResultSet
            val ps: PreparedStatement = conx.dbConn()?.prepareStatement(cadena)!!

            ps.setInt(1, idCliente)

            st = ps.executeQuery()
            st.next()
            txtNombres.setText(st.getString("nombres"))
            txtApellid2.setText(st.getString("apellidos"))
            txtNacimiento.setText(st.getString("nacimiento"))
            txtSexo.setText(st.getString("sexo"))

            /*val selection=st.getString("sexo")
            if(st.getString("sexo")=="Masculino"){

            }
            else{

            }*/
            spinSexoIC.setSelection(adapti.getPosition(st.getString("sexo")));
            txtIdent.setText(st.getString("numdocum"))
            txtTipoSangre.setText(st.getString("tipoSangre"))
            txtTelf.setText(st.getString("telefono"))
            txtPatolog.setText(st.getString("patologias"))


        } catch (ex: SQLException) {
            Toast.makeText(context, "Error al mostrar", Toast.LENGTH_SHORT).show()
        }
    }
   /* fun updatePat() {

        try {
            val cadena: String =
                "UPDATE tbClientes SET patologias =? WHERE idCliente=?;"

            val ps: PreparedStatement = conx.dbConn()?.prepareStatement(cadena)!!

            /*ps.setString(1, idus.toString())
            ps.setString(2, nomb.text.toString())
            ps.setString(3, apell.text.toString())
            ps.setString(4, tpdoc.selectedItem.toString())
            ps.setString(5, ndoc.text.toString())
            ps.setString(6, fechaSql)
            ps.setString(7, tpsexo.selectedItem.toString())
            ps.setString(8, tel.text.toString())
            ps.setString(9, tpsangre.text.toString())
            ps.setString(10, patol.text.toString())*/

            ps.executeUpdate()

        } catch (ex: SQLException) {
            Log.e("Error: ", ex.message!!)
            Toast.makeText(applicationContext, "Errorsito", Toast.LENGTH_SHORT).show()
        }
        conx.dbConn()!!.close()

    }*/

   fun LLenarSpin(): ArrayAdapter<String> {
       val adaptadorSpinner = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, sexo)
       adaptadorSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
       val spinner = requireView().findViewById<Spinner>(R.id.spinSexoIC)
       spinner.adapter = adaptadorSpinner

       return adaptadorSpinner

   }

}