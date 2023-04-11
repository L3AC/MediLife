package com.example.medilife

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var hola:String="hi";

        if(hola=="hi"){
             hola="one shot"
        }
        else{

        }
    }
}