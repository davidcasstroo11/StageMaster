package com.example.stagemaster.vistas

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.stagemaster.R

class VerDetallesUsuario: AppCompatActivity() {
    private lateinit var btnVolver: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.conf_ver_detalles)

        btnVolver = findViewById(R.id.btnVolver)
        btnVolver.setOnClickListener {
            val intent = Intent(this@VerDetallesUsuario, ConfiguracionFragment::class.java)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}