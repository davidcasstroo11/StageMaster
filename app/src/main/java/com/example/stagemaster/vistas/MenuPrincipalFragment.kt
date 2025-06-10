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

        // Si no se encuentran registros de eventos en el sistema, se insertan al acceder al menú principal
        if (controladorEvento.selectEventosEntradas().isEmpty()) {
            insertarArtistas()
            insertarEventos()
        }

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

        controladorArtista.cerrar()
        controladorEvento.cerrar()
        return rootView
    }

    /**
     * Método para insertar los eventos destacados en su sección correspondiente.
     */
    private fun insertarEventosDestacados() {
        eventosListDestacados = controladorEvento.selectEventosEntradas()
    }

    /**
     * Método para insertar otros eventos en su sección correspondiente.
     */
    private fun insertarOtrosEventos() {
        eventosListOtros = controladorEvento.selectOtrosEventos()
    }

    /**
     * Método para insertar artistas que luego permitiran manejarse al crear eventos.
     */
    private fun insertarArtistas() {
        controladorArtista.insertarArtista("Anuel AA", "Puerto Rico", "2020-03-21" , R.drawable.anuel)
        controladorArtista.insertarArtista("Bad Bunny", "España", "2020-03-21" , R.drawable.bad_bunny_latinoamerica_2025)
        controladorArtista.insertarArtista("David Bisbal", "España", "2020-03-21" , R.drawable.david_bisbal)
        controladorArtista.insertarArtista("Melendi", "España", "2020-03-21" , R.drawable.melendi)
        controladorArtista.insertarArtista("Estopa", "España", "2020-03-21" , R.drawable.estopa)
        controladorArtista.insertarArtista("Ozuna", "España", "2020-03-21" , R.drawable.ozuna)
        controladorArtista.insertarArtista("Lola Indigo", "España", "2020-03-21" , R.drawable.lola_indigo)
        controladorArtista.insertarArtista("Quevedo", "España", "2020-03-21" , R.drawable.quevedo)
        controladorArtista.insertarArtista("Raphael", "España", "2020-03-21" , R.drawable.raphael)
        controladorArtista.insertarArtista("Rosario", "España", "2020-03-21" , R.drawable.rosario)
        controladorArtista.insertarArtista("Morat", "España", "2020-03-21" , R.drawable.morat)
        controladorArtista.insertarArtista("Karol G", "España", "2020-03-21" , R.drawable.karol_g)
        controladorArtista.insertarArtista("Drake", "España", "2020-03-21" , R.drawable.drake)
        controladorArtista.insertarArtista("Ed Sheeran", "España", "2020-03-21" , R.drawable.ed_sheeran)
        controladorArtista.insertarArtista("Taylor Swift", "España", "2020-03-21" , R.drawable.taylor_swift)
        controladorArtista.insertarArtista("Rauw Alejandro", "España", "2020-03-21" , R.drawable.rauw_alejandro)
        controladorArtista.insertarArtista("Coldplay", "España", "2020-03-21" , R.drawable.coldplay)
        controladorArtista.insertarArtista("Aitana", "España", "2020-03-21" , R.drawable.aitana)
        controladorArtista.insertarArtista("Myke Towers", "España", "2020-03-21" , R.drawable.myke_towers)
    }

    /**
     * Método para insertar eventos que luego permitiran manejarse al crear eventos.
     */
    private fun insertarEventos() {
        val idAnuel = controladorArtista.obtenerIdArtistaPorNombre("Anuel AA")
        val idBadBunny = controladorArtista.obtenerIdArtistaPorNombre("Bad Bunny")
        val idBisbal = controladorArtista.obtenerIdArtistaPorNombre("David Bisbal")
        val idMelendi = controladorArtista.obtenerIdArtistaPorNombre("Melendi")
        val idEstopa = controladorArtista.obtenerIdArtistaPorNombre("Estopa")
        val idOzuna = controladorArtista.obtenerIdArtistaPorNombre("Ozuna")
        val idLolaIndigo = controladorArtista.obtenerIdArtistaPorNombre("Lola Indigo")
        val idQuevedo = controladorArtista.obtenerIdArtistaPorNombre("Quevedo")
        val idRosario = controladorArtista.obtenerIdArtistaPorNombre("Rosario")
        val idMorat = controladorArtista.obtenerIdArtistaPorNombre("Morat")
        val idRaphael = controladorArtista.obtenerIdArtistaPorNombre("Raphael")
        val idKarolG = controladorArtista.obtenerIdArtistaPorNombre("Karol G")
        val idDrake = controladorArtista.obtenerIdArtistaPorNombre("Drake")
        val idEdSheeran = controladorArtista.obtenerIdArtistaPorNombre("Ed Sheeran")
        val idRauwAlejandro = controladorArtista.obtenerIdArtistaPorNombre("Rauw Alejandro")
        val idColdplay = controladorArtista.obtenerIdArtistaPorNombre("Coldplay")
        val idAitana = controladorArtista.obtenerIdArtistaPorNombre("Aitana")
        val idMykeTowers = controladorArtista.obtenerIdArtistaPorNombre("Myke Towers")


        controladorEvento.insertarEvento(idAnuel.nombreArtista, 80.6, "Malaga", "España", "2025-12-20" ,
            idAnuel.foto, idAnuel.idArtista)
        controladorEvento.insertarEvento(idBadBunny.nombreArtista, 109.5, "Lugo", "España", "2026-08-10" ,
            idBadBunny.foto, idBadBunny.idArtista)
        controladorEvento.insertarEvento(idBisbal.nombreArtista, 20.5, "Madrid", "España", "2025-08-01" ,
            idBisbal.foto, idBisbal.idArtista)
        controladorEvento.insertarEvento(idAnuel.nombreArtista, 75.3, "Barcelona", "España", "2025-07-21" ,
            idAnuel.foto, idAnuel.idArtista)
        controladorEvento.insertarEvento(idMelendi.nombreArtista, 23.3, "Bilbao", "España", "2025-10-10" ,
            idMelendi.foto, idMelendi.idArtista)
        controladorEvento.insertarEvento(idEstopa.nombreArtista, 32.1, "Barcelona", "España", "2026-11-09" ,
            idMelendi.foto, idMelendi.idArtista)
        controladorEvento.insertarEvento(idOzuna.nombreArtista, 65.8, "El Puerto", "España", "2025-10-08" ,
            idOzuna.foto, idOzuna.idArtista)
        controladorEvento.insertarEvento(idLolaIndigo.nombreArtista, 32.5, "Almeria", "España", "2026-05-21" ,
            idLolaIndigo.foto, idLolaIndigo.idArtista)
        controladorEvento.insertarEvento(idQuevedo.nombreArtista, 60.4, "El Puerto", "España", "2026-07-09" ,
            idQuevedo.foto, idQuevedo.idArtista)
        controladorEvento.insertarEvento(idQuevedo.nombreArtista, 50.0, "Lugo", "España", "2026-03-21" ,
            idQuevedo.foto, idQuevedo.idArtista)
        controladorEvento.insertarEvento(idColdplay.nombreArtista, 34.7, "Barcelona", "España", "2025-09-21" ,
            idColdplay.foto, idColdplay.idArtista)
        controladorEvento.insertarEvento(idKarolG.nombreArtista, 78.0, "Madrid", "España", "2026-03-21" ,
            idKarolG.foto, idKarolG.idArtista)
        controladorEvento.insertarEvento(idDrake.nombreArtista, 25.0, "Sevilla", "España", "2025-10-10" ,
            idDrake.foto, idDrake.idArtista)
        controladorEvento.insertarEvento(idEdSheeran.nombreArtista, 28.0, "Sevilla", "España", "2025-09-10" ,
            idEdSheeran.foto, idEdSheeran.idArtista)
        controladorEvento.insertarEvento(idDrake.nombreArtista, 29.0, "Valencia", "España", "2025-11-04" ,
            idDrake.foto, idDrake.idArtista)
        controladorEvento.insertarEvento(idMykeTowers.nombreArtista, 67.0, "Bilbao", "España", "2025-12-23" ,
            idMykeTowers.foto, idMykeTowers.idArtista)
        controladorEvento.insertarEvento(idRosario.nombreArtista, 56.0, "Miami", "EE.UU", "2025-09-30" ,
            idRosario.foto, idRosario.idArtista)
        controladorEvento.insertarEvento(idRauwAlejandro.nombreArtista, 150.0, "Barcelona", "España", "2025-08-12" ,
            idRauwAlejandro.foto, idRauwAlejandro.idArtista)
        controladorEvento.insertarEvento(idAitana.nombreArtista, 54.0, "San Juan", "Puerto.R", "2026-06-11" ,
            idAitana.foto, idAitana.idArtista)
        controladorEvento.insertarEvento(idAitana.nombreArtista, 27.0, "Córdoba", "Argentina", "2025-12-12" ,
            idAitana.foto, idAitana.idArtista)
        controladorEvento.insertarEvento(idRaphael.nombreArtista, 68.0, "Berlin", "Alemania", "2025-09-24" ,
            idRaphael.foto, idRaphael.idArtista)
        controladorEvento.insertarEvento(idMorat.nombreArtista, 98.0, "Luego", "España", "2025-10-01" ,
            idMorat.foto, idMorat.idArtista)
    }
}