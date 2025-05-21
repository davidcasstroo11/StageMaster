package com.example.stagemaster.vistas

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
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
    private lateinit var imageViewBuscar: ImageView

    private var eventosListBuscar = ArrayList<Evento>()
    private lateinit var controladorEvento: EventoController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_buscar,container,false)

        inputBuscar = rootView.findViewById(R.id.inputBuscar)
        imageViewBuscar = rootView.findViewById(R.id.imageViewBuscar)
        controladorEvento = EventoController(requireContext())

        recyclerViewBuscar = rootView.findViewById(R.id.recyclerViewBuscar)
        recyclerViewBuscar.layoutManager = LinearLayoutManager(requireContext())
        mAdapter = ControladorRecylerView(eventosListBuscar)
        recyclerViewBuscar.adapter = mAdapter

        imageViewBuscar.setOnClickListener {
            seleccionarEventosBuscar()
        }

        return rootView
    }

    private fun seleccionarEventosBuscar(){
        val eventosResultantes = controladorEvento.selectEventosNombres(inputBuscar.text.toString())
        eventosListBuscar.clear()
        eventosListBuscar.addAll(eventosResultantes)
        mAdapter?.notifyDataSetChanged()
    }
}