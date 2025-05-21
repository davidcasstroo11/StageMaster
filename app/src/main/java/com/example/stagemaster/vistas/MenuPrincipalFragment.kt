package com.example.stagemaster.vistas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stagemaster.R
import com.example.stagemaster.controlador.ArtistaController
import com.example.stagemaster.controlador.EventoController
import com.example.stagemaster.modeloBBDD.Evento
import java.text.SimpleDateFormat
import java.util.Locale

class MenuPrincipalFragment: Fragment() {
    private var mAdapter: ControladorRecylerView? = null
    private lateinit var recyclerViewDestacados: RecyclerView
    private lateinit var recyclerViewOtros: RecyclerView
    private var eventosListDestacados = ArrayList<Evento>()
    private var eventosListOtros = ArrayList<Evento>()

    private lateinit var controladorEvento: EventoController
    private lateinit var controladorArtista: ArtistaController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView : View = inflater.inflate(R.layout.fragment_menu_principal, container, false)
        rootView.tag = "MenuPrincipal Fragment"

        controladorArtista = ArtistaController(requireContext())
        controladorEvento = EventoController(requireContext())

        if (controladorEvento.selectEventosEntradas().isEmpty()) {
            insertarArtistas()
            insertarEventos()
        }


        insertarOtrosEventos()

        recyclerViewDestacados = rootView.findViewById(R.id.recyclerViewDestacados)
        recyclerViewDestacados.layoutManager = LinearLayoutManager(requireContext())
        insertarEventosDestacados()
        mAdapter = ControladorRecylerView(eventosListDestacados)
        recyclerViewDestacados.adapter = mAdapter


        recyclerViewOtros = rootView.findViewById(R.id.recyclerViewOtros)
        recyclerViewOtros.layoutManager = LinearLayoutManager(requireContext())
        insertarOtrosEventos()
        mAdapter = ControladorRecylerView(eventosListOtros)
        recyclerViewOtros.adapter = mAdapter

        return rootView
    }

    private fun insertarEventosDestacados() {
        eventosListDestacados = controladorEvento.selectEventosEntradas()
    }

    private fun insertarOtrosEventos() {
        eventosListOtros = controladorEvento.selectOtrosEventos()
    }

    private fun insertarArtistas() {
        val formatoFecha = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        controladorArtista.insertarArtista("David", "España", formatoFecha.parse("2020-03-21") , R.drawable.home)
        controladorArtista.insertarArtista("Rafael", "España", formatoFecha.parse("2020-03-21") , R.drawable.home)
        controladorArtista.insertarArtista("Carlos", "España", formatoFecha.parse("2020-03-21") , R.drawable.home)
    }

    private fun insertarEventos() {
        controladorEvento.insertarEvento("David", 23.3, "Malaga", "España", 0,  "2020-03-21" , R.drawable.home, 1)
        controladorEvento.insertarEvento("Fael", 23.3, "Malaga", "España", 20, "2020-03-21" , R.drawable.home, 1)
        controladorEvento.insertarEvento("Carlos", 23.3, "Malaga", "España", 10,  "2020-03-21" , R.drawable.home, 1)
    }
}