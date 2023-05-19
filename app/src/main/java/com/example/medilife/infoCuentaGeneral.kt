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
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import java.util.Date

lateinit var Usuario:EditText
lateinit var Apellido:EditText
lateinit var Doc:EditText
lateinit var Telefono:EditText


class infoCuentaGeneral : Fragment() {
    var idCuenta: Int =0

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
        val root:View=inflater.inflate(R.layout.fragment_info_cuenta_general, container, false)

        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = Bundle().apply {
            putInt("idcu", idCuenta)
        }
        Log.i("xd", idCuenta.toString())

        val volver = requireView().findViewById<ImageButton>(R.id.btnVolver4)

        volver.setOnClickListener{
            findNavController().navigate(R.id.action_infoCuentaGeneral_to_cuentaGeneral,bundle)
        }

    }



}