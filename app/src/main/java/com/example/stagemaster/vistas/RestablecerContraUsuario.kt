package com.example.stagemaster.vistas

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.stagemaster.R
import com.example.stagemaster.controlador.UsuarioController

class RestablecerContraUsuario: AppCompatActivity() {
    private lateinit var inputClave: EditText
    private lateinit var inputClaveRepeat: EditText
    private lateinit var btnRestablecerContra: Button
    private lateinit var btnVolver: Button
    private lateinit var viewContenidoRestablecerContraUsuario: View

    private var controladorUsuario: UsuarioController? = null
    private var entidadesVentanaEmergentes: EntidadVentanasEmergentes? = null
    private lateinit var emailLogin: String

    @RequiresApi(Build.VERSION_CODES.S)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.conf_rest_contra)

        controladorUsuario = UsuarioController(this)
        entidadesVentanaEmergentes = EntidadVentanasEmergentes()

        inputClave = findViewById(R.id.inputClave)
        inputClaveRepeat = findViewById(R.id.inputClaveRepeat)
        btnRestablecerContra = findViewById(R.id.btnRestablecer)
        btnVolver = findViewById(R.id.btnVolver)
        viewContenidoRestablecerContraUsuario = findViewById(R.id.contenidoRestablecerContraseñaUsuario)
        emailLogin = intent.getStringExtra("email").toString()

        btnRestablecerContra.setOnClickListener {
            val usuarioExtraido = controladorUsuario!!.selectUsuarios(emailLogin)
            if (inputClave.text.isEmpty() || inputClaveRepeat.text.isEmpty()) {
                entidadesVentanaEmergentes!!.ventanaEmergenteError(this, viewContenidoRestablecerContraUsuario, "Verifica que todos los campos no se encuentran vacíos.")
                return@setOnClickListener
            } else if (!(inputClave.text.length >= 4 && inputClave.text.length <= 12)) {
                entidadesVentanaEmergentes!!.ventanaEmergenteError(this, viewContenidoRestablecerContraUsuario, "Verifica que la contraseña contenga entre 4 y 12 caracteres.")
                return@setOnClickListener
            } else if (!inputClave.text.toString().equals(inputClaveRepeat.text.toString())) {
                entidadesVentanaEmergentes!!.ventanaEmergenteError(this, viewContenidoRestablecerContraUsuario, "Verifica que ambas claves sean iguales.")
                return@setOnClickListener
            } else if (usuarioExtraido.clave.equals(Hash.md5(inputClave.text.toString()))) {
                entidadesVentanaEmergentes!!.ventanaEmergenteError(this, viewContenidoRestablecerContraUsuario, "Proporciona una contraseña que sea diferente a la actual.")
                return@setOnClickListener
            } else {
                val intent = Intent(this@RestablecerContraUsuario, ConfiguracionFragment::class.java)
                entidadesVentanaEmergentes!!.ventanaEmergenteAviso(this, viewContenidoRestablecerContraUsuario, "¿Seguro que quieres restablecer la contraseña?") {redirigir ->
                    if (redirigir) {
                        // Se actualiza la clave a través del controlador del usuario
                        val resultado = controladorUsuario!!.actualizarUsuarioClave(emailLogin, inputClave.text.toString())
                        if (resultado > 0) {
                            setResult(Activity.RESULT_OK, intent)
                            controladorUsuario!!.cerrar()
                            finish()
                        }
                    }
                }
            }
        }
        btnVolver.setOnClickListener {
            val intent = Intent(this@RestablecerContraUsuario, ConfiguracionFragment::class.java)
            setResult(Activity.RESULT_CANCELED, intent)
            finish()
        }
    }
}