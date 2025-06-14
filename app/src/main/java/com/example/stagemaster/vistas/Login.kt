package com.example.stagemaster.vistas

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
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
    private lateinit var viewContenidoLogin: View

    private var controladorUsuario: UsuarioController? = null
    private var entidadesVentanaEmergentes: EntidadVentanasEmergentes? = null

    @RequiresApi(Build.VERSION_CODES.S)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ventana_login)

        controladorUsuario = UsuarioController(this)
        entidadesVentanaEmergentes = EntidadVentanasEmergentes()

        btnAcceder = findViewById(R.id.btnAcceder)
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion)
        inputEmail = findViewById(R.id.inputEmailLogin)
        inputClave = findViewById(R.id.inputClaveLogin)
        textRegistro = findViewById(R.id.textRegistro)
        textRestablecerContra = findViewById(R.id.textRestablecerContraseñaLog)
        viewContenidoLogin = findViewById(R.id.contenidoLogin)

        btnAcceder.setOnClickListener {
            // Verifica si encuentra un usuario con los parametros recibidos en UI
            val usuarioExtraido = controladorUsuario!!.selectUsuarios(inputEmail.text.toString())

            if (inputEmail.text.isEmpty() || inputClave.text.isEmpty()) {
                entidadesVentanaEmergentes!!.ventanaEmergenteError(this, viewContenidoLogin,"Verifica que los campos no se encuentren vacíos.")
                return@setOnClickListener
            } else if (usuarioExtraido == null) {
                entidadesVentanaEmergentes!!.ventanaEmergenteError(this, viewContenidoLogin,"No se ha podido encontrar el email proporcionado. Registra un nuevo usuario para acceder.")
                return@setOnClickListener
            } else if (!usuarioExtraido.clave.equals(Hash.md5(inputClave.text.toString()))) {
                entidadesVentanaEmergentes!!.ventanaEmergenteError(this, viewContenidoLogin,"Contraseña incorrecta, vuelva a intentarlo.")
                return@setOnClickListener
            } else {
                val intent = Intent(this@Login, MainActivity::class.java)
                intent.putExtra("nombre", usuarioExtraido.nombre)
                intent.putExtra("apellidos", usuarioExtraido.apellidos)
                intent.putExtra("usuarioLogueado", usuarioExtraido.nombreUsuario)
                intent.putExtra("email", usuarioExtraido.email)
                controladorUsuario!!.cerrar()
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
            entidadesVentanaEmergentes!!.ventanaEmergenteError(this, viewContenidoLogin,"No es posible cerrar sesión en este momento.")
        }
    }
}