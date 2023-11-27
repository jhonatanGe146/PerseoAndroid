package com.cide.primeraapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText

class select_user : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_user)
        var FDoc:EditText?=null
        var btn_volver = findViewById<Button>(R.id.btn_volver)


        btn_volver.setOnClickListener {
            startActivity(Intent(applicationContext, modUser::class.java))
        }

    }

    fun select_user (view: View){
        var FDoc = findViewById<EditText>(R.id.FDoc)
        var intent = Intent(this , mostrar_user::class.java)
        intent .putExtra("documento", FDoc.text.toString())
        startActivity(intent)

    }
}