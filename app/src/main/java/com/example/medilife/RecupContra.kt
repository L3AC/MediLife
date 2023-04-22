package com.example.medilife

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat

class RecupContra : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recup_contra)

    val imageButton = findViewById<ImageButton>(R.id.imageButton4)
        imageButton.setBackgroundColor(ContextCompat.getColor(this,android.R.color.transparent))

        val textView = findViewById<TextView>(R.id.txtUC)
        textView.setBackgroundColor(ContextCompat.getColor(this,android.R.color.white))
    }
}