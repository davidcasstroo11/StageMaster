package com.example.stagemaster.vistas

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isEmpty
import com.example.stagemaster.R
import com.example.stagemaster.controlador.EventoController
import com.example.stagemaster.controlador.MisEventosController
import com.example.stagemaster.controlador.UsuarioController

class RealizarPago: AppCompatActivity() {
    private lateinit var textInfoPago: TextView
    private lateinit var textPrecio: TextView
    private lateinit var inputNombreApellidos: EditText
    private lateinit var inputDireccion: EditText
    private lateinit var inputNumeroCuenta: EditText
    private lateinit var inputCVC: EditText
    private lateinit var selectMes: Spinner
    private lateinit var selectAnio: Spinner
    private lateinit var btnPagar: Button
    private lateinit var btnCancelar: Button

    private lateinit var viewContenidoPago: View
    private lateinit var nombreArtista: String
    private lateinit var precio: String
    private lateinit var emailUsuario: String
    private lateinit var sede: String
    private lateinit var fecha: String
    private var entidadVentanasEmergentes: EntidadVentanasEmergentes? = null
    private var controladorUsuario: UsuarioController? = null
    private var controladorEvento: EventoController? = null
    private var controladorMisEventos: MisEventosController? = null

    @RequiresApi(Build.VERSION_CODES.S)
    @SuppressLint("MissingInflatedId", "SetTextI18n", "DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ventana_pago)

        textInfoPago = findViewById(R.id.textoInfoPago)
        textPrecio = findViewById(R.id.textoPrecio)
        inputNombreApellidos = findViewById(R.id.inputNombreApellidos)
        inputDireccion = findViewById(R.id.inputDireccion)
        inputNumeroCuenta = findViewById(R.id.inputNumeroCuenta)
        inputCVC = findViewById(R.id.inputCVC)
        selectMes = findViewById(R.id.selectMes)
        selectAnio = findViewById(R.id.selectAnio)
        btnPagar = findViewById(R.id.btnPagar)
        btnCancelar = findViewById(R.id.btnCancelar)

        viewContenidoPago = findViewById(R.id.contenidoPago)
        entidadVentanasEmergentes = EntidadVentanasEmergentes()
        controladorUsuario = UsuarioController(this)
        controladorEvento = EventoController(this)
        controladorMisEventos = MisEventosController(this)

        // Datos a obtener del Recycler View
        nombreArtista = intent.getStringExtra("nombreArtista").toString()
        precio = intent.getStringExtra("precio").toString()
        emailUsuario = intent.getStringExtra("email").toString()
        sede = intent.getStringExtra("sede").toString()
        fecha = intent.getStringExtra("fecha").toString()

        textInfoPago.setText(nombreArtista + " en " + sede)
        textPrecio.setText(precio.substring(6))

        // Datos a manejar en el select(meses y años)
        val meses = listOf("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12")
        val anios = listOf("25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40")

        // Configuración de datos para cada select relacionado con la fecha de caducidad
        var mesSeleccionado = "";
        var anioSeleccionado = ""
        configurarSelectFecha(meses, selectMes) { valor -> mesSeleccionado = valor}
        configurarSelectFecha(anios, selectAnio) { valor -> anioSeleccionado = valor}

        btnPagar.setOnClickListener {
            if (inputNombreApellidos.text.isEmpty() || inputDireccion.text.isEmpty() || inputNumeroCuenta.text.isEmpty()
                || inputCVC.text.isEmpty() || selectMes.isEmpty()) {
                entidadVentanasEmergentes!!.ventanaEmergenteError(this, viewContenidoPago, "Verifica que los campos no se encuentren vacíos.")
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
            } else if (inputNumeroCuenta.text.length < 12) {
                entidadVentanasEmergentes!!.ventanaEmergenteError(this, viewContenidoPago, "Número de tarjeta bancaria inválida.")
                return@setOnClickListener
            } else if (inputCVC.text.length != 3) {
                entidadVentanasEmergentes!!.ventanaEmergenteError(this, viewContenidoPago, "El CVC no es válido.")
                return@setOnClickListener
            } else if (validarFecha(mesSeleccionado, anioSeleccionado)) {
                entidadVentanasEmergentes!!.ventanaEmergenteError(this, viewContenidoPago, "Fecha de caducidad inválida.")
                return@setOnClickListener
            } else {
                entidadVentanasEmergentes!!.ventanaEmergenteAviso(this, viewContenidoPago, "¿Estás seguro de realizar la compra de este? Recuerda que el proceso no es reversible.") { redireccionar ->
                    if (redireccionar) {
                        val intent = Intent(this@RealizarPago, MisEventosFragment::class.java)
                        // Se realizan distintas verificaciones para comprobar algunos datos e insertar el evento seleccionado, referenciando al usuario actual
                        val usuarioActual = controladorUsuario!!.selectUsuarios(emailUsuario)
                        val eventoAPagar = controladorEvento!!.selectEventosNombres(nombreArtista, fecha)
                        var resultado = controladorMisEventos!!.insertarEventoUsuario(eventoAPagar, usuarioActual)
                        if (resultado > 0) {
                            resultado = controladorMisEventos!!.actualizarCodReferenciaEventos(eventoAPagar, usuarioActual)
                            setResult(Activity.RESULT_OK, intent)

                            // Se cierra la conexión con la BBDD
                            controladorEvento!!.cerrar()
                            controladorUsuario!!.cerrar()
                            controladorMisEventos!!.cerrar()
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

    /**
     * Método para configurar los select relacionados con las fechas de caducidad(meses y años)
     * @param datos Lista de datos
     * @param select Objeto Spinner para select
     * @String Devuelve el item seleccionado
     */
    fun configurarSelectFecha(datos: List<String>, select: Spinner, selecion: (String) -> Unit) {
        val adapter = ArrayAdapter(this, R.layout.selector, datos)
        select.adapter = adapter
        select.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val resultado = datos[position]
                selecion(resultado)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    /**
     * Método que permite validar la fecha de caducidad introducida por el usuario antes de realizar el pago
     * @param mesSeleccionado Mes seleccionado
     * @param anioSeleccionado Año seleccionado
     * @return Devuelve true o false
     */
    fun validarFecha(mesSeleccionado: String, anioSeleccionado: String): Boolean {
        val mes = mesSeleccionado.toInt()
        val anio = 2000 + anioSeleccionado.toInt()

        val calendario = Calendar.getInstance()
        val mesActual = calendario.get(Calendar.MONTH) + 1
        val anioActual = calendario.get(Calendar.YEAR)

        if (!(anio == anioActual && mesActual >= mes)) {
            return false
        }
        return true
    }
}