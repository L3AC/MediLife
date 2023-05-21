package com.example.medilife

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.util.Calendar

lateinit var btnVolver5: ImageButton
lateinit var txtUsuario4: EditText
lateinit var txtNombres4: EditText
lateinit var txtApellid4: EditText
lateinit var txtNacimiento4: EditText
lateinit var btnFecha2: ImageButton
lateinit var spinSexo4: Spinner
lateinit var spinEsp4: Spinner
lateinit var lbEsp: TextView
lateinit var txtTelf4: EditText
lateinit var txtMail4: EditText
lateinit var spintipoDocum4: Spinner
lateinit var txtnumDocum4: EditText
lateinit var spinEnt4: Spinner
lateinit var spinQuin4: Spinner
lateinit var txtHora4: EditText
lateinit var btnGuardar4: Button
lateinit var btnEditar4: Button

class spec(val id: Int, val nombre: String)

val speciL = mutableListOf<spec>()

class infoCuentaLaboral : Fragment() {
    var idCuenta: Int = 0
    var nivelC = 0
    val sexo = listOf("Femenino", "Masculino")
    val tipodoc = listOf("DUI", "Pasaporte")
    private var fechaSql: String = ""
    private var dateh: String = ""
    val hora = listOf(
        "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
        "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"
    )
    var horaN: String = ""
    var horaM: String = ""
    private var conx = Conx()
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
        txtUsuario4 = requireView().findViewById(R.id.txtUsuario4)
        txtNombres4 = requireView().findViewById(R.id.txtNombres4)
        txtApellid4 = requireView().findViewById(R.id.txtApellidos4)
        txtNacimiento4 = requireView().findViewById(R.id.txtNaci4)
        spinSexo4 = requireView().findViewById(R.id.spinSexo4)
        spinEsp4 = requireView().findViewById(R.id.spinEsp4)
        lbEsp = requireView().findViewById(R.id.lbEsp)
        txtTelf4 = requireView().findViewById(R.id.txtTelefono4)
        txtMail4 = requireView().findViewById(R.id.txtMail4)
        spintipoDocum4 = requireView().findViewById(R.id.spinTipoD4)
        txtnumDocum4 = requireView().findViewById(R.id.txtNumDoc4)
        spinEnt4 = requireView().findViewById(R.id.spinEnt4)/**/
        spinQuin4 = requireView().findViewById(R.id.spinQuin4)
        txtHora4 = requireView().findViewById(R.id.txtHora4)
        btnEditar4 = requireView().findViewById(R.id.btnEditar4)
        btnGuardar4 = requireView().findViewById(R.id.btnGuardar4)
        btnFecha2 = requireView().findViewById(R.id.btnFecha2)

