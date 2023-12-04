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

class mostrar_user : AppCompatActivity() {
    var Fnombre: EditText?=null
    var Fapellido: EditText?=null
    var FTipoDoc: EditText?=null
    var Fdocumento: EditText?=null
    var Fcorreo: EditText?=null
    var Ftelefono: EditText?=null
    var Fpassword: EditText?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_user)

        Fnombre = findViewById(R.id.Fnombre)
        Fapellido = findViewById(R.id.Fapellido)
        FTipoDoc = findViewById(R.id.FTipoDoc)
        Fdocumento = findViewById(R.id.Fdocumento)
        Fcorreo = findViewById(R.id.Fcorreo)
        Ftelefono = findViewById(R.id.Ftelefono)
        // Fpassword = findViewById(R.id.Fpassword)
        val btn_volver = findViewById<Button>(R.id.btn_volver)
        val btn_eliminar = findViewById<Button>(R.id.btn_eliminar)
        val id = intent.getStringExtra("documento").toString()
        val btn_actualizar = findViewById<Button>(R.id.btn_actualizar)

        btn_volver.setOnClickListener {
            startActivity(Intent(applicationContext, Menu_Principal::class.java))
        }



        btn_eliminar.setOnClickListener {
            val queue = Volley.newRequestQueue(this)
            val url = "https://phytotoxic-sponsor.000webhostapp.com/androidproject/delete_user.php"
            val resultadoPost = object : StringRequest(Request.Method.POST, url,
                Response.Listener<String> { response ->
                    Toast.makeText(this, "Eliminacion Completa ", Toast.LENGTH_LONG).show()
                }, Response.ErrorListener { error ->
                    Toast.makeText(this, "Error Usuario Inexistente", Toast.LENGTH_LONG).show()
                }) {
                override fun getParams(): MutableMap<String, String>? {
                    val parametros = HashMap<String, String>()
                    parametros["documento"] = id

                    return parametros
                }
            }
            queue.add(resultadoPost)
            startActivity(Intent(applicationContext, Menu_Principal::class.java))
        }

        btn_actualizar.setOnClickListener {
            val queue = Volley.newRequestQueue(this)
            val url = "https://phytotoxic-sponsor.000webhostapp.com/androidproject/update_user.php"
            val resultadoPost = object : StringRequest(Request.Method.POST, url,
                Response.Listener<String> { response ->
                    Toast.makeText(this, "Actualizacón Completa ", Toast.LENGTH_LONG).show()
                }, Response.ErrorListener { error ->
                    Toast.makeText(this, "Error Usuario Inexistente", Toast.LENGTH_LONG).show()
                }) {
                override fun getParams(): MutableMap<String, String>? {
                    val parametros = HashMap<String, String>()
                    parametros.put("FtipoDoc", FTipoDoc?.text.toString())
                    parametros.put("Fnombre", Fnombre?.text.toString())
                    parametros.put("Fapellido", Fapellido?.text.toString())
                    parametros.put("Femail", Fcorreo?.text.toString())
                    parametros.put("Ftelefono", Ftelefono?.text.toString())
                    parametros.put("Fnro", id)
                    return parametros
                }
            }
            queue.add(resultadoPost)
            startActivity(Intent(applicationContext, Menu_Principal::class.java))
        }



        val queue = Volley.newRequestQueue(this)
        val getUrl = "https://phytotoxic-sponsor.000webhostapp.com/androidproject/select_user.php?documento=$id"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, getUrl, null,
            { response ->
                try {
                    Fnombre?.setText(response.getString("nombre"))
                    Fapellido?.setText(response.getString("apellido"))
                    FTipoDoc?.setText(response.getString("id_tipoDocumento"))
                    Fdocumento?.setText(response.getString("numero_documento"))
                    Fcorreo?.setText(response.getString("email"))
                    Ftelefono?.setText(response.getString("telefono"))
                    Fpassword?.setText(response.getString("contrasena"))
                } catch (e: Exception) {
                    Toast.makeText(this, "Error al procesar la respuesta", Toast.LENGTH_LONG).show()
                }
            },
            { error: VolleyError ->
                Toast.makeText(this, "Error Usuario Inexistente", Toast.LENGTH_LONG).show()
                startActivity(Intent(applicationContext, Menu_Principal::class.java))
            }
        )
        queue.add(jsonObjectRequest)
    }
}