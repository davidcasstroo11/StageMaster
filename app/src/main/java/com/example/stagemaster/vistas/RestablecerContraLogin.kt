package com.example.stagemaster.vistas

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.stagemaster.R
import com.example.stagemaster.controlador.UsuarioController

class RestablecerContraLogin: AppCompatActivity() {
    private lateinit var inputEmail: EditText
    private lateinit var inputClave: EditText
    private lateinit var inputClaveRepeat: EditText
    private lateinit var btnRestablecerContra: Button
    private lateinit var btnVolver: Button
    private lateinit var viewContenidoRestablecerContraLogin: View

    private var controladorUsuario: UsuarioController? = null
    private var entidadesVentanaEmergentes: EntidadVentanasEmergentes? = null

    @RequiresApi(Build.VERSION_CODES.S)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ventana_rest_contra)

        controladorUsuario = UsuarioController(this)
        entidadesVentanaEmergentes = EntidadVentanasEmergentes()

        inputEmail = findViewById(R.id.inputEmailContra)
        inputClave = findViewById(R.id.inputClaveContra)
        inputClaveRepeat = findViewById(R.id.inputClaveContraRepeat)
        btnRestablecerContra = findViewById(R.id.btnRestablecerContra)
        btnVolver = findViewById(R.id.btnVolverContra)
        viewContenidoRestablecerContraLogin = findViewById(R.id.contenidoRestContraLogin)

        btnRestablecerContra.setOnClickListener {
            val usuarioExtraido = controladorUsuario!!.selectUsuarios(inputEmail.text.toString())
            if (inputEmail.text.isEmpty() || inputClave.text.isEmpty() || inputClaveRepeat.text.isEmpty()) {
                entidadesVentanaEmergentes!!.ventanaEmergenteError(this, viewContenidoRestablecerContraLogin,"Verifica que los campos no se encuentren vacíos.")
                return@setOnClickListener
            } else if (usuarioExtraido == null) {
                entidadesVentanaEmergentes!!.ventanaEmergenteError(this, viewContenidoRestablecerContraLogin,"No se ha encontrado el email proporcionado.")
                return@setOnClickListener
            } else if (!(inputClave.text.length >= 4 && inputClave.text.length <= 12)) {
                entidadesVentanaEmergentes!!.ventanaEmergenteError(this, viewContenidoRestablecerContraLogin, "Verifica que la contraseña contenga entre 4 y 12 caracteres.")
                return@setOnClickListener
            } else if (!inputClave.text.toString().equals(inputClaveRepeat.text.toString())) {
                entidadesVentanaEmergentes!!.ventanaEmergenteError(this, viewContenidoRestablecerContraLogin,"Debes de introducir la contraseña igual en ambos campos.")
                return@setOnClickListener
            } else {
                val intent = Intent(this@RestablecerContraLogin, Login::class.java)
                entidadesVentanaEmergentes!!.ventanaEmergenteAviso(this, viewContenidoRestablecerContraLogin, "¿Seguro que quieres restablecer la contraseña?") { redirigir ->
                    if (redirigir) {
                        val resultado = controladorUsuario!!.actualizarUsuarioClave(inputEmail.text.toString(), inputClave.text.toString())
                        if (resultado > 0) {
                            controladorUsuario!!.cerrar()
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