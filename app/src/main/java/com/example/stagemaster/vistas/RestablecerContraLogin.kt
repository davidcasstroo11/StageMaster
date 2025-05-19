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

class RestablecerContraLogin: AppCompatActivity() {
    private lateinit var inputEmail: EditText
    private lateinit var inputClave: EditText
    private lateinit var inputClaveRepeat: EditText
    private lateinit var btnRestablecerContra: Button
    private lateinit var btnVolver: Button

    private var controladorUsuario: UsuarioController? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ventana_rest_contra)

        controladorUsuario = UsuarioController(this)

        inputEmail = findViewById(R.id.inputEmailContra)
        inputClave = findViewById(R.id.inputClaveContra)
        inputClaveRepeat = findViewById(R.id.inputClaveContraRepeat)
        btnRestablecerContra = findViewById(R.id.btnRestablecerContra)
        btnVolver = findViewById(R.id.btnVolverContra)

        btnRestablecerContra.setOnClickListener {
            if (inputEmail.text.isEmpty() || inputClave.text.isEmpty() || inputClaveRepeat.text.isEmpty()) {
                Toast.makeText(this, "Verifica si todos los campos no se encuentran vacíos.", Toast.LENGTH_SHORT).show()
            } else {
                val usuarioExtraido = controladorUsuario!!.selectUsuarios(inputEmail.text.toString())
                if (usuarioExtraido == null) {
                    Toast.makeText(this, "No se ha encontrado el email proporcionado.", Toast.LENGTH_SHORT).show()
                } else {
                    if (!inputClave.text.toString().equals(inputClaveRepeat.text.toString())) {
                        Toast.makeText(this, "Debes introducir la contraseña igual en ambos campos.", Toast.LENGTH_SHORT).show()
                    } else {
                        val intent = Intent(this@RestablecerContraLogin, Login::class.java)
                        val resultado = controladorUsuario!!.actualizarUsuarioClave(inputEmail.text.toString(), inputClave.text.toString())
                        if (resultado > 0) {
                            Toast.makeText(this, "Se ha actualizado la contraseña correctamente.", Toast.LENGTH_SHORT).show()
                            setResult(Activity.RESULT_OK, intent)
                            finish()
                        }
                    }
                }
            }
        }

        btnVolver.setOnClickListener {
            val intent = Intent(this@RestablecerContraLogin, Login::class.java)
            setResult(Activity.RESULT_CANCELED, intent)
            finish()
        }
    }
}