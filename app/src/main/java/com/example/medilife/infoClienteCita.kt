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
import androidx.core.view.isVisible
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

lateinit var txtNombres: EditText
lateinit var txtApellid2: EditText
lateinit var txtNacimiento: EditText
lateinit var txtSexo: EditText
lateinit var txtIdent: EditText
lateinit var txtTipoSangre: EditText
lateinit var txtTelf: EditText
lateinit var txtPatolog: EditText
lateinit var btnGuardar: Button
lateinit var btnEditar: Button
lateinit var btnVolver: Button
lateinit var tpsexo2: Spinner

class infoClienteCita : Fragment() {
    var idCuenta: Int = 0
    var idCita: Int = 0
    var nivelC: Int = 0
    var idCliente: Int = 0
    private var conx = Conx()
   // val sexo = listOf("Femenino", "Masculino")


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
        txtIdent = requireView().findViewById(R.id.txtIdent)
        txtTipoSangre = requireView().findViewById(R.id.txtTipoSangre)
        txtTelf = requireView().findViewById(R.id.txtTelf)
        txtPatolog = requireView().findViewById(R.id.txtPatolog)
        btnEditar = requireView().findViewById(R.id.btnEditar2)
        btnGuardar = requireView().findViewById(R.id.btnGuardar2)

        //LLenarSpin()
        txtNombres.isEnabled = false
        txtApellid2.isEnabled = false
        txtNacimiento.isEnabled = false
        txtSexo.isEnabled = false
        txtIdent.isEnabled = false
        txtTipoSangre.isEnabled = false
        txtTelf.isEnabled = false
        txtPatolog.isEnabled = false
        btnGuardar.isVisible = false


        CargarDatos()
        btnEditar.setOnClickListener() {
            if (btnGuardar.isVisible) {
                btnEditar.text = "Editar"
                btnGuardar.isVisible = false
                txtPatolog.isEnabled=false
            } else {
                btnEditar.text = "Cancelar"
                btnGuardar.isVisible = true
                txtPatolog.isEnabled=true
            }
        }
        btnGuardar.setOnClickListener() {
            updatePat()
        }

    }

    fun CargarDatos() {
        try {
           // val adapti = LLenarSpin()
            var cadena = "select * from tbClientes where idCliente=?;"
            var st: ResultSet
            val ps: PreparedStatement = conx.dbConn()?.prepareStatement(cadena)!!

            ps.setInt(1, idCliente)

            st = ps.executeQuery()
            st.next()
            txtNombres.setText(st.getString("nombres"))
            txtApellid2.setText(st.getString("apellidos"))
            txtNacimiento.setText(st.getString("nacimiento"))
            txtSexo.setText(st.getString("sexo"))
            //spinSexoIC.setSelection(adapti.getPosition(st.getString("sexo")));
            txtIdent.setText(st.getString("numdocum"))
            txtTipoSangre.setText(st.getString("tipoSangre"))
            txtTelf.setText(st.getString("telefono"))
            txtPatolog.setText(st.getString("patologias"))


        } catch (ex: SQLException) {
            Toast.makeText(context, "Error al mostrar", Toast.LENGTH_SHORT).show()
        }
    }

    fun updatePat() {
        try {
            val cadena: String =
                "UPDATE tbClientes SET patologias =? WHERE idCliente=?;"

            val ps: PreparedStatement = conx.dbConn()?.prepareStatement(cadena)!!
            ps.setString(1, txtPatolog.text.toString())
            ps.setInt(2, idCliente)

            ps.executeUpdate()
            Toast.makeText(context, "Campo actualizado", Toast.LENGTH_SHORT).show()
        } catch (ex: SQLException) {
            Log.e("Error: ", ex.message!!)
            Toast.makeText(context, "Errorsito", Toast.LENGTH_SHORT).show()
        }
        conx.dbConn()!!.close()

    }

    /*fun LLenarSpin(): ArrayAdapter<String> {
        val adaptadorSpinner =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, sexo)
        adaptadorSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val spinner = requireView().findViewById<Spinner>(R.id.spinSexoIC)
        spinner.adapter = adaptadorSpinner
        return adaptadorSpinner

    }*/

}