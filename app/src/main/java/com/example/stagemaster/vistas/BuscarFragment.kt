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

        var ordenacionActiva = false

        imageViewBuscar.setOnClickListener {
            seleccionarEventosBuscar(eventosListBuscar)
            textoResultadoBusqueda.setText(eventosListBuscar.size.toString() + " eventos mostrados")
        }

        btnOrdenacionFecha.setOnClickListener {
            if (!ordenacionActiva) {
                inputBuscar.setText("")
                ordenar(eventosListBuscar, "fecha")
                btnOrdenacionFecha.setTextColor(R.color.pink)
                textoResultadoBusqueda.setText(eventosListBuscar.size.toString() + " eventos mostrados")
                ordenacionActiva = true
            } else {
                btnOrdenacionFecha.setTextColor(R.color.black)
                eventosListBuscar.clear()
                mAdapter?.notifyDataSetChanged()
                textoResultadoBusqueda.setText("")
                ordenacionActiva = false
            }
        }

        btnOrdenacionNombre.setOnClickListener {
            if (!ordenacionActiva) {
                inputBuscar.setText("")
                ordenar(eventosListBuscar, "nombreArtista")
                btnOrdenacionNombre.setTextColor(R.color.pink)
                textoResultadoBusqueda.setText(eventosListBuscar.size.toString() + " eventos mostrados")
                ordenacionActiva = true
            } else {
                btnOrdenacionNombre.setTextColor(R.color.black)
                eventosListBuscar.clear()
                mAdapter?.notifyDataSetChanged()
                textoResultadoBusqueda.setText("")
                ordenacionActiva = false
            }
        }

        btnOrdenacionPrecio.setOnClickListener {
            if (!ordenacionActiva) {
                inputBuscar.setText("")
                ordenar(eventosListBuscar, "precio")
                btnOrdenacionPrecio.setTextColor(R.color.pink)
                textoResultadoBusqueda.setText(eventosListBuscar.size.toString() + " eventos mostrados")
                ordenacionActiva = true
            } else {
                btnOrdenacionPrecio.setTextColor(R.color.black)
                eventosListBuscar.clear()
                mAdapter?.notifyDataSetChanged()
                textoResultadoBusqueda.setText("")
                ordenacionActiva = false
            }
        }

        return rootView
    }

    fun seleccionarEventosBuscar(eventoList: ArrayList<Evento>){
        val eventosResultantes = controladorEvento.selectEventosNombres(inputBuscar.text.toString())
        if (eventosResultantes != null) {
            eventoList.clear()
            eventoList.addAll(eventosResultantes)
            mAdapter?.notifyDataSetChanged()
        }
    }

    fun ordenar(eventoList: ArrayList<Evento>, metodoOrdenacion: String) {
        eventoList.clear()
        val eventosResultantes = controladorEvento.selectEventosOrdenacion(metodoOrdenacion)
        if (eventosResultantes != null) {
            eventoList.addAll(eventosResultantes)
            mAdapter?.notifyDataSetChanged()
        }
    }
}