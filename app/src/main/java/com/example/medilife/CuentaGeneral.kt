package com.example.medilife

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import java.util.Date
lateinit var perfil:Button
class CuentaGeneral : Fragment() {
    var idCuenta: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        idCuenta = arguments?.getInt("idcu")!!
        return inflater.inflate(R.layout.fragment_cuenta_general, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("cuentage",idCuenta.toString())

        val bundle = Bundle().apply {
            putInt("idcu", idCuenta)
        }
        val volver = requireView().findViewById<Button>(R.id.btnPerfil)


        volver.setOnClickListener{
            findNavController().navigate(R.id.action_cuentaGeneral_to_infoCuentaGeneral,bundle)
        }
    }


}