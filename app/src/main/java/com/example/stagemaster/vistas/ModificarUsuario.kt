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

class ModificarUsuario: AppCompatActivity() {
    private lateinit var inputUsuario: EditText
    private lateinit var inputNuevoUsuario: EditText
    private lateinit var inputNuevoUsuarioRepeat: EditText
    private lateinit var btnModificar: Button
    private lateinit var btnVolver: Button
    private lateinit var viewContenidoModificarUsuario: View

    private lateinit var usuarioLogueado: String
    private lateinit var emailLogin: String
    private var controladorUsuarios: UsuarioController? = null
    private var entidadesVentanaEmergentes: EntidadVentanasEmergentes? = null

    @RequiresApi(Build.VERSION_CODES.S)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.conf_modificar_usuario)

        controladorUsuarios = UsuarioController(this)
        entidadesVentanaEmergentes = EntidadVentanasEmergentes()

        inputUsuario = findViewById(R.id.inputUsuario)
        inputNuevoUsuario = findViewById(R.id.inputNuevoUsuario)
        inputNuevoUsuarioRepeat = findViewById(R.id.inputNuevoUsuarioRepeat)
        btnModificar = findViewById(R.id.btnModificar)
        btnVolver = findViewById(R.id.btnVolver)
        viewContenidoModificarUsuario = findViewById(R.id.contenidoModificarUsuario)

        usuarioLogueado = intent.getStringExtra("usuarioLogueado").toString()
        emailLogin = intent.getStringExtra("email").toString()

        // Comprobación para verificar si el usuario a modificar existe
        val usuarioExtraidoUsuario = controladorUsuarios!!.selectUsuariosNombreUsuarios(usuarioLogueado)
        val usuarioExtraidoEmail = controladorUsuarios!!.selectUsuarios(emailLogin)
        if (usuarioExtraidoUsuario != null) inputUsuario.setText(usuarioLogueado)
        else if (usuarioExtraidoEmail != null) inputUsuario.setText(usuarioExtraidoEmail.nombreUsuario)

        btnModificar.setOnClickListener {
            if (inputNuevoUsuario.text.isEmpty() || inputNuevoUsuarioRepeat.text.isEmpty()) {
                entidadesVentanaEmergentes!!.ventanaEmergenteError(this, viewContenidoModificarUsuario,"Verifica que los campos no se encuentren vacíos.")
                return@setOnClickListener
            } else if (!inputNuevoUsuario.text.toString().equals(inputNuevoUsuarioRepeat.text.toString())) {
                entidadesVentanaEmergentes!!.ventanaEmergenteError(this, viewContenidoModificarUsuario,
                    "Verifica que ambos usuarios sean iguales.")
                return@setOnClickListener
            } else if (inputNuevoUsuario.text.toString().equals(inputUsuario.text.toString())) {
                entidadesVentanaEmergentes!!.ventanaEmergenteError(this, viewContenidoModificarUsuario,"Debes introducir un nombre de usuario diferente al que ya tienes.")
                return@setOnClickListener
            } else {
                val intent = Intent(this@ModificarUsuario, ConfiguracionFragment::class.java)
                entidadesVentanaEmergentes!!.ventanaEmergenteAviso(this, viewContenidoModificarUsuario, "¿Seguro que quieres modificar el nombre de usuario?") { redirigir ->
                    if (redirigir) {
                        // Se actualiza el nombre de usuario a través del controlador de usuarios
                        val resultado = controladorUsuarios!!.actualizarNombreUsuario(inputUsuario.text.toString(), inputNuevoUsuario.text.toString())
                        if (resultado > 0) {
                            setResult(Activity.RESULT_OK, intent)
                            controladorUsuarios!!.cerrar()
                            finish()
                        }
                    }
                }
            }
        }

        btnVolver.setOnClickListener {
            val intent = Intent(this@ModificarUsuario, ConfiguracionFragment::class.java)
            setResult(Activity.RESULT_CANCELED, intent)
            finish()
        }
    }
}