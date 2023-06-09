package com.example.medilife

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

lateinit var txtMedico: EditText
lateinit var txtEsp: EditText
lateinit var txtFecha2: EditText
lateinit var txtHora2: EditText
lateinit var txtNombre: EditText
lateinit var btnInfo: Button
lateinit var btnCancelar: Button
lateinit var btnAtender: Button


class infoCita : Fragment() {
    var idCuenta: Int = 0
    var idCita: Int = 0
    var nivelC: Int = 0
    var idCliente: Int = 0
    var estadoC: Int = 0
    private var conx = Conx()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idCuenta = arguments?.getInt("idcu")!!
            idCita = arguments?.getInt("idcita")!!
            nivelC = arguments?.getInt("nvc")!!
            estadoC = arguments?.getInt("estado")!!
            Log.i("id", idCuenta.toString())
            Log.i("nivel ", nivelC.toString())
            Log.i("k", idCita.toString())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_info_cita, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txtMedico = requireView().findViewById(R.id.txtMedico)
        txtEsp = requireView().findViewById(R.id.txtEsp)
        txtFecha2 = requireView().findViewById(R.id.txtFecha2)
        txtHora2 = requireView().findViewById(R.id.txtHora2)
        txtNombre = requireView().findViewById(R.id.txtNombre)
        btnInfo = requireView().findViewById(R.id.btnInfo)
        btnCancelar = requireView().findViewById(R.id.btnCancelar)
        btnAtender = requireView().findViewById(R.id.btnAtender)
        volver = requireView().findViewById(R.id.btnVolver7)


        volver = requireView().findViewById(R.id.btnVolver7)
        buscarID()
        CargarDatos()

        if (estadoC != 2) {//ACTIVA

            var bundle = Bundle().apply {
                putInt("idcu", idCuenta)
                putInt("idcita", idCita)
                putInt("idcl", idCliente)
                putInt("nvc", nivelC)
            }
            volver.setOnClickListener {
                findNavController().navigate(R.id.action_infoCita_to_citasActivas, bundle)
            }

            if (nivelC == 3) {
                btnInfo.isVisible = false
                btnAtender.isVisible = false
            }
            btnInfo.setOnClickListener() {
                var bundle2 = Bundle().apply {
                    putInt("idcu", idCuenta)
                    putInt("idcita", idCita)
                    putInt("idcl", idCliente)
                    putInt("nvc", nivelC)
                }
                findNavController().navigate(R.id.action_infoCita_to_infoClienteCita, bundle2)
                Log.i("id", idCuenta.toString())
                Log.i("cliente ", idCliente.toString())
            }
            btnAtender.setOnClickListener() {
                AtenderCita()
            }
            btnCancelar.setOnClickListener() {
                CancelarCita()
            }

        } else {//NO ES ACTIVA
            btnCancelar.isVisible = false
            btnAtender.isVisible = false
            var bundle = Bundle().apply {
                putInt("idcu", idCuenta)
                putInt("idcita", idCita)
                putInt("idcl", idCliente)
                putInt("nvc", nivelC)
                putInt("estado", estadoC)
            }
            volver.setOnClickListener {
                findNavController().navigate(R.id.action_infoCita_to_historialCitas, bundle)
            }

            if (nivelC == 3) {
                btnInfo.isVisible = false
            }
            btnInfo.setOnClickListener() {
                var bundle2 = Bundle().apply {
                    putInt("idcu", idCuenta)
                    putInt("idcita", idCita)
                    putInt("idcl", idCliente)
                    putInt("nvc", nivelC)
                    putInt("estado", estadoC)
                }
                findNavController().navigate(R.id.action_infoCita_to_infoClienteCita, bundle2)
                Log.i("id", idCuenta.toString())
                Log.i("cliente ", idCliente.toString())
            }
        }
    }

    fun CargarDatos() {
        try {
            var cadena: String =
                "select ci.idCliente,CONCAT(d.nombres,' ',d.apellidos) as Doctor,e.especialidad,FORMAT(fechahora,'dd-MM-yyyy') AS Fecha,\n" +
                        "FORMAT(fechahora,'hh:mm tt') as Hora, CONCAT(c.nombres,' ',c.apellidos) as Paciente from tbCitas ci,\n" +
                        "tbClientes c,tbDoctores d,tbEspecialidades e where ci.idCliente=c.idCliente and e.idEspecialidad=d.idEspecialidad \n" +
                        "and  ci.idDoctor=d.idDoctor and idCita=?;"
            var st: ResultSet
            val ps: PreparedStatement = conx.dbConn()?.prepareStatement(cadena)!!

            ps.setInt(1, idCita)

            st = ps.executeQuery()
            st.next()
            txtMedico.setText(st.getString("Doctor"))
            txtEsp.setText(st.getString("especialidad"))
            txtFecha2.setText(st.getString("Fecha"))
            txtHora2.setText(st.getString("Hora"))
            txtNombre.setText(st.getString("Paciente"))
            idCliente = st.getInt("idCliente")


        } catch (ex: SQLException) {
            Toast.makeText(context, "Error al mostrar", Toast.LENGTH_SHORT).show()
        }
    }

    fun AtenderCita() {
        try {
            var cadena: String =
                "update tbCitas set estado='Atendida' where idCita=?;"
            val ps: PreparedStatement = conx.dbConn()?.prepareStatement(cadena)!!

            ps.setInt(1, idCita)
            ps.executeUpdate()
            Toast.makeText(context, "Cita atendida", Toast.LENGTH_SHORT).show()


        } catch (ex: SQLException) {
            Toast.makeText(context, "Error al actualizar", Toast.LENGTH_SHORT).show()
        }
    }

    fun CancelarCita() {
        try {
            var cadena: String =
                "delete tbCitas  where idCita=?;"
            var st: ResultSet
            val ps: PreparedStatement = conx.dbConn()?.prepareStatement(cadena)!!
            Log.i("elim", idCita.toString())
            ps.setInt(1, idCita)

            ps.executeUpdate()
            Toast.makeText(context, "Cita eliminada", Toast.LENGTH_SHORT).show()
        } catch (ex: SQLException) {
            Log.i("elim", ex.message.toString())
            Toast.makeText(context, "Error al eliminar", Toast.LENGTH_SHORT).show()
        }
    }

    fun buscarID() {
        try {
            var cadena: String =
                "select idCliente from tbCitas where idCita=?;"
            var st: ResultSet
            val ps: PreparedStatement = conx.dbConn()?.prepareStatement(cadena)!!

            ps.setInt(1, idCita)

            st = ps.executeQuery()
            st.next()
            idCliente = st.getInt("idCliente")


        } catch (ex: SQLException) {
            Toast.makeText(context, "Error al buscar", Toast.LENGTH_SHORT).show()
        }
    }

}