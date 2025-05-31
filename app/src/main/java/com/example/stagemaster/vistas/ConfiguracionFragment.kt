package com.example.stagemaster.vistas

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.stagemaster.R
import kotlin.system.exitProcess

class ConfiguracionFragment : Fragment() {
    private lateinit var textVerDetalles: TextView
    private lateinit var textRestablecerContrasenia: TextView
    private lateinit var textModificarUsuario: TextView
    private lateinit var textCerrarSesion: TextView
    private lateinit var textSalir: TextView
    private lateinit var contenidoVistaConfiguracion: View

    private var entidadesVentanaEmergentes: EntidadVentanasEmergentes? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    @SuppressLint("MissingInflatedId")
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

        entidadesVentanaEmergentes = EntidadVentanasEmergentes()
        contenidoVistaConfiguracion = rootView.findViewById(R.id.contenidoConfiguracion)
        textVerDetalles = rootView.findViewById(R.id.textMisDatos)
        textRestablecerContrasenia = rootView.findViewById(R.id.textRestablecerContraseña)
        textModificarUsuario = rootView.findViewById(R.id.textModificarUsuario)
        textSalir = rootView.findViewById(R.id.textSalir)
        textCerrarSesion = rootView.findViewById(R.id.textCerrarSesion)

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
            intent.putExtra("email", emailLogin)
            startActivity(intent)
        }
        textCerrarSesion.setOnClickListener {
            entidadesVentanaEmergentes!!.ventanaEmergenteAviso(requireContext(), contenidoVistaConfiguracion, "¿Seguro que quieres cerrar sesión? Se guardarán todos los datos automáticamente.") { redireccion ->
                if (redireccion) {
                    val intent = Intent(this.context, Login::class.java)
                    startActivity(intent)
                }
            }
        }
        textSalir.setOnClickListener {
            entidadesVentanaEmergentes!!.ventanaEmergenteAviso(requireContext(), contenidoVistaConfiguracion, "¿Seguro que quieres salir de StageMaster? No se perderán los datos al salir.") { redireccion ->
                if (redireccion) {
                    requireActivity().finishAffinity()
                    exitProcess(0)
                }
            }
        }

        return rootView
    }
}