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
lateinit var elpepe67:EditText

class RecupContra : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recup_contra)

        volver = findViewById(R.id.btnVolver2)
        usco = findViewById(R.id.txtUC)
        env = findViewById(R.id.btnEnvi)

        env.setOnClickListener{
            val task = SendMailTask("correo_destinatario@gmail.com", "Aqui esta el link para la recuperacion de contraseña", "Fuckyu")
            task.execute()
        }

        volver.setOnClickListener {
            val scndAct = Intent(this, MainActivity::class.java)
            startActivity(scndAct)


                }

            }
        }

