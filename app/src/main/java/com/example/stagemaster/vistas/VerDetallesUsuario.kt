package com.example.stagemaster.vistas

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.stagemaster.R

class VerDetallesUsuario: AppCompatActivity() {
    private lateinit var inputNombreCompleto: EditText
    private lateinit var inputUsuario: EditText
    private lateinit var inputEmail: EditText
    private lateinit var btnVolver: Button

    private lateinit var nombreLogin: String
    private lateinit var apellidosLogin: String
    private lateinit var usuarioLogin: String
    private lateinit var emailLogin: String

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.conf_ver_detalles)

        inputNombreCompleto = findViewById(R.id.inputNombreCompleto)
        inputUsuario = findViewById(R.id.inputUsuario)
        inputEmail = findViewById(R.id.inputEmail)
        btnVolver = findViewById(R.id.btnVolver)

        nombreLogin = intent.getStringExtra("nombre").toString()
        apellidosLogin = intent.getStringExtra("apellidos").toString()
        usuarioLogin = intent.getStringExtra("usuarioLogueado").toString()
        emailLogin = intent.getStringExtra("email").toString()

        // Asignación de datos a los campos a mostrar en la ventana
        val nombreCompleto = nombreLogin + " " + apellidosLogin
        inputNombreCompleto.setText(nombreCompleto)
        inputUsuario.setText(usuarioLogin)
        inputEmail.setText(emailLogin)

        btnVolver.setOnClickListener {
            val intent = Intent(this@VerDetallesUsuario, ConfiguracionFragment::class.java)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}