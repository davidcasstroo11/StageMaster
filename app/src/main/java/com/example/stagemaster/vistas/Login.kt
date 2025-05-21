package com.example.stagemaster.vistas

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.stagemaster.R
import com.example.stagemaster.controlador.UsuarioController

class Login: AppCompatActivity() {
    private lateinit var btnAcceder: Button
    private lateinit var btnCerrarSesion: Button
    private lateinit var inputEmail: EditText
    private lateinit var inputClave: EditText
    private lateinit var textRegistro: TextView
    private lateinit var textRestablecerContra: TextView
    private lateinit var vistaContenidoLogin: View

    private var controladorUsuario: UsuarioController? = null
    private var entidadesVentanaEmergentes: EntidadesVentanaEmergentes? = null

    @RequiresApi(Build.VERSION_CODES.S)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ventana_login)

        controladorUsuario = UsuarioController(this)
        entidadesVentanaEmergentes = EntidadesVentanaEmergentes()

        btnAcceder = findViewById(R.id.btnAcceder)
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion)
        inputEmail = findViewById(R.id.inputEmailLogin)
        inputClave = findViewById(R.id.inputClaveLogin)
        textRegistro = findViewById(R.id.textRegistro)
        textRestablecerContra = findViewById(R.id.textRestablecerContraseñaLog)
        vistaContenidoLogin = findViewById(R.id.contenidoLogin)

        btnAcceder.setOnClickListener {
            val usuarioExtraido = controladorUsuario!!.selectUsuarios(inputEmail.text.toString())

            if (inputEmail.text.isEmpty() || inputClave.text.isEmpty()) {
                entidadesVentanaEmergentes!!.ventanaEmergenteError(this, vistaContenidoLogin,"Verifique que los campos no se encuentren vacíos.")
                return@setOnClickListener
            } else if (usuarioExtraido == null) {
                entidadesVentanaEmergentes!!.ventanaEmergenteError(this, vistaContenidoLogin,"No se ha podido encontrar el email proporcionado. Registre un nuevo usuario.")
                return@setOnClickListener
            } else if (!usuarioExtraido.clave.equals(Hash.md5(inputClave.text.toString()))) {
                entidadesVentanaEmergentes!!.ventanaEmergenteError(this, vistaContenidoLogin,"Contraseña incorrecta, vuelva a intentarlo.")
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
        btnCerrarSesion.setOnClickListener {
            entidadesVentanaEmergentes!!.ventanaEmergenteInfo(this, vistaContenidoLogin,"No es posible cerrar sesión en este momento.")
        }
    }
}