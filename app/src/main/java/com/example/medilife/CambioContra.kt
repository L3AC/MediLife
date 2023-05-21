package com.example.medilife

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

lateinit var CambioContr: Button
lateinit var txtPass:EditText
lateinit var txtNewPass:EditText
lateinit var txtNewNewPass:EditText


class CambioContra : Fragment() {
    private var cox=Conx()
    var iduser:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            iduser = arguments?.getInt("idcu")!!
        }
    }

    fun buscarID() {
        try {
            var cadena: String =
                "select idCliente from tbClientes where idCliente=?;"
            var st: ResultSet
            val ps: PreparedStatement = cox.dbConn()?.prepareStatement(cadena)!!

            st = ps.executeQuery()
            st.next()
            iduser = st.getInt("idCliente")


        } catch (ex: SQLException) {
            Toast.makeText(context, "Error al mostrar", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        volver = requireView().findViewById(R.id.btnVolver3)

        volver.setOnClickListener {
            findNavController().navigate(R.id.action_cambioContra_to_cuentaGeneral)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cambio_contra, container, false)
    }

    fun cambocontra(newPass:String, newnewPass:String){
        try {

            val cadena= "Update tbClientes Set = ? Where idCliente = ?"
            val ps: PreparedStatement = cox.dbConn()?.prepareStatement(cadena)!!


            Toast.makeText(context, "Contraseña cambiada correctamente", Toast.LENGTH_SHORT).show()
        }catch (ex: SQLException) {
            Toast.makeText(context, "Error al mostrar", Toast.LENGTH_SHORT).show()
        }
    }

}