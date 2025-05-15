package com.example.stagemaster.vistas

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.stagemaster.R
import com.example.stagemaster.controlador.UsuarioController

class ModificarUsuario: AppCompatActivity() {
    private lateinit var inputUsuario: EditText
    private lateinit var inputNuevoUsuario: EditText
    private lateinit var inputNuevoUsuarioRepeat: EditText
    private lateinit var btnModificar: Button
    private lateinit var btnVolver: Button

    private lateinit var usuarioLogueado: String
    private var controladorUsuarios: UsuarioController? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.conf_modificar_usuario)

        controladorUsuarios = UsuarioController(this)

        inputUsuario = findViewById(R.id.inputUsuario)
        inputNuevoUsuario = findViewById(R.id.inputNuevoUsuario)
        inputNuevoUsuarioRepeat = findViewById(R.id.inputNuevoUsuarioRepeat)
        btnModificar = findViewById(R.id.btnModificar)
        btnVolver = findViewById(R.id.btnVolver)

        usuarioLogueado = intent.getStringExtra("usuarioLogueado").toString()
        inputUsuario.setText(usuarioLogueado)

        btnModificar.setOnClickListener {
            if (inputNuevoUsuario.text.isEmpty() || inputNuevoUsuarioRepeat.text.isEmpty()) {
                Toast.makeText(this, "Verifica que todos los campos no se encuentran vacíos.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (!inputNuevoUsuario.text.toString().equals(inputNuevoUsuarioRepeat.text.toString())) {
                Toast.makeText(this, "Verifica que ambas claves sean iguales.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (inputNuevoUsuario.text.toString().equals(inputUsuario.text.toString())) {
                Toast.makeText(this, "Debes introducir un nombre diferente al anterior.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                val intent = Intent(this@ModificarUsuario, ConfiguracionFragment::class.java)
                val resultado = controladorUsuarios!!.actualizarNombreUsuario(inputUsuario.text.toString(), inputNuevoUsuario.text.toString())
                if (resultado > 0) Toast.makeText(this, "Se ha modificado correctamente el nombre del usuario.", Toast.LENGTH_SHORT).show()
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }

        btnVolver.setOnClickListener {
            val intent = Intent(this@ModificarUsuario, ConfiguracionFragment::class.java)
            setResult(Activity.RESULT_CANCELED, intent)
            finish()
        }
    }
}