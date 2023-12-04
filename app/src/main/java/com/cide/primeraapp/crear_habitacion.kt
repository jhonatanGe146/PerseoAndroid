package com.cide.primeraapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray

class crear_habitacion : AppCompatActivity() {
    var num_hab:EditText?=null
    private var spn_tipoHab: Spinner? = null
    private var spn_EstadoHab: Spinner? = null
    private var nvoval_estadohab: Int = 0
    private var nvoval_tipohab: Int = 0
    private var id_tipohab: Int = 0
    private var id_estadohab: Int = 0
    private var miroi: Int = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_habitacion)
        num_hab = findViewById(R.id.Fnum_hab)
        spn_tipoHab = findViewById(R.id.FTipoHab)
        spn_EstadoHab = findViewById(R.id.FEstadohab)


        val btn_volver = findViewById<Button>(R.id.btn_volver)
        val btn_crear = findViewById<Button>(R.id.btn_crear)

        btn_volver.setOnClickListener {
            startActivity(Intent(applicationContext, mostrar_habitacion::class.java))
        }
        btn_crear.setOnClickListener {
            if (!validarCamposVacios()) {
                // Toast.makeText(this, "bien", Toast.LENGTH_SHORT).show()
                insert_habitacion()
                startActivity(Intent(applicationContext, mostrar_habitacion::class.java))
            } else {
                Toast.makeText(this, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show()

            }
        }
        //Spinner Tipo Habitacion
        val queue = Volley.newRequestQueue(this)
        val url = "https://phytotoxic-sponsor.000webhostapp.com/androidproject/select_tipoHab.php"
        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->
                val jsonArray = JSONArray(response)
                val miro = mutableListOf<String>()
                val miroid = mutableListOf<String>()
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    miro.add(jsonObject.getString("tipo_habitacion"))
                    miroid.add(jsonObject.getString("id_tipoHabitacion"))

                }

                val adaptador =
                    ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, miro)
                spn_tipoHab?.adapter = adaptador

                spn_tipoHab?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        nvoval_tipohab = position + 1
                        //tv_Resultado.text = nvoval.toString()
                        Toast.makeText(
                            applicationContext,
                            "Posicion Tomada " + miroid[position],
                            Toast.LENGTH_LONG
                        ).show()

                        this@crear_habitacion.id_tipohab =  miroid[position].toInt()
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        // Implementación opcional
                    }
                }
            },
            Response.ErrorListener { error ->
                // Manejo de errores
            }
        )
        queue.add(stringRequest)
        // -----------------------------------------------------------------------------------------

        //Spinner Tipo Estado
        val queue2 = Volley.newRequestQueue(this)
        val url2 = "https://phytotoxic-sponsor.000webhostapp.com/androidproject/select_estadoHab.php"
        val stringRequest2 = StringRequest(Request.Method.GET, url2,
            { response ->
                val jsonArray = JSONArray(response)
                val miro = mutableListOf<String>()
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    miro.add(jsonObject.getString("tipo_estado"))
                }

                val adaptador =
                    ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, miro)
                spn_EstadoHab?.adapter = adaptador

                spn_EstadoHab?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        nvoval_estadohab = position + 1
                        //tv_Resultado.text = nvoval.toString()
                        Toast.makeText(
                            applicationContext,
                            "Posicion Tomada " + miro[position],
                            Toast.LENGTH_LONG
                        ).show()

                        this@crear_habitacion.id_estadohab = nvoval_estadohab
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        // Implementación opcional
                    }
                }
            },
            Response.ErrorListener { error ->
                // Manejo de errores
            }
        )
        queue2.add(stringRequest2)





    }

    private  fun insert_habitacion ()
    {
        val url = "https://phytotoxic-sponsor.000webhostapp.com/androidproject/insert_habitacion.php"
        val queue=Volley.newRequestQueue(this)
        val resultadoPost=object : StringRequest(Request.Method.POST,url,
            Response.Listener<String> { response ->
                Toast.makeText(this, "Registro Completo ", Toast.LENGTH_LONG).show()
            },Response.ErrorListener { error ->
                Toast.makeText(this,"Error $error",Toast.LENGTH_LONG).show()
            }){
            override fun getParams(): MutableMap<String, String>? {
                val parametros=HashMap<String, String>()
                parametros.put("num", num_hab?.text.toString())
                parametros.put("id_tipo", id_tipohab.toString())
                parametros.put("id_estado", id_estadohab.toString())
                return parametros
            }
        }
        queue.add(resultadoPost)
    }

    private fun validarCamposVacios(): Boolean {
        val num_hab = num_hab?.text.toString()


        return num_hab.isEmpty()
    }



}