package com.example.stagemaster.vistas

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.stagemaster.R
import com.example.stagemaster.controlador.UsuarioController

class Login: AppCompatActivity() {
    private lateinit var btnAcceder: Button
    private lateinit var inputEmail: EditText
    private lateinit var inputClave: EditText
    private lateinit var textRegistro: TextView
    private lateinit var textRestablecerContra: TextView

    private var controladorUsuario: UsuarioController? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ventana_login)

        controladorUsuario = UsuarioController(this)

        btnAcceder = findViewById(R.id.btnAcceder)
        inputEmail = findViewById(R.id.inputEmailLogin)
        inputClave = findViewById(R.id.inputClaveLogin)
        textRegistro = findViewById(R.id.textRegistro)
        textRestablecerContra = findViewById(R.id.textRestablecerContraseñaLog)

        btnAcceder.setOnClickListener {
            val usuarioExtraido = controladorUsuario!!.selectUsuarios(inputEmail.text.toString())

            if (inputEmail.text.isEmpty() || inputClave.text.isEmpty()) {
                Toast.makeText(this, "Verifique que los campos no se encuentren vacios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (usuarioExtraido == null) {
                Toast.makeText(this, "No se encuentra ese email en el programa", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (!usuarioExtraido.clave.equals(Hash.md5(inputClave.text.toString()))) {
                Toast.makeText(this, "Contraseña incorrecta. Vuelve a intentarlo", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                val intent = Intent(this@Login, MainActivity::class.java)
                intent.putExtra("nombre", usuarioExtraido.nombre)
                intent.putExtra("apellidos", usuarioExtraido.apellidos)
                intent.putExtra("usuarioLogueado", usuarioExtraido.nombreUsuario)
                intent.putExtra("email", usuarioExtraido.email)
                startActivity(intent)
            }
        }
        textRegistro.setOnClickListener {
            val intent = Intent(this@Login, Registro::class.java)
            startActivity(intent)
        }
        textRestablecerContra.setOnClickListener {
            val intent = Intent(this@Login, RestablecerContraLogin::class.java)
            startActivity(intent)
        }
    }
}