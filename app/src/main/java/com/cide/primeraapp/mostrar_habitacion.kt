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

class mostrar_habitacion : AppCompatActivity() {
    var listaHabitaciones: TableLayout? = null
    var num_hab: EditText?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_habitacion)
        listaHabitaciones = findViewById(R.id.listahabitaciones)
        val btn_volver = findViewById<Button>(R.id.btn_volver)
        val btn_eliminar = findViewById<Button>(R.id.btn_eliminar)
        val btn_crear = findViewById<Button>(R.id.btn_crear)
        num_hab = findViewById<EditText>(R.id.num_hab)

        btn_crear.setOnClickListener {
            startActivity(Intent(applicationContext, crear_habitacion::class.java))
        }

        btn_volver.setOnClickListener {
            startActivity(Intent(applicationContext, Menu_Principal::class.java))
        }

        btn_eliminar.setOnClickListener {
            if (!validarCamposVacios()) {
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
                startActivity(Intent(applicationContext, mostrar_servicios::class.java))}
            else{
                Toast.makeText(this, "Debes Poner el Id del servicio", Toast.LENGTH_SHORT).show()

            }
        }


        var queue = Volley.newRequestQueue(this)
        var url = "https://phytotoxic-sponsor.000webhostapp.com/androidproject/select_habitaciones.php"
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                try {
                    val jsonArray: JSONArray = response.getJSONArray("data")

                    // Crear la fila de encabezados
                    val encabezados = LayoutInflater.from(this).inflate(R.layout.tabla_habitacion, null, false)
                    val idServicioHeader = encabezados.findViewById<View>(R.id.num_hab) as TextView
                    val descripcionServicioHeader = encabezados.findViewById<View>(R.id.id_tipoHab) as TextView
                    val precioServicioHeader = encabezados.findViewById<View>(R.id.id_EstadoHab) as TextView
                    idServicioHeader.text = "Numero Habitación"
                    descripcionServicioHeader.text = "Tipo"
                    precioServicioHeader.text = "Estado"
                    listaHabitaciones?.addView(encabezados)

                    // Agregar filas de datos
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val filaDatos = LayoutInflater.from(this).inflate(R.layout.tabla_habitacion, null, false)
                        val num_hab = filaDatos.findViewById<View>(R.id.num_hab) as TextView
                        val id_tipohab = filaDatos.findViewById<View>(R.id.id_tipoHab) as TextView
                        val id_estadohab = filaDatos.findViewById<View>(R.id.id_EstadoHab) as TextView

                        num_hab.text = jsonObject.getString("num_habitacion")
                        id_tipohab.text = jsonObject.getString("tipo_habitacion")
                        id_estadohab.text = jsonObject.getString("tipo_estado")

                        listaHabitaciones?.addView(filaDatos)
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
    fun select_servicio (view: View){
        val num_hab = findViewById<EditText>(R.id.id_serv)
        var intent = Intent(this , update_servicio::class.java)
        intent .putExtra("num_hab", num_hab.text.toString())
        startActivity(intent)

    }

    private fun validarCamposVacios(): Boolean {
        val id = num_hab?.text.toString()

        return id.isEmpty()
    }

}
