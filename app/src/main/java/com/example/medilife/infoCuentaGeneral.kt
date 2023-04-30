package com.example.medilife

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Date

lateinit var Usuario:EditText
lateinit var Apellido:EditText
lateinit var Doc:EditText
lateinit var Telefono:EditText

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"




/**
 * A simple [Fragment] subclass.
 * Use the [infoCuentaGeneral.newInstance] factory method to
 * create an instance of this fragment.
 */
class infoCuentaGeneral : Fragment() {


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root:View=inflater.inflate(R.layout.fragment_info_cuenta_general, container, false)

        Usuario = root.findViewById<EditText>(R.id.editTextTextPersonName5)

        Usuario.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                TODO("Not yet implemented")
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                TODO("Not yet implemented")
                val regex = Regex("[a-zA-Z]+")
                if(!p0.toString().matches(regex)){
                    Usuario.error = "Solo se permiten letras"
                }
                else{
                    Usuario.error = null
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                TODO("Not yet implemented")
            }
        })

        return root

        Apellido = root.findViewById<EditText>(R.id.editTextTextPersonName6)

        Apellido.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                TODO("Not yet implemented")
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                TODO("Not yet implemented")
                val regex = Regex("[a-zA-Z]+")
                if(!p0.toString().matches(regex)){
                    Apellido.error = "Solo se permiten letras"
                }
                else{
                    Apellido.error = null
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                TODO("Not yet implemented")
            }
        })

        return root


        Doc = root.findViewById<EditText>(R.id.editTextTextPersonName8)

        Doc.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                TODO("Not yet implemented")
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                TODO("Not yet implemented")
                val regex = Regex("[0-9]+")
                if(!p0.toString().matches(regex)){
                    Doc.error = "Solo se permiten números"
                }
                else{
                    Doc.error = null
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                TODO("Not yet implemented")
            }
        })

        return root

        Telefono = root.findViewById<EditText>(R.id.editTextTextPersonName8)

        Telefono.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                TODO("Not yet implemented")
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                TODO("Not yet implemented")
                val regex = Regex("[0-9]+")
                if(!p0.toString().matches(regex)){
                    Telefono.error = "Solo se permiten números"
                }
                else{
                    Telefono.error = null
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                TODO("Not yet implemented")
            }
        })

        return root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment infoCuentaGeneral.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            infoCuentaGeneral().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}