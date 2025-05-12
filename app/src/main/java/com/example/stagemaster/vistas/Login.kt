package com.example.stagemaster.vistas

import android.annotation.SuppressLint
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.stagemaster.R
import com.example.stagemaster.controlador.UsuarioController
import com.example.stagemaster.modeloBBDD.StageMasterDB

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

//        conexion = StageMasterDB(this)
//        db = conexion!!.writableDatabase

        btnAcceder = findViewById(R.id.btnAcceder)
        inputEmail = findViewById(R.id.inputEmailLogin)
        inputClave = findViewById(R.id.inputClaveLogin)
        textRegistro = findViewById(R.id.textRegistro)
        textRestablecerContra = findViewById(R.id.textRestablecerContraseñaLog)

        btnAcceder.setOnClickListener {
            if (inputEmail.text == null || inputClave.text == null) {
                return@setOnClickListener
            }
            val intent = Intent(this@Login, MainActivity::class.java)
            startActivity(intent)
        }
        textRegistro.setOnClickListener {
            val intent = Intent(this@Login, Registro::class.java)
            startActivity(intent)
        }
        textRestablecerContra.setOnClickListener {
            val intent = Intent(this@Login, RestablecerContraLogin::class.java)
            startActivity((intent))
        }
    }
}