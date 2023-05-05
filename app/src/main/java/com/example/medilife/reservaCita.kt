package com.example.medilife

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.media.Image
import android.os.Bundle
import android.text.method.TextKeyListener.clear
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.util.*

class espeL(val id: Int, val nombre: String)

val especL = mutableListOf<espeL>()

class doc(val id: Int, val nombre: String)

val doctores = mutableListOf<doc>()
lateinit var cbEsp:Spinner
lateinit var cbDoc:Spinner
lateinit var lbHorario:TextView
lateinit var bFecha2:ImageButton
lateinit var lbDispo:TextView

private var spinDoc:String=""
private var conx = Conx()
private var nEsp:String=""
private var idEsp:Int=0
private var nDoctor:String=""
private var idDoctor:Int=0
private var idDoc:Int=0
private var idCliente:Int=0
private var fechaSql: String = ""

class reservaCita : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idCliente = arguments?.getInt("idcu")!!

        }
    }

   override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       return inflater.inflate(R.layout.fragment_reserva_cita, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cbEsp=requireView().findViewById(R.id.spinEsp)
        cbDoc=requireView().findViewById(R.id.spinDoc)
        lbHorario=requireView().findViewById(R.id.txvHorario)
        bFecha2=requireView().findViewById(R.id.btnFecha)
        lbDispo=requireView().findViewById(R.id.txvDispo)
        cbDoc.isEnabled=false
        lbHorario.isVisible=false
        lbDispo.isVisible=false

        SpinEsp(cbEsp)

    }
    fun SpinEsp(cb: Spinner) {
        try {
            val cadena = "select * from tbEspecialidades;"
            val st: ResultSet
            val ps: PreparedStatement = conx.dbConn()?.prepareStatement(cadena)!!


            st = ps.executeQuery()
            //LLENAR SPINNER
            while (st.next()) {
                val idEsp = st.getString("idEspecialidad").toInt()
                val especi = st.getString("especialidad")
                especL.add(espeL(idEsp, "$especi"))
            }
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item,
                especL.map { it.nombre })
            cb.adapter = adapter

            conx.dbConn()!!.close()
            cbDoc.isEnabled=true //HABILITAR EL OTRO SPIN

            cb.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    spinDoc = parent.getItemAtPosition(position).toString()
                    val espc = especL[position]
                    nEsp = espc.nombre
                    idEsp = espc.id
                    SpinDoc(cbDoc)

                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

            }

        } catch (ex: SQLException) {
            Log.e("Error: ", ex.message!!)
            Toast.makeText(context, "No existen especialidades", Toast.LENGTH_SHORT).show()
        }
    }
    fun SpinDoc(cb: Spinner) {
        try {
            val cadena = "select idDoctor,CONCAT(nombres,' ',apellidos) as nombre " +
                    "from tbDoctores where idEspecialidad=?;"
            val st: ResultSet
            val ps: PreparedStatement = conx.dbConn()?.prepareStatement(cadena)!!
            ps.setString(1,idEsp.toString())

            st = ps.executeQuery()
            //LLENAR SPINNER
            doctores.clear()
            while (st.next()) {
                idDoc = st.getString("idDoctor").toInt()
                val nombre = st.getString("nombre")
                doctores.add(doc(idDoc, "$nombre"))
            }
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item,
                doctores.map { it.nombre })

            cb.adapter = adapter

            conx.dbConn()!!.close()

            cb.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    //adapter.clear()

                    spinDoc = parent.getItemAtPosition(position).toString()
                    val doct = doctores[position]
                    nDoctor = doct.nombre
                    idDoctor = doct.id
                    HorarioLab()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

            }

        } catch (ex: SQLException) {
            Log.e("Error: ", ex.message!!)
            Toast.makeText(context, "No existen doctores", Toast.LENGTH_SHORT).show()
        }
    }
    fun HorarioLab(){
        try {
            val cadena: String = "select * from tbDoctores where idDoctor=?;"
            val st: ResultSet
            val ps: PreparedStatement = conx.dbConn()?.prepareStatement(cadena)!!

            ps.setString(1, idDoctor.toString())
            st = ps.executeQuery()
            st.next()

            val found = st.row
            if (found == 1) {
                lbHorario.isVisible=true
                lbHorario.text= st.getString("horarioLaboral")
            } else {
                Toast.makeText(context, "Datos incorrectos", Toast.LENGTH_SHORT).show()
            }
        } catch (ex: SQLException) {
            Log.e("Error: ", ex.message!!)
            Toast.makeText(context, "Errorsito", Toast.LENGTH_SHORT).show()
        }
        conx.dbConn()!!.close()
    }
    private fun verResultado(year: Int, month: Int, day: Int) {
        val mes = month + 1
        fechaSql = "$year-$mes-$day"
        tNaci?.setText("$day-$mes-$year")

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
}