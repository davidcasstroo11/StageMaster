package com.example.stagemaster.vistas

import android.annotation.SuppressLint
import android.media.metrics.Event
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stagemaster.R
import com.example.stagemaster.controlador.EventoController
import com.example.stagemaster.modeloBBDD.Evento

class BuscarFragment: Fragment() {
    private var mAdapter: ControladorRecylerView? = null
    private lateinit var recyclerViewBuscar: RecyclerView
    private lateinit var inputBuscar: EditText
    private lateinit var textoResultadoBusqueda: TextView
    private lateinit var imageViewBuscar: ImageView
    private lateinit var btnOrdenacionFecha: Button
    private lateinit var btnOrdenacionNombre: Button
    private lateinit var btnOrdenacionPrecio: Button

    private var ordenacionActiva: Boolean = false
    private var eventosListBuscar = ArrayList<Evento>()
    private lateinit var controladorEvento: EventoController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("MissingInflatedId", "SetTextI18n", "ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_buscar,container,false)

        val emailUsuario = arguments?.getString("email").toString()

        btnOrdenacionFecha = rootView.findViewById(R.id.btnOrdenacionFecha)
        btnOrdenacionNombre = rootView.findViewById(R.id.btnOrdenacionNombre)
        btnOrdenacionPrecio = rootView.findViewById(R.id.btnOrdenacionPrecio)
        inputBuscar = rootView.findViewById(R.id.inputBuscar)
        textoResultadoBusqueda = rootView.findViewById(R.id.textNumeroBusqueda)
        imageViewBuscar = rootView.findViewById(R.id.imageViewBuscar)
        controladorEvento = EventoController(requireContext())

        recyclerViewBuscar = rootView.findViewById(R.id.recyclerViewBuscar)
        recyclerViewBuscar.layoutManager = LinearLayoutManager(requireContext())
        mAdapter = ControladorRecylerView(eventosListBuscar, emailUsuario)
        recyclerViewBuscar.adapter = mAdapter

        // Se inicializa la ventana con todos los eventos contenidos en la BBDD
        obtenerEventos(eventosListBuscar)
        textoResultadoBusqueda.setText(eventosListBuscar.size.toString() + " eventos mostrados")

        // Lógica al accionar el icono de búscar
        imageViewBuscar.setOnClickListener {
            if (inputBuscar.text.isEmpty()) {
                obtenerEventos(eventosListBuscar)
            } else seleccionarEventosBuscar(eventosListBuscar)
            textoResultadoBusqueda.setText(eventosListBuscar.size.toString() + " eventos mostrados")
        }

        // Configuración de los filtros de ordenación
        configurarOrdenacion(btnOrdenacionFecha, "fecha", R.color.white, R.color.black)
        configurarOrdenacion(btnOrdenacionNombre, "nombreArtista", R.color.pink, R.color.black)
        configurarOrdenacion(btnOrdenacionPrecio, "precio", R.color.pink, R.color.black)

        return rootView
    }

    /**
     * Método que devuelve los eventos que se corresponde con el nombre introducido en la búsqueda
     * @param eventoList Lista de eventos
     */
    fun seleccionarEventosBuscar(eventoList: ArrayList<Evento>){
        val eventosResultantes = controladorEvento.selectEventosNombresBuscar(inputBuscar.text.toString())
        if (eventosResultantes != null) {
            eventoList.clear()
            eventoList.addAll(eventosResultantes)
            mAdapter?.notifyDataSetChanged()
        }
    }

    /**
     * Método que devuelve todos los eventos disponibles que se encuentran en la BBDD
     * @param eventoList Lista de eventos
     */
    fun obtenerEventos(eventoList: ArrayList<Evento>) {
        val eventosResultantes = controladorEvento.selectEventos()
        if (eventosResultantes != null) {
            eventoList.clear()
            eventoList.addAll(eventosResultantes)
            mAdapter?.notifyDataSetChanged()
        }
    }

    /**
     * Método que ordena según los parametros introducidos, en base a cierto campo del evento
     * @param eventoList Lista de eventos
     * @param metodoOrdenacion Campo de ordenación
     */
    fun ordenar(eventoList: ArrayList<Evento>, metodoOrdenacion: String) {
        eventoList.clear()
        val eventosResultantes = controladorEvento.selectEventosOrdenacion(metodoOrdenacion)
        if (eventosResultantes != null) {
            eventoList.addAll(eventosResultantes)
            mAdapter?.notifyDataSetChanged()
        }
    }

    /**
     * Método que configura la lógica al accionar un botón correspondiente de algún filtro de eventos
     * @param boton Filtro que se acciona
     * @param metodoOrdenacion Campo de ordenación
     * @param colorActivo Color que refleja la activación de un filtro
     * @param colorInactivo Color que reflela de inactividad de un filtro
     */
    fun configurarOrdenacion(boton: Button, metodoOrdenacion: String, colorActivo: Int, colorInactivo: Int) {
        boton.setOnClickListener {
            if (!ordenacionActiva) {
                inputBuscar.setText("")
                ordenar(eventosListBuscar, metodoOrdenacion)
                textoResultadoBusqueda.setText(eventosListBuscar.size.toString() + " eventos mostrados")
                ordenacionActiva = true
            } else {
                eventosListBuscar.clear()
                mAdapter?.notifyDataSetChanged()
                textoResultadoBusqueda.setText("")
                ordenacionActiva = false
            }
        }
    }
}