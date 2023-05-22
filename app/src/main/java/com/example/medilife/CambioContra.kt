package com.example.medilife

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
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
    private var cox=Conx()
    var idCuenta: Int = 0
    var nivelC: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idCuenta = arguments?.getInt("idcu")!!
            nivelC = arguments?.getInt("nvc")!!
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
            verifpass()
            habil(true)
        }

        btnConfirmar.setOnClickListener {
            verificar2()
            cambocontra()
        }

        val bundle = Bundle().apply {
            putInt("idcu", idCuenta)
            putInt("nvc", nivelC)
        }
        volver.setOnClickListener {
            findNavController().navigate(R.id.action_cambioContra_to_cuentaGeneral, bundle)
        }

    }

    fun habil(tf: Boolean){
        txtNewPass.isVisible = tf
        tv1.isVisible = tf
        tv2.isVisible = tf
        txtNewPass.isVisible = tf
        btnConfirmar.isVisible = tf
    }

    fun verifpass(){
        try {
            val cadena: String="Select contra From tbUsuarios Where usuario=?"
            val st: ResultSet
            val ps: PreparedStatement = cox.dbConn()?.prepareStatement(cadena)!!

            ps.setString(1, txtPass.text.toString())
            st = ps.executeQuery()
            st.next()

            val found = st.row
            if (found == 2) {
                Toast.makeText(context, "La contraseña no coincide", Toast.LENGTH_SHORT).show()
            }
        }catch (ex: SQLException) {

            Toast.makeText(context, "Error interno", Toast.LENGTH_SHORT).show()
        }
        cox.dbConn()!!.close()
    }

    fun verificar2(){
        if(txtNewPass.text.toString()== txtNewNewPass.text.toString())
        {
            Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            btnConfirmar.isEnabled=false
        }
        else
        {
            btnConfirmar.isEnabled=true
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cambio_contra, container, false)
    }

    fun cambocontra(){
        try {

            val cadena= "Update tbUsuarios Set contra = ? Where usuario = ?"
            val ps: PreparedStatement = cox.dbConn()?.prepareStatement(cadena)!!

            ps.setString(1, txtNewPass.text.toString())


            Toast.makeText(context, "Contraseña cambiada correctamente", Toast.LENGTH_SHORT).show()
        }catch (ex: SQLException) {
            Toast.makeText(context, "Error al mostrar", Toast.LENGTH_SHORT).show()
        }
    }

}