package com.example.stagemaster.vistas

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.stagemaster.R

class RestablecerContraLogin: AppCompatActivity() {
    private lateinit var btnVolver: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ventana_rest_contra)

        btnVolver = findViewById(R.id.btnVolverLog)
        btnVolver.setOnClickListener {
            val intent = Intent(this@RestablecerContraLogin, Login::class.java)
            setResult(Activity.RESULT_CANCELED, intent)
            finish()
        }
    }
}