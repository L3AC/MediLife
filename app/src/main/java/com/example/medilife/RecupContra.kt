package com.example.medilife

import android.annotation.SuppressLint
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat

lateinit var volver: ImageButton
lateinit var usco:EditText
lateinit var env:Button

class RecupContra : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recup_contra)

        volver = findViewById(R.id.btnVolver2)
        usco= findViewById(R.id.txtUC)
        env= findViewById(R.id.btnEnvi)

        volver.setOnClickListener{
            val scndAct = Intent(this,MainActivity::class.java)
            startActivity(scndAct)
        }

    }
}