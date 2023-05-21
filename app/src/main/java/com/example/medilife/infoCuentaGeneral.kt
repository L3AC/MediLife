package com.example.medilife

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.util.Date

lateinit var txtUsuario3: EditText
lateinit var txtNombres3: EditText
lateinit var txtApellid3: EditText
lateinit var txtNacimiento3: EditText
lateinit var spinSexoIC: Spinner
lateinit var txtTipoSangre3: EditText
lateinit var txtTelf3: EditText
lateinit var txtMail3: EditText
lateinit var spintipoDocum: Spinner
lateinit var txtnumDocum: EditText
lateinit var txtPatolog3: EditText
lateinit var btnGuardar3: Button
lateinit var btnEditar3: Button

class infoCuentaGeneral : Fragment() {
    var idCuenta: Int = 0
    val sexo = listOf("Femenino", "Masculino")
    val tipodoc = listOf("DUI", "Pasaporte")
    private var conx = Conx()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idCuenta = arguments?.getInt("idcu")!!
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root: View = inflater.inflate(R.layout.fragment_info_cuenta_general, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txtUsuario3 = requireView().findViewById(R.id.txtUsuario3)
        txtNombres3 = requireView().findViewById(R.id.txtNombres3)
        txtApellid3 = requireView().findViewById(R.id.txtApellidos3)
        txtNacimiento3 = requireView().findViewById(R.id.txtNaci3)
        spinSexoIC = requireView().findViewById(R.id.spinSexo3)
        txtTipoSangre3 = requireView().findViewById(R.id.txtTipoSangre3)
        txtTelf3 = requireView().findViewById(R.id.txtTelefono3)
        txtMail3 = requireView().findViewById(R.id.txtMail3)
        spintipoDocum= requireView().findViewById(R.id.spinTipoD)
        txtnumDocum = requireView().findViewById(R.id.txtNumDoc3)
        txtPatolog3 = requireView().findViewById(R.id.txtPatolog3)
        btnEditar3 = requireView().findViewById(R.id.btnEditar)
        btnGuardar3 = requireView().findViewById(R.id.btnGuardar)
/*HOLA*/
        LLenarSpin()
        Habilit(false)
        cargarData()
        btnEditar3.setOnClickListener(){
            if (btnGuardar3.isVisible) {
                btnEditar3.text = "Editar"
                Habilit(false)
            } else {
                btnEditar3.text = "Cancelar"
                Habilit(true)
            }
        }
        btnGuardar3.setOnClickListener(){
            updateData()
        }


        val bundle = Bundle().apply {
            putInt("idcu", idCuenta)
        }
        Log.i("xd", idCuenta.toString())

        val volver = requireView().findViewById<ImageButton>(R.id.btnVolver4)

        volver.setOnClickListener {
            findNavController().navigate(R.id.action_infoCuentaGeneral_to_cuentaGeneral, bundle)
        }

    }

    fun cargarData() {
        try {
            val adapti = LLenarSpin()
            val adaptiTPD = LLenarSpinTPD()
            var st: ResultSet
            val cadena =
                "select usuario,correo,nombres,apellidos,tipodocum,numdocum,nacimiento,sexo,telefono,tipoSangre,patologias\n" +
                        "from tbClientes c,tbUsuarios u where c.idUsuario=u.idUsuario and idCliente=?;"
            val ps: PreparedStatement = conx.dbConn()?.prepareStatement(cadena)!!
            ps.setInt(1, idCuenta)
            st = ps.executeQuery()
            st.next()
            txtUsuario3.setText(st.getString("usuario"))
            txtMail3.setText(st.getString("correo"))
            txtNombres3.setText(st.getString("nombres"))
            txtApellid3.setText(st.getString("apellidos"))
            txtNacimiento3.setText(st.getString("nacimiento"))
            spinSexoIC.setSelection(adapti.getPosition(st.getString("sexo")))
            txtTipoSangre3.setText(st.getString("tipoSangre"))
            txtTelf3.setText(st.getString("telefono"))
            spintipoDocum.setSelection(adaptiTPD.getPosition(st.getString("tipodocum")))
            txtnumDocum.setText(st.getString("numdocum"))
            txtPatolog3.setText(st.getString("patologias"))

        } catch (ex: SQLException) {
            Log.e("Error: ", ex.message!!)
            Toast.makeText(context, "Error al cargar", Toast.LENGTH_SHORT).show()
        }
        conx.dbConn()!!.close()
    }

    fun updateData() {
        try {
            val cadena =
                "UPDATE tbUsuarios SET usuario=?,correo=?  from tbClientes c,tbUsuarios u " +
                "WHERE c.idUsuario=u.idUsuario and idCliente=?;"+

                "UPDATE tbClientes SET nombres =?, apellidos=?,tipodocum=?," +
                "numdocum=?,nacimiento=?,sexo=?," +
                "telefono=?,tipoSangre=?,patologias=? " +
                "from tbClientes c,tbUsuarios u WHERE c.idUsuario=u.idUsuario and idCliente=?;"
            val ps: PreparedStatement = conx.dbConn()?.prepareStatement(cadena)!!

            ps.setString(1, txtUsuario3.text.toString())
            ps.setString(2, txtMail3.text.toString())
            ps.setInt(3, idCuenta)
            ps.setString(4, txtNombres3.text.toString())
            ps.setString(5, txtApellid3.text.toString())
            ps.setString(6, spintipoDocum.selectedItem.toString())
            ps.setString(7, txtnumDocum.text.toString())
            ps.setString(8, txtNacimiento3.text.toString())
            ps.setString(9, spinSexoIC.selectedItem.toString())
            ps.setString(10, txtTelf3.text.toString())
            ps.setString(11, txtTipoSangre3.text.toString())
            ps.setString(12, txtPatolog3.text.toString())
            ps.setInt(13,idCuenta)

            ps.executeUpdate()
            Toast.makeText(context, "Campos actualizados", Toast.LENGTH_SHORT).show()
        } catch (ex: SQLException) {
            Log.e("Error: ", ex.message!!)
            Toast.makeText(context, "No se pudo actualizar", Toast.LENGTH_SHORT).show()
        }
        conx.dbConn()!!.close()
    }

    fun LLenarSpin(): ArrayAdapter<String> {
        val adaptadorSpinner =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, sexo)
        adaptadorSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val spinner = requireView().findViewById<Spinner>(R.id.spinSexo3)
        spinner.adapter = adaptadorSpinner
        return adaptadorSpinner
    }
    fun LLenarSpinTPD(): ArrayAdapter<String> {
        val adaptadorSpinner =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, tipodoc)
        adaptadorSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val spinner = requireView().findViewById<Spinner>(R.id.spinTipoD)
        spinner.adapter = adaptadorSpinner
        return adaptadorSpinner
    }

    fun Habilit(tf: Boolean) {
        txtUsuario3.isEnabled = tf
        txtMail3.isEnabled=tf
        txtNombres3.isEnabled = tf
        txtApellid3.isEnabled = tf
        txtNacimiento3.isEnabled = tf
        spinSexoIC.isEnabled = tf
        txtTipoSangre3.isEnabled = tf
        txtTelf3.isEnabled = tf
        spintipoDocum.isEnabled = tf
        txtnumDocum.isEnabled = tf
        txtPatolog3.isEnabled = tf
        btnGuardar3.isVisible = tf
    }
}