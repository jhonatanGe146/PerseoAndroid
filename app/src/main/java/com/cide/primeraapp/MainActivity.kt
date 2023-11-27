package com.cide.primeraapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv_irRegistro = findViewById<TextView>(R.id.irregistro)
        val btn_login = findViewById<Button>(R.id.btn_login)

        tv_irRegistro.setOnClickListener {
            val intent = Intent(applicationContext, registro::class.java)
            startActivity(intent)
        }
        btn_login.setOnClickListener {
            val intent = Intent(applicationContext, Menu_Principal::class.java)
            startActivity(intent)
        }

    }


}