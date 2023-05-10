package com.example.medilife

import android.annotation.SuppressLint
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import  java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.util.Properties
import java.util.Scanner
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

/*import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MailDateFormat
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.sun.mail.iap.ByteArray
import com.sun.mail.imap.protocol.BODY
import java.lang.RuntimeException
import javax.mail.internet.MimeMessage
import javax.security.auth.Subject*/


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
                        message.setFrom(InternetAddress("egrande447@gmail.com"))
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