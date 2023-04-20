package com.example.medilife

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

lateinit var editUsu: EditText
lateinit var editContra: EditText
lateinit var bIngresar: Button

class MainActivity : AppCompatActivity() {
    private var conx = Conx()
    var idTipo: Int = 0
    var idUs: Int = 0
    var idCuenta: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editUsu = findViewById(R.id.txtUsuario)
        editContra = findViewById(R.id.txtContra)
        bIngresar = findViewById(R.id.btnIngresar)

        val hola: String = "Prueba 234 KIO23"
        bIngresar.setOnClickListener {

            VerifTipo()
            VerifDatos()

        }

    }

    fun VerifTipo() {
        try {
            val cadena: String = "select * from tbUsuarios where usuario='poji' " +
                    "COLLATE SQL_Latin1_General_CP1_CS_AS and contra='poji' COLLATE SQL_Latin1_General_CP1_CS_AS"
            val st: ResultSet
            val ps: PreparedStatement = conx.dbConn()?.prepareStatement(cadena)!!

            ps.setString(1, editUsu.text.toString())
            ps.setString(2, editContra.text.toString())
            st = ps.executeQuery()
            st.next()

            val found = st.row
            if (found == 1) {
                idTipo = st.getInt("idTipo")//Si es secretaria, doctor o cliente
                idUs = st.getInt("idUsuario")
                //Toast.makeText(applicationContext,"Acceso completado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "Datos incorrectos", Toast.LENGTH_SHORT).show()
            }
        } catch (ex: SQLException) {
            Log.e("Error: ", ex.message!!)
            Toast.makeText(applicationContext, "Errorsito", Toast.LENGTH_SHORT).show()
        }
        conx.dbConn()!!.close()
    }

    fun VerifDatos() {
        val scndAct = Intent(this, MainInside::class.java)
        try {
            //DOCTORES
            if (idTipo == 1) {
                val cadena: String = "select idDoctor from tbDoctores inner join tbUsuarios " +
                        "on tbDoctores.idUsuario=tbUsuarios.idUsuario\n" +
                        "where tbDoctores.idUsuario=?;"
                val st: ResultSet
                val ps: PreparedStatement = conx.dbConn()?.prepareStatement(cadena)!!

                ps.setString(1, idUs.toString())
                st = ps.executeQuery()
                st.next()

                val found = st.row
                if (found == 1) {
                    idCuenta = st.getInt("idDoctor")//Obtener id
                    Toast.makeText(applicationContext, "Acceso completado", Toast.LENGTH_SHORT)
                        .show()
                    scndAct.putExtra("idCuenta", idCuenta)
                    startActivity(scndAct)
                } else {
                    Toast.makeText(applicationContext, "Datos incorrectos", Toast.LENGTH_SHORT)
                        .show()
                }
                conx.dbConn()!!.close()
            }
            //SECRETARIAS
            if (idTipo == 2) {
                val cadena: String = "select idSecretaria from tbSecretarias inner join " +
                        "tbUsuarios on tbSecretarias.idUsuario=tbUsuarios.idUsuario\n" +
                        "where tbSecretarias.idUsuario=?;"
                val st: ResultSet
                val ps: PreparedStatement = conx.dbConn()?.prepareStatement(cadena)!!

                ps.setString(1, idUs.toString())
                st = ps.executeQuery()
                st.next()

                val found = st.row
                if (found == 1) {
                    idCuenta = st.getInt("idSecretaria")
                    scndAct.putExtra("idCuenta", idCuenta)
                    startActivity(scndAct)
                    Toast.makeText(applicationContext, "Acceso completado", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(applicationContext, "Datos incorrectos", Toast.LENGTH_SHORT)
                        .show()
                }
                conx.dbConn()!!.close()
            }
            //CLIENTES
            if (idTipo == 3) {
                val cadena: String = "select idCliente from tbClientes inner join \n" +
                        "tbUsuarios on tbClientes.idUsuario=tbUsuarios.idUsuario\n" +
                        "where tbClientes.idUsuario=?;;"
                val st: ResultSet
                val ps: PreparedStatement = conx.dbConn()?.prepareStatement(cadena)!!

                ps.setString(1, idUs.toString())
                st = ps.executeQuery()
                st.next()

                val found = st.row
                if (found == 1) {
                    idCuenta = st.getInt("idCliente")
                    scndAct.putExtra("idCuenta", idCuenta)
                    startActivity(scndAct)
                    Toast.makeText(applicationContext, "Acceso completado", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(applicationContext, "Datos incorrectos", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        } catch (ex: SQLException) {
            Log.e("Error: ", ex.message!!)
            Toast.makeText(applicationContext, "Errorsito", Toast.LENGTH_SHORT).show()
        }
        conx.dbConn()!!.close()
    }
}


