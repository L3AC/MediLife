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
import androidx.navigation.fragment.findNavController
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
    var estadoC: Int = 0
    private var conx = Conx()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idCuenta = arguments?.getInt("idcu")!!
            idCita = arguments?.getInt("idcita")!!
            idCliente = arguments?.getInt("idcl")!!
            nivelC = arguments?.getInt("nvc")!!
            estadoC = arguments?.getInt("estado")!!
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
        volver = requireView().findViewById(R.id.btnVolver8)

        //LLenarSpin()
        if (estadoC == 2) {//INACTIVA
            habilit(false)
            btnEditar.isVisible = false
            btnGuardar.isVisible = false
        }
        habilit(false)

        var bundle = Bundle().apply {
            putInt("idcu", idCuenta)
            putInt("idcita", idCita)
            putInt("idcl", idCliente)
            putInt("nvc", nivelC)
            putInt("estado", estadoC)
        }
        volver.setOnClickListener {
            findNavController().navigate(R.id.action_infoClienteCita_to_infoCita, bundle)
        }


        CargarDatos()
        btnEditar.setOnClickListener() {
            if (btnGuardar.isVisible) {
                btnEditar.text = "Editar"
                btnGuardar.isVisible = false
                txtPatolog.isEnabled = false
            } else {
                btnEditar.text = "Cancelar"
                btnGuardar.isVisible = true
                txtPatolog.isEnabled = true
            }
        }
        btnGuardar.setOnClickListener() {
            updatePat()
        }

    }

    fun CargarDatos() {
        try {
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
            txtIdent.setText(st.getString("numdocum"))
            txtTipoSangre.setText(st.getString("tipoSangre"))
            txtTelf.setText(st.getString("telefono"))
            txtPatolog.setText(st.getString("patologias"))


        } catch (ex: SQLException) {
            Log.i("error", ex.message.toString())
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

    fun habilit(tf: Boolean) {
        txtNombres.isEnabled = tf
        txtApellid2.isEnabled = tf
        txtNacimiento.isEnabled = tf
        txtSexo.isEnabled = tf
        txtIdent.isEnabled = tf
        txtTipoSangre.isEnabled = tf
        txtTelf.isEnabled = tf
        txtPatolog.isEnabled = tf
        btnGuardar.isVisible = tf
    }

}