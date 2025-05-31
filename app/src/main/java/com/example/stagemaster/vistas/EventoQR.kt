package com.example.stagemaster.vistas

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.stagemaster.R
import com.example.stagemaster.controlador.EventoController
import com.example.stagemaster.controlador.MisEventosController
import com.example.stagemaster.controlador.UsuarioController
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder

class EventoQR: AppCompatActivity() {
    private lateinit var textDatos: TextView
    private lateinit var textFecha: TextView
    private lateinit var btnVolver: Button
    private lateinit var imageViewQR: ImageView
    private lateinit var emailUsuario: String
    private lateinit var idEvento: String

    private lateinit var controladorUsuario: UsuarioController
    private lateinit var controladorEvento: EventoController
    private lateinit var controladorMisEventos: MisEventosController

    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ventana_qr)

        controladorUsuario = UsuarioController(this)
        controladorEvento = EventoController(this)
        controladorMisEventos = MisEventosController(this)

        textDatos = findViewById(R.id.textDatos)
        textFecha = findViewById(R.id.textFecha)
        btnVolver = findViewById(R.id.btnVolver)
        imageViewQR = findViewById(R.id.imageViewQR)
        emailUsuario = intent.getStringExtra("emailUsuario").toString()
        idEvento = intent.getIntExtra("idEvento", -1).toString()

        val usuarioExtraido = controladorUsuario.selectUsuarios(emailUsuario)
        val eventoExtraido = controladorEvento.selectEventosId(idEvento.toInt())
        if (usuarioExtraido != null && eventoExtraido != null) {
            textDatos.setText("${eventoExtraido.get(0).nombreArtista} en ${eventoExtraido.get(0).sede}(${eventoExtraido.get(0).pais})")
            textFecha.setText(eventoExtraido.get(0).fecha)
            val eventoSeleccionado = controladorMisEventos.selectMisEventosCod(eventoExtraido.get(0), usuarioExtraido)
            val qr = generarQR(eventoSeleccionado.codReferencia)
            imageViewQR.setImageBitmap(qr)
        }

        btnVolver.setOnClickListener {
            val intent = Intent(this@EventoQR, MisEventosFragment::class.java)
            setResult(Activity.RESULT_CANCELED, intent)
            finish()
        }
    }

    fun generarQR(codReferencia: String): Bitmap? {
        return try {
            val bitMatrix: BitMatrix = MultiFormatWriter().encode(codReferencia, BarcodeFormat.QR_CODE, 400, 400)
            BarcodeEncoder().createBitmap(bitMatrix)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}