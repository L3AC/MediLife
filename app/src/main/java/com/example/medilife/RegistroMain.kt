package com.example.medilife

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast

lateinit var volv: ImageButton
lateinit var bingresar:Button
lateinit var usu:EditText
class RegistroMain : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_main)
        volv = findViewById(R.id.btnVolver)
        bingresar = findViewById(R.id.btnReg)
        usu=findViewById(R.id.txtUs)

        volv.setOnClickListener{
            val scndAct = Intent(this,MainActivity::class.java)
            startActivity(scndAct)
        }
        bingresar.setOnClickListener{
            val scndAct = Intent(this,MainActivity::class.java)
            startActivity(scndAct)
        }

    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
        usu.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                Toast.makeText(applicationContext, "NOT FOCUS", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun VerifUs(){

    }
    fun Registrar(){

    }
}