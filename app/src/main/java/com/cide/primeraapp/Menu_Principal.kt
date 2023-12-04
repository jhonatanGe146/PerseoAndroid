package com.cide.primeraapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Menu_Principal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_principal)
        val btn_cerrarSesion = findViewById<Button>(R.id.btn_cerrarSesion)
        val btn_usuario = findViewById<Button>(R.id.btn_usuario)
        val btn_habitacion = findViewById<Button>(R.id.btn_habitacion)
        val btn_servicios = findViewById<Button>(R.id.btn_servicios)

        btn_cerrarSesion.setOnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }
        btn_usuario.setOnClickListener {
            startActivity(Intent(applicationContext, select_user::class.java))
        }
        btn_habitacion.setOnClickListener {
            startActivity(Intent(applicationContext, mostrar_habitacion::class.java))
        }

        btn_servicios.setOnClickListener {
            startActivity(Intent(applicationContext, mostrar_servicios::class.java))
        }

    }

}