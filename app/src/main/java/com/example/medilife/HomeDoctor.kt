package com.example.medilife

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController


lateinit var bPendiente: Button
lateinit var bPasadas: Button

class HomeDoctor : Fragment() {
    var idCuenta: Int = 0
    var nivelC: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val parametros = arguments
            idCuenta = parametros?.getInt("idcu")!!
            nivelC = arguments?.getInt("nvc")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_doctor, container, false)
    }

    //LA IMPORTANTE
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bPendiente = requireView().findViewById(R.id.btnPendiH)
        bPasadas = requireView().findViewById(R.id.btnPasadH)
        Log.i("doctor", idCuenta.toString())
        Log.i("nivel ", nivelC.toString())
        var bundle = Bundle().apply {
            putInt("idcu", idCuenta)
            putInt("nvc", nivelC)
            putInt("estado", 1)
        }
        var bundle2 = Bundle().apply {
            putInt("idcu", idCuenta)
            putInt("nvc", nivelC)
            putInt("estado", 2)
        }
        bPendiente.setOnClickListener() {
            findNavController().navigate(R.id.action_homeDoctor_to_citasActivas, bundle)
        }
        bPasadas.setOnClickListener() {
            findNavController().navigate(R.id.action_homeDoctor_to_historialCitas, bundle2)
        }
    }

}