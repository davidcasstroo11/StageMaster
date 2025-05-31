package com.example.stagemaster.vistas

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.stagemaster.R
import com.example.stagemaster.controlador.EventoController
import com.example.stagemaster.controlador.MisEventosController
import com.example.stagemaster.controlador.UsuarioController
import java.time.LocalDate

class RealizarPago: AppCompatActivity() {
    private lateinit var textInfoPago: TextView
    private lateinit var inputNombreApellidos: EditText
    private lateinit var inputDireccion: EditText
    private lateinit var inputNumeroCuenta: EditText
    private lateinit var inputCVC: EditText
    private lateinit var inputFechaCad: EditText
    private lateinit var btnPagar: Button
    private lateinit var btnCancelar: Button

    private lateinit var viewContenidoPago: View
    private lateinit var nombreArtista: String
    private lateinit var precio: String
    private lateinit var emailUsuario: String
    private var entidadVentanasEmergentes: EntidadVentanasEmergentes? = null
    private var controladorUsuario: UsuarioController? = null
    private var controladorEvento: EventoController? = null
    private var controladorMisEventos: MisEventosController? = null

    @RequiresApi(Build.VERSION_CODES.S)
    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ventana_pago)

        textInfoPago = findViewById(R.id.textoInfoPago)
        inputNombreApellidos = findViewById(R.id.inputNombreApellidos)
        inputDireccion = findViewById(R.id.inputDireccion)
        inputNumeroCuenta = findViewById(R.id.inputNumeroCuenta)
        inputCVC = findViewById(R.id.inputCVC)
        inputFechaCad = findViewById(R.id.inputFechaCad)
        btnPagar = findViewById(R.id.btnPagar)
        btnCancelar = findViewById(R.id.btnCancelar)

        viewContenidoPago = findViewById(R.id.contenidoPago)
        entidadVentanasEmergentes = EntidadVentanasEmergentes()
        controladorUsuario = UsuarioController(this)
        controladorEvento = EventoController(this)
        controladorMisEventos = MisEventosController(this)
        nombreArtista = intent.getStringExtra("nombreArtista").toString()
        precio = intent.getStringExtra("precio").toString()
        emailUsuario = intent.getStringExtra("email").toString()

        textInfoPago.setText(nombreArtista + " " + precio)

        inputFechaCad.setOnClickListener {
            val calendario = Calendar.getInstance()
            val anio = calendario.get(Calendar.YEAR)
            val mes = calendario.get(Calendar.MONTH)

            val datePicker = android.app.DatePickerDialog(this, { _, anioSeleccionado, mesSeleccionado, _ ->
                val fechaFormateada = String.format("%02d/%02d", mesSeleccionado + 1, anioSeleccionado % 100)
                inputFechaCad.setText(fechaFormateada)
                }, anio, mes, 1)

            datePicker.show()
        }

        btnPagar.setOnClickListener {
            if (inputNombreApellidos.text.isEmpty() || inputDireccion.text.isEmpty() || inputNumeroCuenta.text.isEmpty()
                || inputCVC.text.isEmpty() || inputFechaCad.text.isEmpty()) {
                entidadVentanasEmergentes!!.ventanaEmergenteError(this, viewContenidoPago, "Verifique que los campos no se encuentren vacíos.")
                return@setOnClickListener
            } else if (inputNumeroCuenta.text.length != 12) {
                entidadVentanasEmergentes!!.ventanaEmergenteError(this, viewContenidoPago, "Número de tarjeta bancaria inválida.")
                return@setOnClickListener
            } else if (!inputNumeroCuenta.text.startsWith("51") &&
                !inputNumeroCuenta.text.startsWith("52") &&
                !inputNumeroCuenta.text.startsWith("53") &&
                !inputNumeroCuenta.text.startsWith("54") &&
                !inputNumeroCuenta.text.startsWith("55") &&
                !inputNumeroCuenta.text.startsWith('4')) {
                entidadVentanasEmergentes!!.ventanaEmergenteError(this, viewContenidoPago, "Número de tarjeta bancaria inválida.")
                return@setOnClickListener
            } else if (inputNumeroCuenta.text.length != 12) {
                entidadVentanasEmergentes!!.ventanaEmergenteError(this, viewContenidoPago, "La fecha de caducidad no puede ser menor a la actual.")
                return@setOnClickListener
            } else {
                entidadVentanasEmergentes!!.ventanaEmergenteAviso(this, viewContenidoPago, "¿Estás seguro de realizar la compra de este? Recuerda que el proceso no es reversible.") { redireccionar ->
                    if (redireccionar) {
                        val intent = Intent(this@RealizarPago, MisEventosFragment::class.java)
                        val usuarioActual = controladorUsuario!!.selectUsuarios(emailUsuario)
                        val eventoAPagar = controladorEvento!!.selectEventosNombres(nombreArtista)
                        var resultado = controladorMisEventos!!.insertarEventoUsuario(eventoAPagar.get(0), usuarioActual)
                        if (resultado > 0) {
                            resultado = controladorMisEventos!!.actualizarCodReferenciaEventos(eventoAPagar.get(0), usuarioActual)
                            setResult(Activity.RESULT_OK, intent)
                            finish()
                        }
                    }
                }
            }
        }

        btnCancelar.setOnClickListener {
            val intent = Intent(this@RealizarPago, MenuPrincipalFragment::class.java)
            setResult(Activity.RESULT_CANCELED, intent)
            finish()
        }
    }
}