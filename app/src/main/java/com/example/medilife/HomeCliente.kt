package com.example.medilife

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController

lateinit var reservar: ImageButton
lateinit var bPendienteC: Button
lateinit var bPasadasC: Button

class HomeCliente : Fragment() {
    private var conx = Conx()
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
        return inflater.inflate(R.layout.fragment_home_cliente, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("id", idCuenta.toString())
        Log.i("nivel", nivelC.toString())
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
        reservar = requireView().findViewById(R.id.btnReserv)
        bPendiente = requireView().findViewById(R.id.btnPendiC)
        bPasadas = requireView().findViewById(R.id.btnPasadC)
        reservar.setOnClickListener() {
            findNavController().navigate(R.id.action_homeCliente_to_reservaCita, bundle)
        }
        bPendiente.setOnClickListener() {
            findNavController().navigate(R.id.action_homeCliente_to_citasActivas, bundle)
        }
        bPasadas.setOnClickListener() {
            findNavController().navigate(R.id.action_homeCliente_to_historialCitas, bundle2)
        }
    }

}