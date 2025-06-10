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

class EntidadVentanasEmergentes : AppCompatActivity() {
    private lateinit var textMensaje: TextView
    private lateinit var btnVentanaEmergenteAceptar: Button
    private lateinit var btnVentanaEmergenteCancelar: Button

    /**
     * Método que permite mostrar en pantalla una ventana emergente de tipo error.
     * Se suelen mostrar a la hora comparar parametros en distintos formularios que contiene la app.
     * @param context Contexto para iniciar la ventana
     * @param vistaContenido Vista para difuminar ventana
     * @param mensajeError Mensaje a mostrar
     */
    @RequiresApi(Build.VERSION_CODES.S)
    fun ventanaEmergenteError(context: Context, vistaContenido: View, mensajeError: String) {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.ventana_emergente_error, null)
        val popupWindow = PopupWindow(view, 1000, 900, true)

        // Se coloca la ventana en el centro de la pantalla
        popupWindow.showAtLocation(vistaContenido, Gravity.CENTER, 0, 0)
        btnVentanaEmergenteAceptar = view.findViewById(R.id.btnAceptar)
        textMensaje = view.findViewById(R.id.textInfoError)
        textMensaje.text = mensajeError

        vistaContenido.setRenderEffect(
            // Establece el efecto de difuminación por detrás
            RenderEffect.createBlurEffect(15f, 15f, Shader.TileMode.CLAMP)
        )

        btnVentanaEmergenteAceptar.setOnClickListener {
            // Se restablece el diseño y se cierra la ventana
            vistaContenido.setRenderEffect(null)
            popupWindow.dismiss()
        }
    }

    /**
     * Método que permite mostrar en pantalla una ventana emergente de tipo aviso.
     * Esta ventana incluye dos opciones para aplicar alguna funcionalidad contenida o no.
     * @param context Contexto para iniciar la ventana
     * @param vistaContenido Vista para difuminar ventana
     * @param mensajeAviso Mensaje a mostrar
     * @return Devuelve un resultado true o false, dependiendo la opción elegida.
     */
    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.S)
    fun ventanaEmergenteAviso(context: Context, vistaContenido: View, mensajeAviso: String, redirigir: (Boolean) -> Unit) {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.ventana_emergente_aviso, null)
        val popupWindow = PopupWindow(view, 1000, 900, true)

        // Se coloca la ventana en el centro de la pantalla
        popupWindow.showAtLocation(vistaContenido, Gravity.CENTER, 0, 0)
        btnVentanaEmergenteAceptar = view.findViewById(R.id.btnAceptar)
        btnVentanaEmergenteCancelar = view.findViewById(R.id.btnCancelar)
        textMensaje = view.findViewById(R.id.textInfoError)
        textMensaje.text = mensajeAviso

        vistaContenido.setRenderEffect(
            // Establece el efecto de difuminación por detrás
            RenderEffect.createBlurEffect(15f, 15f, Shader.TileMode.CLAMP)
        )

        btnVentanaEmergenteAceptar.setOnClickListener {
            redirigir(true)
            vistaContenido.setRenderEffect(null)
            popupWindow.dismiss()
        }

        btnVentanaEmergenteCancelar.setOnClickListener {
            redirigir(false)
            vistaContenido.setRenderEffect(null)
            popupWindow.dismiss()
        }

        popupWindow.setOnDismissListener {
            // Se restablece el diseño y se cierra la ventana
            vistaContenido.setRenderEffect(null)
            popupWindow.dismiss()
        }
    }
}