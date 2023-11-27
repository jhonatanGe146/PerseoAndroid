package com.cide.primeraapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class modUser : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mod_user)

        val btn_volver = findViewById<Button>(R.id.btn_volver)
        val btn_consultar = findViewById<Button>(R.id.btn_consultar)


        btn_volver.setOnClickListener {
            startActivity(Intent(applicationContext, Menu_Principal::class.java))
        }
        btn_consultar.setOnClickListener {
            startActivity(Intent(applicationContext, select_user::class.java))
        }
    }
}