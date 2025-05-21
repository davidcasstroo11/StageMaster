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

class RestablecerContraLogin: AppCompatActivity() {
    private lateinit var inputEmail: EditText
    private lateinit var inputClave: EditText
    private lateinit var inputClaveRepeat: EditText
    private lateinit var btnRestablecerContra: Button
    private lateinit var btnVolver: Button
    private lateinit var vistaContenidoRestablecerContraLogin: View

    private var controladorUsuario: UsuarioController? = null
    private var entidadesVentanaEmergentes: EntidadesVentanaEmergentes? = null

    @RequiresApi(Build.VERSION_CODES.S)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ventana_rest_contra)

        controladorUsuario = UsuarioController(this)
        entidadesVentanaEmergentes = EntidadesVentanaEmergentes()

        inputEmail = findViewById(R.id.inputEmailContra)
        inputClave = findViewById(R.id.inputClaveContra)
        inputClaveRepeat = findViewById(R.id.inputClaveContraRepeat)
        btnRestablecerContra = findViewById(R.id.btnRestablecerContra)
        btnVolver = findViewById(R.id.btnVolverContra)
        vistaContenidoRestablecerContraLogin = findViewById(R.id.contenidoRestContraLogin)

        btnRestablecerContra.setOnClickListener {
            val usuarioExtraido = controladorUsuario!!.selectUsuarios(inputEmail.text.toString())
            if (inputEmail.text.isEmpty() || inputClave.text.isEmpty() || inputClaveRepeat.text.isEmpty()) {
                entidadesVentanaEmergentes!!.ventanaEmergenteError(this, vistaContenidoRestablecerContraLogin,"Verifique que los campos no se encuentren vacíos.")
                return@setOnClickListener
            } else if (usuarioExtraido == null) {
                entidadesVentanaEmergentes!!.ventanaEmergenteError(this, vistaContenidoRestablecerContraLogin,"No se ha encontrado el email proporcionado.")
                return@setOnClickListener
            } else if (!inputClave.text.toString().equals(inputClaveRepeat.text.toString())) {
                entidadesVentanaEmergentes!!.ventanaEmergenteError(this, vistaContenidoRestablecerContraLogin,"Debes de introducir la contraseña igual en ambos campos.")
                return@setOnClickListener
            } else {
                val intent = Intent(this@RestablecerContraLogin, Login::class.java)
                val resultado = controladorUsuario!!.actualizarUsuarioClave(inputEmail.text.toString(), inputClave.text.toString())
                if (resultado > 0) {
                    setResult(Activity.RESULT_OK, intent)
                    finish()
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