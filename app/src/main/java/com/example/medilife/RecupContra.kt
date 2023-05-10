package com.example.medilife

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException


lateinit var volver: ImageButton
lateinit var txtUsuario: EditText
lateinit var env: Button

class RecupContra : AppCompatActivity() {
    private var conx = Conx()
    private var correo: String = ""
    private var contra: String = ""

    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recup_contra)

        volver = findViewById(R.id.btnVolver2)
        txtUsuario = findViewById(R.id.txtUsuario2)
        env = findViewById(R.id.btnEnvi)

        env.setOnClickListener {
            BuscarCorreo()
        }

        volver.setOnClickListener {
            val scndAct = Intent(this, MainActivity::class.java)
            startActivity(scndAct)
        }
    }

    fun BuscarCorreo() {
        try {
            val cadena: String = "select correo,contra from tbUsuarios where usuario=? " +
                    "COLLATE SQL_Latin1_General_CP1_CS_AS;"
            val st: ResultSet
            val ps: PreparedStatement = conx.dbConn()?.prepareStatement(cadena)!!

            ps.setString(1, txtUsuario.text.toString())
            st = ps.executeQuery()
            st.next()

            val found = st.row
            if (found == 1) {
                correo = st.getString("correo")
                contra = st.getString("contra")
                val task = SendMailTask(
                    correo,
                    "Aqui esta el link para la recuperacion de contraseña",
                    "Su contraseña es: $contra, pero se le recomienda que la cambie lo antes posible"
                )
                task.execute()
                Toast.makeText(
                    applicationContext,
                    "Mensaje enviado a su correo",
                    Toast.LENGTH_SHORT
                ).show()
                val scndAct = Intent(this, MainActivity::class.java)
                startActivity(scndAct)
            } else {
                Toast.makeText(applicationContext, "Usuario incorrecto", Toast.LENGTH_SHORT).show()
            }
        } catch (ex: SQLException) {
            Log.e("Error: ", ex.message!!)
            Toast.makeText(applicationContext, "Error interno", Toast.LENGTH_SHORT).show()
        }
        conx.dbConn()!!.close()
    }
}

