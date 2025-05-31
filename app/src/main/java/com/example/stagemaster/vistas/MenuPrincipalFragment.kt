package com.example.stagemaster.vistas

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stagemaster.R
import com.example.stagemaster.controlador.ArtistaController
import com.example.stagemaster.controlador.EventoController
import com.example.stagemaster.modeloBBDD.Artista
import com.example.stagemaster.modeloBBDD.Evento
import java.text.SimpleDateFormat
import java.util.Locale

class MenuPrincipalFragment: Fragment() {
    private var mAdapter: ControladorRecylerView? = null
    private lateinit var recyclerViewDestacados: RecyclerView
    private lateinit var recyclerViewOtros: RecyclerView
    private var eventosListDestacados = ArrayList<Evento>()
    private var eventosListOtros = ArrayList<Evento>()

    private var emailUsuario: String? = null
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
        emailUsuario = arguments?.getString("email")

        if (controladorEvento.selectEventosEntradas().isEmpty()) {
            insertarArtistas()
            insertarEventos()
        }

        insertarOtrosEventos()

        recyclerViewDestacados = rootView.findViewById(R.id.recyclerViewDestacados)
        recyclerViewDestacados.layoutManager = LinearLayoutManager(requireContext())
        insertarEventosDestacados()
        mAdapter = ControladorRecylerView(eventosListDestacados, emailUsuario.toString())
        recyclerViewDestacados.adapter = mAdapter


        recyclerViewOtros = rootView.findViewById(R.id.recyclerViewOtros)
        recyclerViewOtros.layoutManager = LinearLayoutManager(requireContext())
        insertarOtrosEventos()
        mAdapter = ControladorRecylerView(eventosListOtros, emailUsuario.toString())
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
        controladorArtista.insertarArtista("Carlos", "España", formatoFecha.parse("2020-03-21") , R.drawable.home)
        controladorArtista.insertarArtista("Carlos", "España", formatoFecha.parse("2020-03-21") , R.drawable.home)
        controladorArtista.insertarArtista("Carlos", "España", formatoFecha.parse("2020-03-21") , R.drawable.home)
        controladorArtista.insertarArtista("Carlos", "España", formatoFecha.parse("2020-03-21") , R.drawable.home)
        controladorArtista.insertarArtista("Carlos", "España", formatoFecha.parse("2020-03-21") , R.drawable.home)
    }

    private fun insertarEventos() {
        controladorEvento.insertarEvento("Anuel AA", 80.6, "Malaga", "España", 0,  "2025-03-21" ,
            R.drawable.anuel, 1)
        controladorEvento.insertarEvento("Bad Bunny", 109.5, "Lugo", "España", 20, "2025-03-21" ,
            R.drawable.bad_bunny_latinoamerica_2025, 2)
        controladorEvento.insertarEvento("David Bisbal", 20.5, "Madrid", "España", 10,  "2025-03-21" ,
            R.drawable.david_bisbal, 3)
        controladorEvento.insertarEvento("Anuel AA", 75.3, "Barcelona", "España", 0,  "2025-03-21" ,
            R.drawable.anuel, 1)
        controladorEvento.insertarEvento("Melendi", 23.3, "Bilbao", "España", 0,  "2025-03-21" ,
            R.drawable.melendi, 4)
        controladorEvento.insertarEvento("Estopa", 32.1, "Barcelona", "España", 0,  "2025-035-21" ,
            R.drawable.estopa, 5)
        controladorEvento.insertarEvento("Ozuna", 65.8, "El Puerto", "España", 0,  "2025-03-21" ,
            R.drawable.ozuna, 6)
        controladorEvento.insertarEvento("Lola Indigo", 32.5, "Almeria", "España", 0,  "2025-03-21" ,
            R.drawable.lola_indigo, 7)
        controladorEvento.insertarEvento("Quevedo", 60.4, "El Puerto", "España", 0,  "2025-04-21" ,
            R.drawable.quevedo, 8)
        controladorEvento.insertarEvento("Quevedo", 50.0, "Barcelona", "España", 0,  "2025-03-21" ,
            R.drawable.quevedo, 8)
    }
}