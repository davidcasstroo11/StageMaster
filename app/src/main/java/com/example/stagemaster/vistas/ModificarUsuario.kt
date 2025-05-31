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
    private lateinit var vistaContenidoModificarUsuario: View

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
        vistaContenidoModificarUsuario = findViewById(R.id.contenidoModificarUsuario)

        usuarioLogueado = intent.getStringExtra("usuarioLogueado").toString()
        emailLogin = intent.getStringExtra("email").toString()
        val usuarioExtraidoUsuario = controladorUsuarios!!.selectUsuariosNombreUsuarios(usuarioLogueado)
        val usuarioExtraidoEmail = controladorUsuarios!!.selectUsuarios(emailLogin)
        if (usuarioExtraidoUsuario != null) inputUsuario.setText(usuarioLogueado)
        else if (usuarioExtraidoEmail != null) inputUsuario.setText(usuarioExtraidoEmail.nombreUsuario)

        btnModificar.setOnClickListener {
            if (inputNuevoUsuario.text.isEmpty() || inputNuevoUsuarioRepeat.text.isEmpty()) {
                entidadesVentanaEmergentes!!.ventanaEmergenteError(this, vistaContenidoModificarUsuario,"Verifique que los campos no se encuentren vacíos.")
                return@setOnClickListener
            } else if (!inputNuevoUsuario.text.toString().equals(inputNuevoUsuarioRepeat.text.toString())) {
                entidadesVentanaEmergentes!!.ventanaEmergenteError(this, vistaContenidoModificarUsuario,
                    "Verifica que ambos usuarios sean iguales.")
                return@setOnClickListener
            } else if (inputNuevoUsuario.text.toString().equals(inputUsuario.text.toString())) {
                entidadesVentanaEmergentes!!.ventanaEmergenteError(this, vistaContenidoModificarUsuario,"Debes introducir un nombre de usuario diferente al que ya tienes.")
                return@setOnClickListener
            } else {
                val intent = Intent(this@ModificarUsuario, ConfiguracionFragment::class.java)
                val resultado = controladorUsuarios!!.actualizarNombreUsuario(inputUsuario.text.toString(), inputNuevoUsuario.text.toString())
                if (resultado > 0) {
                    setResult(Activity.RESULT_OK, intent)
                    finish()
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