        txtNacimiento4.isEnabled = false
        txtHora4.isEnabled = false
        SpinHora()
        LLenarSpinSe()
        LLenarSpinTPD()
        btnFecha2.setOnClickListener() {
            val Calendario =
                reservaCita.DatePickerFragment { year, month, day ->
                    verResultado(year, month, day)
                }
            Calendario.show(childFragmentManager, "DatePicker")
        }
        //DOCTOR
        if (nivelC == 1) {
            SpinEsp()
            cargarDataDoc()
            HabilitDoc(false)
        }
        if (nivelC == 2) { //SECRETARIA
            lbEsp.isVisible = false
            spinEsp4.isVisible = false
            cargarDataSec()
            HabilitSec(false)
        }
        btnEditar4.setOnClickListener() {
            if (nivelC == 1) {
                if (btnGuardar4.isVisible) {
                    btnEditar4.text = "Editar"
                    HabilitDoc(false)
                } else {
                    btnEditar4.text = "Cancelar"
                    HabilitDoc(true)
                }
            }
            if(nivelC==2){
                if (btnGuardar4.isVisible) {
                    btnEditar4.text = "Editar"
                    HabilitSec(false)
                } else {
                    btnEditar4.text = "Cancelar"
                    HabilitSec(true)
                }
            }
        }
        spinQuin4.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                horaM = spinQuin.getItemAtPosition(position).toString()
                txtHora4.setText(spinEnt4.selectedItem.toString() + ":" + horaM)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        spinEnt4.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                horaN = spinEnt.getItemAtPosition(position).toString()
                txtHora4.setText(horaN + ":" + spinQuin4.selectedItem.toString())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        btnGuardar4.setOnClickListener() {

        }
    }

    fun SpinEsp() {
        try {

            speciL.clear()
            val cadena = "select * from tbEspecialidades;"
            val st: ResultSet
            val ps: PreparedStatement = conx.dbConn()?.prepareStatement(cadena)!!
            st = ps.executeQuery()
            //LLENAR SPINNER
            while (st.next()) {
                val idEsp = st.getString("idEspecialidad").toInt()
                val especi = st.getString("especialidad")
                speciL.add(spec(idEsp, "$especi"))
            }
            LLenarSpinEsp()
            conx.dbConn()!!.close()

        } catch (ex: SQLException) {
            Log.e("Error: ", ex.message!!)
            Toast.makeText(context, "No existen especialidades", Toast.LENGTH_SHORT).show()
        }
    }

    fun cargarDataDoc() {
        try {
            val adaptEsp = LLenarSpinEsp()
            val adaptSe = LLenarSpinSe()
            val adaptTPD = LLenarSpinTPD()
            var st: ResultSet
            val cadena =
                "select usuario,correo,especialidad,nombres,apellidos,tipodocum,numdocum,nacimiento,sexo,telefono " +
                        "from tbDoctores c,tbUsuarios u,tbEspecialidades e " +
                        "where c.idUsuario=u.idUsuario and c.idEspecialidad=e.idEspecialidad and idDoctor=?;"
            val ps: PreparedStatement = conx.dbConn()?.prepareStatement(cadena)!!
            ps.setInt(1, idCuenta)
            st = ps.executeQuery()
            st.next()
            txtUsuario4.setText(st.getString("usuario"))
            txtMail4.setText(st.getString("correo"))
            txtNombres4.setText(st.getString("nombres"))
            txtApellid4.setText(st.getString("apellidos"))
            txtNacimiento4.setText(st.getString("nacimiento"))
            spinSexo4.setSelection(adaptSe.getPosition(st.getString("sexo")))
            spinEsp4.setSelection(adaptEsp.getPosition(st.getString("especialidad")))
            txtTelf4.setText(st.getString("telefono"))
            spintipoDocum4.setSelection(adaptTPD.getPosition(st.getString("tipodocum")))
            txtnumDocum4.setText(st.getString("numdocum"))


        } catch (ex: SQLException) {
            Log.e("Error: ", ex.message!!)
            Toast.makeText(context, "Error al cargar", Toast.LENGTH_SHORT).show()
        }
        conx.dbConn()!!.close()
    }

    fun cargarDataSec() {
        try {
            val adaptSe = LLenarSpinSe()
            val adaptTPD = LLenarSpinTPD()
            var st: ResultSet
            val cadena =
                "select usuario,correo,nombres,apellidos,tipodocum,numdocum,nacimiento,sexo,telefono " +
                        "from tbSecretarias c,tbUsuarios u where c.idUsuario=u.idUsuario and idSecretaria=?;"
            val ps: PreparedStatement = conx.dbConn()?.prepareStatement(cadena)!!
            ps.setInt(1, idCuenta)
            st = ps.executeQuery()
            st.next()
            txtUsuario4.setText(st.getString("usuario"))
            txtMail4.setText(st.getString("correo"))
            txtNombres4.setText(st.getString("nombres"))
            txtApellid4.setText(st.getString("apellidos"))
            txtNacimiento4.setText(st.getString("nacimiento"))
            spinSexo4.setSelection(adaptSe.getPosition(st.getString("sexo")))
            txtTelf4.setText(st.getString("telefono"))
            spintipoDocum4.setSelection(adaptTPD.getPosition(st.getString("tipodocum")))
            txtnumDocum4.setText(st.getString("numdocum"))

        } catch (ex: SQLException) {
            Log.e("Error: ", ex.message!!)
            Toast.makeText(context, "Error al cargar", Toast.LENGTH_SHORT).show()
        }
        conx.dbConn()!!.close()
    }

    fun LLenarSpinSe(): ArrayAdapter<String> {
        val adaptadorSpinner =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, sexo)
        adaptadorSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val spinner = requireView().findViewById<Spinner>(R.id.spinSexo4)
        spinner.adapter = adaptadorSpinner
        return adaptadorSpinner
    }

    fun LLenarSpinEsp(): ArrayAdapter<String> {
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item,
                speciL.map { it.nombre })
        spinEsp4.adapter = adapter

        return adapter
    }

    fun LLenarSpinTPD(): ArrayAdapter<String> {
        val adaptadorSpinner =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, tipodoc)
        adaptadorSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val spinner = requireView().findViewById<Spinner>(R.id.spinTipoD4)
        spinner.adapter = adaptadorSpinner
        return adaptadorSpinner
    }

    private fun verResultado(year: Int, month: Int, day: Int) {
        val mes = month + 1
        fechaSql = "$year-$mes-$day"
        txtNacimiento4?.setText("$day-$mes-$year")

    }

    class DatePickerFragment(val listener: (year: Int, month: Int, day: Int) -> Unit) :
        DialogFragment(),
        DatePickerDialog.OnDateSetListener {

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            return DatePickerDialog(requireActivity(), this, year, month, day)
        }

        override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
            listener(year, month, day)
        }
    }
    fun SpinHora() {
        val adaptadorSpinner =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, hora)
        adaptadorSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val spinner = requireView().findViewById<Spinner>(R.id.spinEnt4)
        spinner.adapter = adaptadorSpinner

        val adaptadorSpinner2 =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, hora)
        adaptadorSpinner2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val spinner2 = requireView().findViewById<Spinner>(R.id.spinQuin4)
        spinner2.adapter = adaptadorSpinner2
    }
    fun HabilitDoc(tf: Boolean) {
        txtUsuario4.isEnabled = tf
        txtMail4.isEnabled = tf
        txtNombres4.isEnabled = tf
        btnFecha2.isVisible = tf
        txtApellid4.isEnabled = tf
        spinSexo4.isEnabled = tf
        spinEsp4.isEnabled = tf
        txtTelf4.isEnabled = tf
        spinEnt4.isEnabled = tf
        spinQuin4.isEnabled = tf
        spintipoDocum4.isEnabled = tf
        txtnumDocum4.isEnabled = tf
        btnGuardar4.isVisible = tf
    }

    fun HabilitSec(tf: Boolean) {
        txtUsuario4.isEnabled = tf
        txtMail4.isEnabled = tf
        txtNombres4.isEnabled = tf
        btnFecha2.isVisible = tf
        txtApellid4.isEnabled = tf
        spinSexo4.isEnabled = tf
        txtTelf4.isEnabled = tf
        spinEnt4.isEnabled = tf
        spinQuin4.isEnabled = tf
        spintipoDocum4.isEnabled = tf
        txtnumDocum4.isEnabled = tf
        btnGuardar4.isVisible = tf
    }
}