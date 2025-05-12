package com.example.stagemaster.vistas

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.stagemaster.R

class ConfiguracionFragment : Fragment() {
    private lateinit var verDetalles: TextView
    private lateinit var restablecerContrasenia: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_configuracion2, container, false)
        rootView.tag = "Configuracion Fragment"

        verDetalles = rootView.findViewById(R.id.textMisDatos)
        restablecerContrasenia = rootView.findViewById(R.id.textRestablecerContraseña)

        verDetalles.setOnClickListener {
            val intent = Intent(this.context, VerDetallesUsuario::class.java)
            startActivity(intent)
        }
        restablecerContrasenia.setOnClickListener {
            val intent = Intent(this.context, RestablecerContraUsuario::class.java)
            startActivity(intent)
        }

        return rootView
    }
}