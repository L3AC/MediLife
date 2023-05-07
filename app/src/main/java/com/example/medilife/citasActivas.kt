package com.example.medilife

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import java.sql.SQLException

lateinit var ListVista1: ListView
val myData = mutableListOf<String>()

class citasActivas : Fragment() {
    var idCuenta: Int = 0
    private var conx = Conx()

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
        return inflater.inflate(R.layout.fragment_citas_activas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    fun Tabla() {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, myData)
        ListVista1.adapter = adapter
        myData.clear()
        try {
            val statement = conx.dbConn()?.createStatement()
            val resulSet =
                statement?.executeQuery("select * from tbCitas where fechahora>GETDATE();")

            while (resulSet?.next() == true) {

                val column1 = resulSet.getString("hora")
                val column2 = resulSet.getString("codigo")

                val newElement = "$column1, $column2"

                myData.add(newElement)

                adapter.notifyDataSetChanged()

            }
            ListVista1.visibility = View.VISIBLE
        } catch (ex: SQLException) {
            Toast.makeText(context, "Error al mostrar", Toast.LENGTH_SHORT).show()
        }
    }


}