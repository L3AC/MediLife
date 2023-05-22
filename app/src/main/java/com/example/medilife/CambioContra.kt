package com.example.medilife

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

lateinit var CambioContr: Button
lateinit var txtPass:EditText
lateinit var txtNewPass:EditText
lateinit var txtNewNewPass:EditText
lateinit var btnVerificar:Button
lateinit var btnConfirmar:Button
lateinit var tv1: TextView
lateinit var tv2: TextView


class CambioContra : Fragment() {
    private var conx=Conx()
    var idCuenta: Int = 0
    var nivelC: Int = 0
    var contra:String=""
    var idUser:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idCuenta = arguments?.getInt("idcu")!!
            nivelC = arguments?.getInt("nvc")!!
            idUser = arguments?.getInt("idus")!!
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txtPass = requireView().findViewById(R.id.txtPass)
        txtNewPass = requireView().findViewById(R.id.txtNewPass)
        txtNewNewPass = requireView().findViewById(R.id.txtNewNewPass)
        btnVerificar = requireView().findViewById(R.id.btnVerificar)
        btnConfirmar = requireView().findViewById(R.id.btnConfirmar)
        tv1 = requireActivity().findViewById(R.id.tv1)
        tv2 = requireActivity().findViewById(R.id.tv2)
        volver = requireView().findViewById(R.id.btnVolver3)

        habil(false)

        btnVerificar.setOnClickListener {
            verifContra()
        }

        btnConfirmar.setOnClickListener {
            verifNew()
        }

        val bundle = Bundle().apply {
            putInt("idcu", idCuenta)
            putInt("nvc", nivelC)
            putInt("idus", idUser)
        }
        volver.setOnClickListener {
            findNavController().navigate(R.id.action_cambioContra_to_cuentaGeneral, bundle)
        }

    }

    fun habil(tf: Boolean){
        txtNewPass.isEnabled = tf
        tv1.isEnabled= tf
        tv2.isEnabled= tf
        txtNewNewPass.isEnabled = tf
        btnConfirmar.isEnabled = tf
    }
    fun verifContra() {
        try {
            val cadena: String = "select contra from tbUsuarios where idUsuario=? " +
                    "and contra=? COLLATE SQL_Latin1_General_CP1_CS_AS"
            val st: ResultSet
            val ps: PreparedStatement = conx.dbConn()?.prepareStatement(cadena)!!

            ps.setInt(1, idUser)
            ps.setString(2, txtPass.text.toString())
            st = ps.executeQuery()
            st.next()

            val found = st.row
            if (found == 1) {
                contra = st.getString("contra")
                habil(true)
            } else {
                Toast.makeText(context, "Contraseña incorrecta", Toast.LENGTH_SHORT).show()
                habil(false)
            }
        } catch (ex: SQLException) {
            Log.e("Error: ", ex.message!!)
            Toast.makeText(context, "Error al ejecutar", Toast.LENGTH_SHORT).show()
        }
        conx.dbConn()!!.close()
    }

    fun verifNew(){
        if(txtNewPass.text.toString()== txtNewNewPass.text.toString())
        {
            updateContra()
        }
        else
        {
            Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cambio_contra, container, false)
    }

    fun updateContra(){
        try {
            val cadena= "Update tbUsuarios Set contra=? Where idUsuario =?;"
            val ps: PreparedStatement = conx.dbConn()?.prepareStatement(cadena)!!
Log.i("usuario",idUser.toString())
            ps.setString(1, txtNewPass.text.toString())
            ps.setInt(2,idUser)
            ps.executeUpdate()
            Toast.makeText(context, "Contraseña actualizada", Toast.LENGTH_SHORT).show()
        }catch (ex: SQLException) {
            Log.i("error",ex.message.toString())
            Toast.makeText(context, "Error al actualizar", Toast.LENGTH_SHORT).show()
        }
    }

}