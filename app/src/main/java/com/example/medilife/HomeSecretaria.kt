package com.example.medilife

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

lateinit var bPendienteS: Button
lateinit var bPasadasS: Button
class HomeSecretaria : Fragment() {
    var idCuenta:Int = 0
    var nivelC:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idCuenta = arguments?.getInt("idcu")!!
            nivelC = arguments?.getInt("nvc")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_secretaria, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("secre",idCuenta.toString())
        Log.i("nivel ",nivelC.toString())
        var bundle = Bundle().apply {
            putInt("idcu", idCuenta)
            putInt("nvc", nivelC)
        }
        bPendienteS = requireView().findViewById(R.id.btnPendiS)
        bPasadasS = requireView().findViewById(R.id.btnPasadS)

        bPendienteS.setOnClickListener(){
            findNavController().navigate(R.id.action_homeSecretaria_to_citasActivas,bundle)
        }

    }

}