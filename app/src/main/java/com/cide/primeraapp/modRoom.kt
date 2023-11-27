package com.cide.primeraapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class modRoom : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mod_room)

        val btn_volver = findViewById<Button>(R.id.btn_volver)

        btn_volver.setOnClickListener {
            startActivity(Intent(applicationContext, Menu_Principal::class.java))
        }
    }
}