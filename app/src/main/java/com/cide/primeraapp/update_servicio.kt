package com.cide.primeraapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class update_servicio : AppCompatActivity() {
    var f_descripcion: EditText?=null
    var f_precio: EditText?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_servicio)

        val btn_volver = findViewById<Button>(R.id.btn_volver)
        val btn_actualizar = findViewById<Button>(R.id.btn_actualizar)
        val id_servicio = intent.getStringExtra("id_serv").toString()
        val f_descripcion = findViewById<EditText>(R.id.f_descripcion)
        val f_precio = findViewById<EditText>(R.id.f_precio)

        btn_volver.setOnClickListener {
            startActivity(Intent(applicationContext, mostrar_servicios::class.java))
        }

        btn_actualizar.setOnClickListener {
            val queue = Volley.newRequestQueue(this)
            val url = "https://phytotoxic-sponsor.000webhostapp.com/androidproject/update_servicio.php"
            val resultadoPost = object : StringRequest(Request.Method.POST, url,
                Response.Listener<String> { response ->
                    Toast.makeText(this, "Actualizacón Completa ", Toast.LENGTH_LONG).show()
                }, Response.ErrorListener { error ->
                    Toast.makeText(this, "Error Usuario Inexistente", Toast.LENGTH_LONG).show()
                }) {
                override fun getParams(): MutableMap<String, String>? {
                    val parametros = HashMap<String, String>()
                    parametros.put("Fid",id_servicio)
                    parametros.put("Fdescripcion", f_descripcion?.text.toString())
                    parametros.put("Fprecio", f_precio?.text.toString())
                    return parametros
                }
            }
            queue.add(resultadoPost)
            startActivity(Intent(applicationContext, mostrar_servicios::class.java))
        }


        val queue = Volley.newRequestQueue(this)
        val getUrl = "https://phytotoxic-sponsor.000webhostapp.com/androidproject/selectservicio.php?id_servicio=$id_servicio"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, getUrl, null,
            { response ->
                try {
                    f_descripcion?.setText(response.getString("descripcion_servicio"))
                    f_precio?.setText(response.getString("precio_servicio"))
                } catch (e: Exception) {
                    Toast.makeText(this, "Error al procesar la respuesta", Toast.LENGTH_LONG).show()
                }
            },
            { error: VolleyError ->
                Toast.makeText(this, "Error Servicio No Existe", Toast.LENGTH_LONG).show()
                startActivity(Intent(applicationContext, mostrar_servicios::class.java))
            }
        )
        queue.add(jsonObjectRequest)




    }
}

