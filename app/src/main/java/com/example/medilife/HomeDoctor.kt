package com.example.medilife

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import androidx.navigation.fragment.findNavController


lateinit var bPendiente:Button
class HomeDoctor : Fragment() {
    var idCuenta:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val parametros = arguments
            idCuenta = parametros?.getInt("idcu")!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_doctor, container, false)
    }

//LA IMPORTANTE
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    bPendiente = requireView().findViewById(R.id.btnPendi)

    Log.i("doctor",idCuenta.toString())
    var bundle = Bundle().apply {
        putInt("idcu", idCuenta)
    }
    bPendiente.setOnClickListener(){
        findNavController().navigate(R.id.action_homeDoctor_to_citasActivas,bundle)
    }

    }

}