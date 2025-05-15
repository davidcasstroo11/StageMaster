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
    private lateinit var textVerDetalles: TextView
    private lateinit var textRestablecerContrasenia: TextView
    private lateinit var textModificarUsuario: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_configuracion2, container, false)
        rootView.tag = "Configuracion Fragment"

        val nombreLogin = arguments?.getString("nombre")
        val apellidosLogin = arguments?.getString("apellidos")
        val usuarioLogueado = arguments?.getString("usuarioLogueado")
        val emailLogin = arguments?.getString("email")

        textVerDetalles = rootView.findViewById(R.id.textMisDatos)
        textRestablecerContrasenia = rootView.findViewById(R.id.textRestablecerContraseña)
        textModificarUsuario = rootView.findViewById(R.id.textModificarUsuario)

        textVerDetalles.setOnClickListener {
            val intent = Intent(this.context, VerDetallesUsuario::class.java)
            intent.putExtra("nombre", nombreLogin)
            intent.putExtra("apellidos", apellidosLogin)
            intent.putExtra("usuarioLogueado", usuarioLogueado)
            intent.putExtra("email", emailLogin)
            startActivity(intent)
        }
        textRestablecerContrasenia.setOnClickListener {
            val intent = Intent(this.context, RestablecerContraUsuario::class.java)
            intent.putExtra("email", emailLogin)
            startActivity(intent)
        }
        textModificarUsuario.setOnClickListener {
            val intent = Intent(this.context, ModificarUsuario::class.java)
            intent.putExtra("usuarioLogueado", usuarioLogueado)
            startActivity(intent)
        }

        return rootView
    }
}