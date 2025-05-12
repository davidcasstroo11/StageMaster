package com.example.stagemaster.vistas

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.stagemaster.R

class RestablecerContraUsuario: AppCompatActivity() {
    private lateinit var btnRestablecerContra: Button
    private lateinit var btnVolver: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.conf_rest_contra)

        btnRestablecerContra = findViewById(R.id.btnRestablecer)
        btnVolver = findViewById(R.id.btnVolver)

        btnRestablecerContra.setOnClickListener {
            val intent = Intent(this@RestablecerContraUsuario, ConfiguracionFragment::class.java)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
        btnVolver.setOnClickListener {
            val intent = Intent(this@RestablecerContraUsuario, ConfiguracionFragment::class.java)
            setResult(Activity.RESULT_CANCELED, intent)
            finish()
        }
    }
}