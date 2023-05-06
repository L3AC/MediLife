package com.example.medilife

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.util.*

//CAMPOS
lateinit var volv: ImageButton
lateinit var bingresar: Button
lateinit var usu: EditText
lateinit var contra1: EditText
lateinit var contra2: EditText
lateinit var correo: EditText
lateinit var nomb: EditText
lateinit var apell: EditText
lateinit var tel: EditText
lateinit var tpsexo: Spinner
lateinit var bFecha: ImageButton
lateinit var tpsangre: EditText
lateinit var tpdoc: Spinner
lateinit var ndoc: EditText
lateinit var patol: EditText
lateinit var tNaci: EditText

//VARIABLES GLOBALES
lateinit var textAdv: TextView
lateinit var textAdv2: TextView


class RegistroMain : AppCompatActivity() {
    private var conx = Conx()
    private var idus: Int = 0
    private var fechaSql: String = ""
    private var spinText: String = ""

    val sexo = listOf("Femenino", "Masculino")
    val tipodoc = listOf("DUI", "Pasaporte")

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_main)
        textAdv = findViewById(R.id.lbAdver)
        textAdv2 = findViewById(R.id.lbAdver2)
        textAdv.isVisible = false
        textAdv2.isVisible = false

        volv = findViewById(R.id.btnVolver)
        bingresar = findViewById(R.id.btnConfirm)
        usu = findViewById(R.id.txtUs)
        tNaci = findViewById(R.id.txtNaci)
        contra1 = findViewById(R.id.txtContra1)
        contra2 = findViewById(R.id.txtContra2)
        correo = findViewById(R.id.txtCorreo)
        nomb = findViewById(R.id.txtNomb)
        apell = findViewById(R.id.txtApellidos)
        tel = findViewById(R.id.txtTel)
        tpsexo = findViewById(R.id.spinSexo)
        bFecha = findViewById(R.id.btnNaci)
        tpsangre = findViewById(R.id.txtTipoS)
        tpdoc = findViewById(R.id.spinTD)
        ndoc = findViewById(R.id.txtDoc)
        patol = findViewById(R.id.txtPatologias)


        //LLENAR SPINNER
        LLenarSpin()


        volv.setOnClickListener {
            val scndAct = Intent(this, MainActivity::class.java)
            startActivity(scndAct)
        }
        bingresar.setOnClickListener {
            createUs()
            selectUs()
            createCl()
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(bingresar.windowToken, 0)

            Toast.makeText(applicationContext, "Cuenta creada exitosamente", Toast.LENGTH_SHORT).show()
            val scndAct = Intent(this, MainActivity::class.java)
            startActivity(scndAct)
        }
        bFecha.setOnClickListener {
            val Calendario =
                DatePickerFragment { year, month, day -> verResultado(year, month, day) }
            Calendario.show(supportFragmentManager, "DatePicker")
        }
        usu.setOnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                verifUs()
            }
        }

        contra2.setOnFocusChangeListener(){ view, hasFocus ->
            if (!hasFocus) {
                verifContra()
            }
            contra1.setOnFocusChangeListener(){ view, hasFocus ->
                if (!hasFocus) {
                    verifContra()
                }
            }
        }

    }
    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)

    }

    fun createUs() {

        try {
            val cadena: String = "insert into tbUsuarios(idTipo,usuario,contra,correo) " +
                    "values(3,?,?,?);"

            val ps: PreparedStatement = conx.dbConn()?.prepareStatement(cadena)!!

            ps.setString(1, usu.text.toString())
            ps.setString(2, contra2.text.toString())
            ps.setString(3, correo.text.toString())

            ps.executeUpdate()

        } catch (ex: SQLException) {
            Log.e("Error: ", ex.message!!)
            Toast.makeText(applicationContext, "Errorsito", Toast.LENGTH_SHORT).show()
        }
        conx.dbConn()!!.close()

    }
    fun selectUs(){
        try {
            val cadena: String = "select * from tbUsuarios where usuario=? COLLATE SQL_Latin1_General_CP1_CS_AS" +
                    " and idTipo=3;"
            val st: ResultSet
            val ps: PreparedStatement = conx.dbConn()?.prepareStatement(cadena)!!

            ps.setString(1, usu.text.toString())
            st = ps.executeQuery()
            st.next()

            val found = st.row
            if (found == 1) {
                idus = st.getInt("idUsuario")

            } else {
                Toast.makeText(applicationContext, "Error de inserción", Toast.LENGTH_SHORT).show()
            }
        } catch (ex: SQLException) {
            Log.e("Error: ", ex.message!!)
            Toast.makeText(applicationContext, "Error interno", Toast.LENGTH_SHORT).show()
        }
        conx.dbConn()!!.close()
    }

    fun createCl() {

        try {
            val cadena: String =
                "insert into tbClientes(idUsuario,nombres,apellidos,tipodocum,numdocum," +
                        "nacimiento,sexo,telefono,tipoSangre,patologias) " +
                        "values (?,?,?,?,?,?,?,?,?,?);"

            val ps: PreparedStatement = conx.dbConn()?.prepareStatement(cadena)!!

            ps.setString(1, idus.toString())
            ps.setString(2, nomb.text.toString())
            ps.setString(3, apell.text.toString())
            ps.setString(4, tpdoc.selectedItem.toString())
            ps.setString(5, ndoc.text.toString())
            ps.setString(6, fechaSql)
            ps.setString(7, tpsexo.selectedItem.toString())
            ps.setString(8, tel.text.toString())
            ps.setString(9, tpsangre.text.toString())
            ps.setString(10, patol.text.toString())

            ps.executeUpdate()

        } catch (ex: SQLException) {
            Log.e("Error: ", ex.message!!)
            Toast.makeText(applicationContext, "Errorsito", Toast.LENGTH_SHORT).show()
        }
        conx.dbConn()!!.close()

    }

    fun verifUs() {
        try {
            val cadena: String = "select * from tbUsuarios where usuario=?;"
            val st: ResultSet
            val ps: PreparedStatement = conx.dbConn()?.prepareStatement(cadena)!!

            ps.setString(1, usu.text.toString())
            st = ps.executeQuery()
            st.next()

            val found = st.row
            if (found == 1) {
                textAdv.isVisible = true
                Toast.makeText(applicationContext, "Ya existe usuario", Toast.LENGTH_SHORT).show()

            } else {
                textAdv.isVisible = false
                bingresar.isEnabled = false
            }
        } catch (ex: SQLException) {
            Log.e("Error: ", ex.message!!)
            Toast.makeText(applicationContext, "Error interno", Toast.LENGTH_SHORT).show()
        }
        conx.dbConn()!!.close()
    }

    fun verifContra() {
        if (contra1.text.toString() != contra2.text.toString()) {
            textAdv2.isVisible=true
            bingresar.isEnabled=false
        }
        else {
            textAdv2.isVisible=false
            bingresar.isEnabled=true
        }

    }

    fun LLenarSpin() {
        val adaptadorSpinner = ArrayAdapter(this, android.R.layout.simple_spinner_item, sexo)
        adaptadorSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val spinner = findViewById<Spinner>(R.id.spinSexo)
        spinner.adapter = adaptadorSpinner

        val adaptadorSpinner2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, tipodoc)
        adaptadorSpinner2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val spinner2 = findViewById<Spinner>(R.id.spinTD)
        spinner2.adapter = adaptadorSpinner2
    }

    /*//Para ocultar el teclado
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(Boton.windowToken, 0)
}*/

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