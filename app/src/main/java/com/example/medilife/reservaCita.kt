package com.example.medilife

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

class espeL(val id: Int, val nombre: String)

val especL = mutableListOf<espeL>()

class doc(val id: Int, val nombre: String)

val doctores = mutableListOf<doc>()
lateinit var cbEsp:Spinner
lateinit var cbDoc:Spinner
lateinit var lbHorario:TextView

private var spinDoc:String=""
private var conx = Conx()
private var nEsp:String=""
private var idEsp:Int=0
private var nDoctor:String=""
private var idDoctor:Int=0

private var idCliente:Int=0

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
        cbDoc.isEnabled=false
        lbHorario.isVisible=false

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
                    //LLENAR EL OTRO SPIN
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
            while (st.next()) {
                val idDoc = st.getString("idDoctor").toInt()
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
                    spinDoc = parent.getItemAtPosition(position).toString()
                    val doct = doctores[position]
                    nDoctor = doct.nombre
                    idDoctor = doct.id

                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

            }

        } catch (ex: SQLException) {
            Log.e("Error: ", ex.message!!)
            Toast.makeText(context, "No existen doctores", Toast.LENGTH_SHORT).show()
        }
    }
}