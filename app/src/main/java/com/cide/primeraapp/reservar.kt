package com.cide.primeraapp

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class reservar : AppCompatActivity() {

    private val myCalendarEntrada = Calendar.getInstance()
    private val myCalendarSalida = Calendar.getInstance()
    private lateinit var fechaEntradaEditText: EditText
    private lateinit var fechaSalidaEditText: EditText
    var cantidad_a: EditText?=null
    var cantidad_n:EditText?=null
    var documento:EditText?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservar)

        fechaEntradaEditText = findViewById(R.id.fechaEntradaEditText)
        fechaSalidaEditText = findViewById(R.id.fechaSalidaEditText)
        cantidad_a = findViewById(R.id.cant_a)
        cantidad_n= findViewById(R.id.cant_n)
        documento = findViewById(R.id.nro_doc)
        val btn_reservar = findViewById<Button>(R.id.btn_reservar)


        val btn_volver = findViewById<Button>(R.id.btn_volver)

        btn_volver.setOnClickListener {
            startActivity(Intent(applicationContext, modUser::class.java))
        }

        btn_reservar.setOnClickListener {
            insert_reserva()
        }

        // Configura el formato de la fecha de entrada
        val dateEntrada = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            myCalendarEntrada.set(Calendar.YEAR, year)
            myCalendarEntrada.set(Calendar.MONTH, monthOfYear)
            myCalendarEntrada.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateFechaEntrada()
        }

        // Configura el formato de la fecha de salida
        val dateSalida = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            myCalendarSalida.set(Calendar.YEAR, year)
            myCalendarSalida.set(Calendar.MONTH, monthOfYear)
            myCalendarSalida.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateFechaSalida()
        }

        fechaEntradaEditText.setOnClickListener {
            DatePickerDialog(
                this, dateEntrada,
                myCalendarEntrada.get(Calendar.YEAR),
                myCalendarEntrada.get(Calendar.MONTH),
                myCalendarEntrada.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        fechaSalidaEditText.setOnClickListener {
            DatePickerDialog(
                this, dateSalida,
                myCalendarSalida.get(Calendar.YEAR),
                myCalendarSalida.get(Calendar.MONTH),
                myCalendarSalida.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun updateFechaEntrada() {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        fechaEntradaEditText.setText(sdf.format(myCalendarEntrada.time))
    }

    private fun updateFechaSalida() {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        fechaSalidaEditText.setText(sdf.format(myCalendarSalida.time))
    }

    fun volver(view: android.view.View) {
        startActivity(Intent(applicationContext, modUser::class.java))
    }

    private  fun insert_reserva ()
    {
        val url = "https://phytotoxic-sponsor.000webhostapp.com/androidproject/insert_reserva.php"
        val queue= Volley.newRequestQueue(this)
        val resultadoPost=object : StringRequest(Request.Method.POST,url,
            Response.Listener<String> { response ->
                Toast.makeText(this, "Registro Completo ", Toast.LENGTH_LONG).show()
            }, Response.ErrorListener { error ->
                Toast.makeText(this,"Error $error", Toast.LENGTH_LONG).show()
            }){
            override fun getParams(): MutableMap<String, String>? {
                val parametros=HashMap<String, String>()
                parametros.put("fecha_entrada", fechaEntradaEditText?.text.toString())
                parametros.put("fecha_salida", fechaSalidaEditText?.text.toString())
                parametros.put("cantidad_adultos", cantidad_a?.text.toString())
                parametros.put("cantidad_ninos", cantidad_n?.text.toString())
                parametros.put("numero_documento", documento?.text.toString())
            
                return parametros
            }
        }
        queue.add(resultadoPost)
    }
}
