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
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import java.util.Date

lateinit var txtUsuario3:EditText
lateinit var txtNombres3: EditText
lateinit var txtApellid3: EditText
lateinit var txtNacimiento3: EditText
lateinit var txtSexo3: EditText
lateinit var spinSexoIC: Spinner
lateinit var txtIdent3: EditText
lateinit var txtTipoSangre3: EditText
lateinit var txtTelf3: EditText
lateinit var txtPatolog3: EditText
lateinit var btnGuardar3: Button
lateinit var btnEditar3: Button

class infoCuentaGeneral : Fragment() {
    var idCuenta: Int =0
    val sexo = listOf("Femenino", "Masculino")
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

        txtNombres3 = requireView().findViewById(R.id.txtNombres)
        txtApellid3 = requireView().findViewById(R.id.txtApellid2)
        txtNacimiento3 = requireView().findViewById(R.id.txtNacimiento)
        txtSexo3 = requireView().findViewById(R.id.txtSexo)
        txtIdent3 = requireView().findViewById(R.id.txtIdent)
        txtTipoSangre3 = requireView().findViewById(R.id.txtTipoSangre)
        txtTelf3 = requireView().findViewById(R.id.txtTelf)
        txtPatolog3 = requireView().findViewById(R.id.txtPatolog)
        btnEditar3 = requireView().findViewById(R.id.btnEditar)
        btnGuardar3 = requireView().findViewById(R.id.btnGuardar)

        LLenarSpin()
        txtNombres3.isEnabled = false
        txtApellid3.isEnabled = false
        txtNacimiento3.isEnabled = false
        txtSexo3.isEnabled = false
        txtIdent3.isEnabled = false
        txtTipoSangre3.isEnabled = false
        txtTelf3.isEnabled = false
        txtPatolog3.isEnabled = false
        btnGuardar3.isVisible = false

        val bundle = Bundle().apply {
            putInt("idcu", idCuenta)
        }
        Log.i("xd", idCuenta.toString())

        val volver = requireView().findViewById<ImageButton>(R.id.btnVolver4)

        volver.setOnClickListener{
            findNavController().navigate(R.id.action_infoCuentaGeneral_to_cuentaGeneral,bundle)
        }

    }
    fun updateData(){

    }
    fun LLenarSpin(): ArrayAdapter<String> {
        val adaptadorSpinner =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, sexo)
        adaptadorSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val spinner = requireView().findViewById<Spinner>(R.id.spinSexo3)
        spinner.adapter = adaptadorSpinner
        return adaptadorSpinner
    }
}