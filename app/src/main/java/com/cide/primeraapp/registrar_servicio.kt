package com.cide.primeraapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class registrar_servicio : AppCompatActivity() {
    var descripcion:EditText?=null
    var precio:EditText?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_servicio)
        descripcion = findViewById(R.id.f_descripcion)
        precio = findViewById(R.id.f_precio)


        val btn_volver = findViewById<Button>(R.id.btn_volver)
        val btn_registrar = findViewById<Button>(R.id.btn_registrar_servicio)

        btn_volver.setOnClickListener {
            startActivity(Intent(applicationContext, modServicio::class.java))
        }
        btn_registrar.setOnClickListener {
            if (!validarCamposVacios()) {
               // Toast.makeText(this, "bien", Toast.LENGTH_SHORT).show()
                insert_servicio()
                startActivity(Intent(applicationContext, mostrar_servicios::class.java))
            } else {
                Toast.makeText(this, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show()

            }
        }


    }

private  fun insert_servicio ()
{
    val url = "https://phytotoxic-sponsor.000webhostapp.com/androidproject/insert_servicio.php"
    val queue=Volley.newRequestQueue(this)
    val resultadoPost=object : StringRequest(Request.Method.POST,url,
        Response.Listener<String> { response ->
            Toast.makeText(this, "Registro Completo ", Toast.LENGTH_LONG).show()
        },Response.ErrorListener { error ->
            Toast.makeText(this,"Error $error",Toast.LENGTH_LONG).show()
        }){
        override fun getParams(): MutableMap<String, String>? {
            val parametros=HashMap<String, String>()
            parametros.put("descripcion", descripcion?.text.toString())
            parametros.put("precio", precio?.text.toString())
            return parametros
        }
    }
    queue.add(resultadoPost)
}

    private fun validarCamposVacios(): Boolean {
        val desc = descripcion?.text.toString()
        val prec = precio?.text.toString()

        return desc.isEmpty() || prec.isEmpty()
    }



}