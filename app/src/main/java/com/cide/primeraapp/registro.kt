package com.cide.primeraapp
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.cide.primeraapp.R
import org.json.JSONArray

class registro : AppCompatActivity() {
    private var Fnombre: EditText? = null
    private var Fapellido: EditText? = null
    private var Fdocumento: EditText? = null
    private var Fcorreo: EditText? = null
    private var Ftelefono: EditText? = null
    private var Fpassword: EditText? = null
    private var Fpassword2: EditText? = null
    private var spn_datos: Spinner? = null
    private var id: Int = 0
    private var nvoval: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        spn_datos = findViewById(R.id.FTipoDoc)

        Fnombre = findViewById(R.id.Fnombre)
        Fapellido = findViewById(R.id.Fapellido)
        Fdocumento = findViewById(R.id.Fdocumento)
        Fcorreo = findViewById(R.id.Fcorreo)
        Ftelefono = findViewById(R.id.Ftelefono)
        Fpassword = findViewById(R.id.Fpassword)
        Fpassword2 = findViewById(R.id.Fpassword2)

        //val tv_Resultado = findViewById<TextView>(R.id.tv_Resultado)
        val btn_registrar = findViewById<Button>(R.id.btn_registrar)

        btn_registrar.setOnClickListener {
            if (!validarCamposVacios()) {
                Toast.makeText(this, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show()
            } else if (!validarPasswordsCoinciden()) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            } else {
                val url = "https://phytotoxic-sponsor.000webhostapp.com/androidproject/insert_usuario.php"
                val queue = Volley.newRequestQueue(this)
                val resultadoPost = object : StringRequest(
                    Request.Method.POST, url,
                    Response.Listener<String> { response ->
                        Toast.makeText(this, "Registro Completo ", Toast.LENGTH_LONG).show()
                    },
                    Response.ErrorListener { error ->
                        Toast.makeText(this, "Error $error", Toast.LENGTH_LONG).show()
                    }
                ) {
                    override fun getParams(): MutableMap<String, String>? {
                        val parametros = HashMap<String, String>()
                        parametros["FtipoDoc"] = id.toString()
                        parametros["Fdocumento"] = Fdocumento?.text.toString()
                        parametros["Fnombre"] = Fnombre?.text.toString()
                        parametros["Fapellido"] = Fapellido?.text.toString()
                        parametros["Femail"] = Fcorreo?.text.toString()
                        parametros["Ftelefono"] = Ftelefono?.text.toString()
                        parametros["Fusuario"] = "3"
                        parametros["Pcontrasena"] = Fpassword?.text.toString()
                        return parametros
                    }
                }
                queue.add(resultadoPost)
                startActivity(Intent(applicationContext, MainActivity::class.java))
            }
        }

        val queue = Volley.newRequestQueue(this)
        val url = "https://phytotoxic-sponsor.000webhostapp.com/androidproject/select_documento.php"
        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->
                val jsonArray = JSONArray(response)
                val miro = mutableListOf<String>()
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    miro.add(jsonObject.getString("tipo_documento"))
                }

                val adaptador =
                    ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, miro)
                spn_datos?.adapter = adaptador

                spn_datos?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        nvoval = position + 1
                        //tv_Resultado.text = nvoval.toString()
                        Toast.makeText(
                            applicationContext,
                            "Posicion Tomada " + miro[position],
                            Toast.LENGTH_LONG
                        ).show()

                        this@registro.id = nvoval
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
    }

    private fun validarCamposVacios(): Boolean {
        val nombre = Fnombre?.text.toString()
        val apellido = Fapellido?.text.toString()
        val documento = Fdocumento?.text.toString()
        val correo = Fcorreo?.text.toString()
        val telefono = Ftelefono?.text.toString()
        val password = Fpassword?.text.toString()
        val password2 = Fpassword2?.text.toString()

        return nombre.isNotEmpty() &&
                apellido.isNotEmpty() &&
                documento.isNotEmpty() &&
                correo.isNotEmpty() &&
                telefono.isNotEmpty() &&
                password.isNotEmpty() &&
                password2.isNotEmpty()
    }

    private fun validarPasswordsCoinciden(): Boolean {
        val password = Fpassword?.text.toString()
        val password2 = Fpassword2?.text.toString()

        return password == password2
    }
}
