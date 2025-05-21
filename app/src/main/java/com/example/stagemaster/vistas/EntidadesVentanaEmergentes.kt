package com.example.stagemaster.vistas

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.PopupWindow
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.stagemaster.R

class EntidadesVentanaEmergentes : AppCompatActivity() {
    private lateinit var textMensaje: TextView
    private lateinit var btnVentanaEmergente: Button

    @RequiresApi(Build.VERSION_CODES.S)
    fun ventanaEmergenteError(context: Context, vistaContenido: View, mensajeError: String) {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.ventana_emergente_error, null)
        val popupWindow = PopupWindow(view, 1000, 900, true)

        popupWindow.showAtLocation(vistaContenido, Gravity.CENTER, 0, 0)
        btnVentanaEmergente = view.findViewById(R.id.btnAceptar)
        textMensaje = view.findViewById(R.id.textInfoError)
        textMensaje.text = mensajeError

        vistaContenido.setRenderEffect(
            RenderEffect.createBlurEffect(15f, 15f, Shader.TileMode.CLAMP)
        )

        btnVentanaEmergente.setOnClickListener {
            vistaContenido.setRenderEffect(null)
            popupWindow.dismiss()
        }
    }

    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.S)
    fun ventanaEmergenteInfo(context: Context, vistaContenido: View, mensajeError: String) {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.ventana_emergente_info, null)
        val popupWindow = PopupWindow(view, 1000, 900, true)

        popupWindow.showAtLocation(vistaContenido, Gravity.CENTER, 0, 0)
        btnVentanaEmergente = view.findViewById(R.id.btnAceptar)
        textMensaje = view.findViewById(R.id.textInfoError)
        textMensaje.text = mensajeError

        vistaContenido.setRenderEffect(
            RenderEffect.createBlurEffect(15f, 15f, Shader.TileMode.CLAMP)
        )

        btnVentanaEmergente.setOnClickListener {
            vistaContenido.setRenderEffect(null)
            popupWindow.dismiss()
        }

        popupWindow.setOnDismissListener {
            vistaContenido.setRenderEffect(null)
            popupWindow.dismiss()
        }
    }
}