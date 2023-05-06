package com.example.medilife

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController

lateinit var reservar:ImageButton

class HomeCliente : Fragment() {
    private var conx = Conx()
    var idCuenta:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idCuenta = arguments?.getInt("idcu")!!
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_cliente, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("cliente",idCuenta.toString())
        var bundle = Bundle().apply {
            putInt("idcu", idCuenta)
        }
        reservar=requireView().findViewById(R.id.btnReserv)
        reservar.setOnClickListener(){
            findNavController().navigate(R.id.action_homeCliente_to_reservaCita,bundle)
        }
    }

}