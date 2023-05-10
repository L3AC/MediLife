package com.example.medilife

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import java.util.Properties
import java.util.Scanner
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage


lateinit var volver: ImageButton
lateinit var usco:EditText
lateinit var env:Button

class RecupContra : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recup_contra)

        volver = findViewById(R.id.btnVolver2)
        usco = findViewById(R.id.txtUC)
        env = findViewById(R.id.btnEnvi)

        volver.setOnClickListener {
            val scndAct = Intent(this, MainActivity::class.java)
            startActivity(scndAct)

            env.setOnClickListener {
                val hola = Intent(this, MainActivity::class.java)
                startActivity(hola)

                fun verificarUsuarioENBD(usuario: String): Boolean {
                    return true
                }

                fun main() {
                    val scanner = Scanner(System.`in`)
                    println("Ingresa tu correo electronico")
                    val email = scanner.nextLine()

                    println("Ingresa tu nombre de usuario: ")
                    val usuario = scanner.nextLine()

                    if (!verificarUsuarioENBD(usuario)) {
                        println("El usuario ingresado no existe")
                        return
                    }

                    // Aquí se realizaría la lógica de buscar el usuario y obtener su contraseña anterior
                    val oldPassword = "miPasswordAnterior123"
val elpepe:String=""
                    val props = Properties()
                    props["mail.smtp.host"] = "smtp.gmail.com"
                    props["mail.smtp.socketFactory.port"] = "465"
                    props["mail.smtp.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory"
                    props["mail.smtp.auth"] = "true"
                    props["mail.smtp.port"] = "465"

                    val session = Session.getDefaultInstance(props, object : javax.mail.Authenticator() {
                        override fun getPasswordAuthentication(): PasswordAuthentication {
                            return PasswordAuthentication("egrande447@gmail.com", "vwkpxcomkphircba")
                        }
                    })

                    try {
                        val message = MimeMessage(session)
                        message.setFrom(InternetAddress("miCorreo@gmail.com"))
                        message.addRecipient(Message.RecipientType.TO, InternetAddress(email))
                        message.subject = "Recuperación de contraseña"
                        message.setText("Tu contraseña anterior es: $oldPassword")

                        Transport.send(message)

                        println("Mensaje enviado correctamente")
                    } catch (e: MessagingException) {
                        e.printStackTrace()
                    }
                }

            }
        }
    }
}