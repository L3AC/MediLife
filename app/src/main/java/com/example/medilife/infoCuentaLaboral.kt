package com.example.medilife

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner

lateinit var btnVolver5: ImageButton
lateinit var txtUsuario4: EditText
lateinit var txtNombres4: EditText
lateinit var txtApellid4: EditText
lateinit var txtNacimiento4: EditText
lateinit var spinSexo4: Spinner
lateinit var txtTipoSangre4: EditText
lateinit var txtTelf4: EditText
lateinit var txtMail4: EditText
lateinit var spintipoDocum4: Spinner
lateinit var txtnumDocum4: EditText
lateinit var spinEnt4: Spinner
lateinit var spinQuin4: Spinner
lateinit var txtHora4: EditText
lateinit var btnGuardar4: Button
lateinit var btnEditar4: Button
class infoCuentaLaboral : Fragment() {
    var idCuenta: Int = 0
    var nivelC=0
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
        return inflater.inflate(R.layout.fragment_info_cuenta_laboral, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txtUsuario4 = requireView().findViewById(R.id.txtUsuario3)
        txtNombres4 = requireView().findViewById(R.id.txtNombres3)
        txtApellid4 = requireView().findViewById(R.id.txtApellidos3)
        txtNacimiento4 = requireView().findViewById(R.id.txtNaci3)
        spinSexo4 = requireView().findViewById(R.id.spinSexo3)
        txtTipoSangre4 = requireView().findViewById(R.id.txtTipoSangre3)
        txtTelf4 = requireView().findViewById(R.id.txtTelefono3)
        txtMail4 = requireView().findViewById(R.id.txtMail3)
        spintipoDocum4= requireView().findViewById(R.id.spinTipoD)
        txtnumDocum4 = requireView().findViewById(R.id.txtNumDoc3)
        spinEnt4 = requireView().findViewById(R.id.spinEnt4)/**/
        spinQuin4 = requireView().findViewById(R.id.spinQuin4)
        txtHora4 = requireView().findViewById(R.id.txtHora4)
        btnEditar3 = requireView().findViewById(R.id.btnEditar)
        btnGuardar3 = requireView().findViewById(R.id.btnGuardar)
    }
}