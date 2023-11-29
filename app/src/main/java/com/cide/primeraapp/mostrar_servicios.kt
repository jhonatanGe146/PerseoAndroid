package com.cide.primeraapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TableLayout
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class mostrar_servicios : AppCompatActivity() {
    var listaServicio: TableLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_servicios)
        listaServicio = findViewById(R.id.listaServicio)
        val btn_volver = findViewById<Button>(R.id.btn_volver)
        val btn_eliminar = findViewById<Button>(R.id.btn_eliminar)
        val btn_crear = findViewById<Button>(R.id.btn_crear)

        btn_crear.setOnClickListener {
            startActivity(Intent(applicationContext, registrar_servicio::class.java))
        }

        btn_volver.setOnClickListener {
            startActivity(Intent(applicationContext, modServicio::class.java))
        }

        btn_eliminar.setOnClickListener {
            val id_serv = findViewById<EditText>(R.id.id_serv)
            val queue = Volley.newRequestQueue(this)
            val url = "https://phytotoxic-sponsor.000webhostapp.com/androidproject/delete_servicio.php"
            val resultadoPost = object : StringRequest(Request.Method.POST, url,
                Response.Listener<String> { response ->
                    Toast.makeText(this, "Eliminacion Completa ", Toast.LENGTH_LONG).show()
                }, Response.ErrorListener { error ->
                    Toast.makeText(this, "Error Usuario Inexistente", Toast.LENGTH_LONG).show()
                }) {
                override fun getParams(): MutableMap<String, String>? {
                    val parametros = HashMap<String, String>()
                    parametros.put("id", id_serv?.text.toString())

                    return parametros
                }
            }
            queue.add(resultadoPost)
            startActivity(Intent(applicationContext, modServicio::class.java))
        }

        var queue = Volley.newRequestQueue(this)
        var url = "https://phytotoxic-sponsor.000webhostapp.com/androidproject/select_servicios.php"
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                try {
                    val jsonArray: JSONArray = response.getJSONArray("data")

                    // Crear la fila de encabezados
                    val encabezados = LayoutInflater.from(this).inflate(R.layout.tabla_servicio, null, false)
                    val idServicioHeader = encabezados.findViewById<View>(R.id.id_servicio) as TextView
                    val descripcionServicioHeader = encabezados.findViewById<View>(R.id.descripcion_servicio) as TextView
                    val precioServicioHeader = encabezados.findViewById<View>(R.id.precio_servicio) as TextView
                    idServicioHeader.text = "ID Servicio"
                    descripcionServicioHeader.text = "Descripción"
                    precioServicioHeader.text = "Precio"
                    listaServicio?.addView(encabezados)

                    // Agregar filas de datos
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val filaDatos = LayoutInflater.from(this).inflate(R.layout.tabla_servicio, null, false)
                        val idServicio = filaDatos.findViewById<View>(R.id.id_servicio) as TextView
                        val descripcionServicio = filaDatos.findViewById<View>(R.id.descripcion_servicio) as TextView
                        val precioServicio = filaDatos.findViewById<View>(R.id.precio_servicio) as TextView

                        idServicio.text = jsonObject.getString("id_servicio")
                        descripcionServicio.text = jsonObject.getString("descripcion_servicio")
                        precioServicio.text = jsonObject.getString("precio_servicio")

                        listaServicio?.addView(filaDatos)
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Toast.makeText(this, "ErrorCatch: $e", Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(jsonObjectRequest)
    }
}
