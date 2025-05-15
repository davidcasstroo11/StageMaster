package com.example.stagemaster.vistas

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.stagemaster.R
import com.example.stagemaster.controlador.UsuarioController

class RestablecerContraUsuario: AppCompatActivity() {
    private lateinit var inputClave: EditText
    private lateinit var inputClaveRepeat: EditText
    private lateinit var btnRestablecerContra: Button
    private lateinit var btnVolver: Button

    private var controladorUsuario: UsuarioController? = null
    private lateinit var emailLogin: String

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.conf_rest_contra)

        controladorUsuario = UsuarioController(this)

        inputClave = findViewById(R.id.inputClave)
        inputClaveRepeat = findViewById(R.id.inputClaveRepeat)
        btnRestablecerContra = findViewById(R.id.btnRestablecer)
        btnVolver = findViewById(R.id.btnVolver)
        emailLogin = intent.getStringExtra("email").toString()

        btnRestablecerContra.setOnClickListener {
            if (inputClave.text.isEmpty() || inputClaveRepeat.text.isEmpty()) {
                Toast.makeText(this, "Verifica que todos los campos no se encuentran vacíos.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (!inputClave.text.toString().equals(inputClaveRepeat.text.toString())) {
                Toast.makeText(this, "Verifica que ambas claves sean iguales.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                val intent = Intent(this@RestablecerContraUsuario, ConfiguracionFragment::class.java)
                val resultado = controladorUsuario!!.actualizarUsuarioClave(emailLogin, inputClave.text.toString())
                if (resultado > 0) Toast.makeText(this, "Se ha modificado correctamente la contraseña.", Toast.LENGTH_SHORT).show()
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
        btnVolver.setOnClickListener {
            val intent = Intent(this@RestablecerContraUsuario, ConfiguracionFragment::class.java)
            setResult(Activity.RESULT_CANCELED, intent)
            finish()
        }
    }
}