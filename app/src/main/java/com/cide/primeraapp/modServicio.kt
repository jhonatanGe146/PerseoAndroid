package com.cide.primeraapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class modServicio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mod_servicio)
         val btn_volver = findViewById<Button>(R.id.btn_volver)
         val btn_agregar = findViewById<Button>(R.id.btn_agregar)
        btn_volver.setOnClickListener {
            startActivity(Intent(applicationContext, Menu_Principal::class.java))
        }

        btn_agregar.setOnClickListener {
            startActivity(Intent(applicationContext, registrar_servicio::class.java))

        }
    }
}