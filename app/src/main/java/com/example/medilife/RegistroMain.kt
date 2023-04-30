package com.example.medilife

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.*
import androidx.fragment.app.DialogFragment
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.util.*

lateinit var volv: ImageButton
lateinit var bingresar:Button
lateinit var usu:EditText
lateinit var bFecha:ImageButton
lateinit var tFecha:EditText

private var nombus:String=""
private var fechaSql:String=""
private var spinText:String=""

val sexo = listOf("Femenino", "Masculino")

class RegistroMain : AppCompatActivity() {


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_main)
        volv = findViewById(R.id.btnVolver)
        bingresar = findViewById(R.id.btnReg)
        usu=findViewById(R.id.txtUs)
        bFecha=findViewById(R.id.btnFecha)
        tFecha=findViewById(R.id.txtFecha)

        //LLENAR SPINNER
        LLenarSpin()



        volv.setOnClickListener{
            val scndAct = Intent(this,MainActivity::class.java)
            startActivity(scndAct)
        }
        bingresar.setOnClickListener{
            val scndAct = Intent(this,MainActivity::class.java)
            startActivity(scndAct)
        }
        bFecha.setOnClickListener{
            val Calendario=DatePickerFragment{year, month, day ->verResultado(year,month,day) }
            Calendario.show(supportFragmentManager,"DatePicker")
        }

    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)

    }

    fun createUs(){
       val cadena:String="insert into tbUsuarios(idTipo,usuario,contra,correo) " +
               "values(1,'poji','poji','poji@gmail.com')"
    }
    fun LLenarSpin(){
        val adaptadorSpinner = ArrayAdapter(this, android.R.layout.simple_spinner_item,sexo)
        adaptadorSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val spinner = findViewById<Spinner>(R.id.spinSexo)
        spinner.adapter = adaptadorSpinner
    }
    /*
    fun Spin(cb: Spinner) {
        try {
            val cadena = "select * from tbDoctores;"
            val st: ResultSet
            val ps: PreparedStatement =conx.dbConn()?.prepareStatement(cadena)!!


            st = ps.executeQuery()
            //LLENAR SPINNER
            while (st.next()) {
                val idDoc = st.getString("idDoctor").toInt()
                val nombre = st.getString("nombre")
                doctores.add(doc(idDoc,"$nombre"))
            }
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,
                doctores.map { it.nombre })
            cb.adapter = adapter
            conx.dbConn()!!.close()

            cb.onItemSelectedListener=object:
                AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                    spinText = parent.getItemAtPosition(position).toString()
                    val doct = doctores[position]
                    nDoctor=doct.nombre
                    idDoctor = doct.id
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

            }

        }
        catch(ex: SQLException){
            Log.e("Error: ",ex.message!!)
            Toast.makeText(applicationContext,"No existen doctores", Toast.LENGTH_SHORT).show()
        }
    }*/
    fun Registrar(){

    }

    private fun verResultado(year: Int, month: Int, day: Int) {
        val mes=month+1
        fechaSql="$year-$mes-$day"
        tFecha?.setText("$day-$mes-$year")

    }

    class DatePickerFragment (val listener:(year:Int,month:Int,day:Int)->Unit): DialogFragment(),
        DatePickerDialog.OnDateSetListener{

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val c= Calendar.getInstance()
            val year=c.get(Calendar.YEAR)
            val month=c.get(Calendar.MONTH)
            val day=c.get(Calendar.DAY_OF_MONTH)

            return DatePickerDialog(requireActivity(),this,year,month,day)
        }

        override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
            listener(year,month,day)
        }

    }
}