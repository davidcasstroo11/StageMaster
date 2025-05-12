package com.example.stagemaster.vistas

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.stagemaster.R
import com.example.stagemaster.controlador.UsuarioController

class Registro: AppCompatActivity() {
    private lateinit var inputNombre: EditText
    private lateinit var inputApellidos: EditText
    private lateinit var inputNombreUsuario: EditText
    private lateinit var inputEmail: EditText
    private lateinit var inputClave: EditText
    private lateinit var btnRegistro: Button

    private var controladorUsuario: UsuarioController? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ventana_registro)

        controladorUsuario = UsuarioController(this)

        inputNombre = findViewById(R.id.inputNombre)
        inputApellidos = findViewById(R.id.inputApellidos)
        inputNombreUsuario = findViewById(R.id.inputNombreUsuario)
        inputEmail = findViewById(R.id.inputEmail)
        inputClave = findViewById(R.id.inputClaveRegistro)
        btnRegistro = findViewById(R.id.btnRegistrar)

        btnRegistro.setOnClickListener {
            if (inputNombre.text == null || inputApellidos.text == null || inputNombreUsuario.text == null || inputNombreUsuario.text == null
                || inputEmail.text == null || inputClave.text == null) {
                Toast.makeText(this, "Verifica si todos los campos no se encuentran vacíos.", Toast.LENGTH_SHORT).show()
            } else {
                if (!inputEmail.text.contains("@gmail.com") || !inputEmail.text.contains("hotmail.com")){
                    Toast.makeText(this, "Verifique que el email sea el correcto", Toast.LENGTH_SHORT).show()
                } else {
                    val intent = Intent(this@Registro, MainActivity::class.java)
                    val resultado = controladorUsuario!!.insertarUsuario(inputNombre.text.toString(), inputApellidos.text.toString(), inputNombreUsuario.text.toString(),
                        inputEmail.text.toString(), inputClave.text.toString())
                    if (resultado > 0) {
                        Toast.makeText(this, "Bienvenido a StageMaster", Toast.LENGTH_SHORT).show()
                        startActivity(intent)
                    }
                }
            }
        }

    }
}