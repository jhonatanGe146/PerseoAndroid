package com.cide.primeraapp


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley


class registro : AppCompatActivity() {
    var Fnombre:EditText?=null
    var Fapellido: EditText?=null
    var FTipoDoc: EditText?=null
    var Fdocumento:EditText?=null
    var Fcorreo:EditText?=null
    var Ftelefono:EditText?=null
    var Fpassword:EditText?=null
    var Fpassword2:EditText?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        Fnombre = findViewById(R.id.Fnombre)
        Fapellido = findViewById(R.id.Fapellido)
        FTipoDoc = findViewById(R.id.FTipoDoc)
        Fdocumento = findViewById(R.id.Fdocumento)
        Fcorreo = findViewById(R.id.Fcorreo)
        Ftelefono = findViewById(R.id.Ftelefono)
        Fpassword = findViewById(R.id.Fpassword)
        Fpassword2 = findViewById(R.id.Fpassword2)
        val btn_registrar=findViewById<Button>(R.id.btn_registrar)
        val volver = findViewById<TextView>(R.id.volver)
        volver.setOnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }
        btn_registrar.setOnClickListener {
            if (!validarCamposVacios()) {
                Toast.makeText(this, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show()
            } else if (!validarPasswordsCoinciden()) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            } else {
                insert_usuario()
                startActivity(Intent(applicationContext, MainActivity::class.java))

            }
        }

    }
    private  fun insert_usuario ()
    {
        val url = "https://phytotoxic-sponsor.000webhostapp.com/androidproject/insert_usuario.php"
        val queue=Volley.newRequestQueue(this)
        val resultadoPost=object : StringRequest(Request.Method.POST,url,
            Response.Listener<String> { response ->
                Toast.makeText(this, "Registro Completo ", Toast.LENGTH_LONG).show()
            },Response.ErrorListener { error ->
                Toast.makeText(this,"Error $error",Toast.LENGTH_LONG).show()
            }){
            override fun getParams(): MutableMap<String, String>? {
                val parametros=HashMap<String, String>()
                parametros.put("FtipoDoc", FTipoDoc?.text.toString())
                parametros.put("Fdocumento", Fdocumento?.text.toString())
                parametros.put("Fnombre", Fnombre?.text.toString())
                parametros.put("Fapellido", Fapellido?.text.toString())
                parametros.put("Femail", Fcorreo?.text.toString())
                parametros.put("Ftelefono", Ftelefono?.text.toString())
                parametros.put("Fusuario", "3")
                parametros.put("Pcontrasena", Fpassword?.text.toString())
                return parametros
            }
        }
        queue.add(resultadoPost)
    }

    private fun validarCamposVacios(): Boolean {
        val nombre = Fnombre?.text.toString()
        val apellido = Fapellido?.text.toString()
        val tipoDoc = FTipoDoc?.text.toString()
        val documento = Fdocumento?.text.toString()
        val correo = Fcorreo?.text.toString()
        val telefono = Ftelefono?.text.toString()
        val password = Fpassword?.text.toString()
        val password2 = Fpassword2?.text.toString()

        return nombre.isNotEmpty() &&
                apellido.isNotEmpty() &&
                tipoDoc.isNotEmpty() &&
                documento.isNotEmpty() &&
                correo.isNotEmpty() &&
                telefono.isNotEmpty() &&
                password.isNotEmpty() &&
                password2.isNotEmpty()
    }

    fun validarPasswordsCoinciden(): Boolean {
        val password = Fpassword?.text.toString()
        val password2 = Fpassword2?.text.toString()

        return password == password2
    }


}