package com.example.stagemaster.vistas

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.stagemaster.R
import com.example.stagemaster.controlador.UsuarioController

class Registro: AppCompatActivity() {
    private lateinit var textLogin: TextView
    private lateinit var inputNombre: EditText
    private lateinit var inputApellidos: EditText
    private lateinit var inputNombreUsuario: EditText
    private lateinit var inputEmail: EditText
    private lateinit var inputClave: EditText
    private lateinit var btnRegistro: Button
    private lateinit var btnCancelar: Button
    private lateinit var vistaContenidoRegistro: View

    private var controladorUsuario: UsuarioController? = null
    private var entidadesVentanaEmergentes: EntidadesVentanaEmergentes? = null

    @RequiresApi(Build.VERSION_CODES.S)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ventana_registro)

        controladorUsuario = UsuarioController(this)
        entidadesVentanaEmergentes = EntidadesVentanaEmergentes()

        textLogin = findViewById(R.id.textLogin)
        inputNombre = findViewById(R.id.inputNombre)
        inputApellidos = findViewById(R.id.inputApellidos)
        inputNombreUsuario = findViewById(R.id.inputNombreUsuario)
        inputEmail = findViewById(R.id.inputEmail)
        inputClave = findViewById(R.id.inputClaveRegistro)
        btnRegistro = findViewById(R.id.btnRegistrar)
        btnCancelar = findViewById(R.id.btnCancelar)
        vistaContenidoRegistro = findViewById(R.id.contenidoRegistro)

        btnRegistro.setOnClickListener {
            val usuarioExtraidoEmail = controladorUsuario!!.selectUsuarios(inputEmail.text.toString())
            val usuarioExtraidoNombreUsuario = controladorUsuario!!.selectUsuariosNombreUsuarios(inputNombreUsuario.text.toString())

            if (inputNombre.text.isEmpty() || inputApellidos.text.isEmpty() || inputNombreUsuario.text.isEmpty() || inputNombreUsuario.text.isEmpty()
                || inputEmail.text.isEmpty() || inputClave.text.isEmpty()) {
                entidadesVentanaEmergentes!!.ventanaEmergenteError(this, vistaContenidoRegistro,"Verifique que los campos no se encuentren vacíos.")
                return@setOnClickListener
            } else if (!inputEmail.text.contains("@gmail.com")){
                entidadesVentanaEmergentes!!.ventanaEmergenteError(this, vistaContenidoRegistro,"Verifique que el email sea el correcto.")
                return@setOnClickListener
            } else if (usuarioExtraidoNombreUsuario != null) {
                entidadesVentanaEmergentes!!.ventanaEmergenteError(this, vistaContenidoRegistro,"Ya existe ese nombre de usuario, elija otro.")
                return@setOnClickListener
            } else if (usuarioExtraidoEmail != null) {
                entidadesVentanaEmergentes!!.ventanaEmergenteError(this, vistaContenidoRegistro,"Ya existe ese email, inicie sesión.")
                return@setOnClickListener
            } else {
                val intent = Intent(this@Registro, MainActivity::class.java)
                val resultado = controladorUsuario!!.insertarUsuario(inputNombre.text.toString(), inputApellidos.text.toString(), inputNombreUsuario.text.toString(),
                    inputEmail.text.toString(), inputClave.text.toString())
                if (resultado > 0) {
                    startActivity(intent)
                }
            }
        }
        textLogin.setOnClickListener {
            val intent = Intent(this@Registro, Login::class.java)
            startActivity(intent)
        }
        btnCancelar.setOnClickListener {
            val intent = Intent(this@Registro, Login::class.java)
            setResult(Activity.RESULT_CANCELED, intent)
            finish()
        }
    }
}