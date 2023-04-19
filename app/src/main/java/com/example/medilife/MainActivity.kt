package com.example.medilife

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

lateinit var editUsu:EditText
lateinit var editContra:EditText
lateinit var bIngresar:Button
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editUsu=findViewById(R.id.txtUsuario)
        editContra=findViewById(R.id.txtContra)
        bIngresar=findViewById(R.id.btnIngresar)

        bIngresar.setOnClickListener{

        }

    }
    fun Ingresar(){
        //val intent = Intent(this, ::class.java)
    }
}