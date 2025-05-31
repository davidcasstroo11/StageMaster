package com.example.stagemaster.vistas

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stagemaster.R
import com.example.stagemaster.controlador.EventoController
import com.example.stagemaster.controlador.MisEventosController
import com.example.stagemaster.controlador.UsuarioController
import com.example.stagemaster.modeloBBDD.Evento
import com.example.stagemaster.modeloBBDD.MisEventos

class MisEventosFragment: Fragment() {
    private var mAdapter: ControladorRecyclerViewEventoC? = null
    private lateinit var recyclerViewMisEventos: RecyclerView
    private var misEventosList = ArrayList<MisEventos>()
    private var eventosList = ArrayList<Evento>()
    private lateinit var vistaContenidoMisEventos: View

    private lateinit var controladorUsuario: UsuarioController
    private lateinit var controladorMisEvento: MisEventosController
    private lateinit var controladorEvento: EventoController
    private lateinit var entidadVentanasEmergentes: EntidadVentanasEmergentes
    private lateinit var emailUsuario: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_mis_eventos, container, false)

        controladorUsuario = UsuarioController(requireContext())
        controladorMisEvento = MisEventosController(requireContext())
        controladorEvento = EventoController(requireContext())
        entidadVentanasEmergentes = EntidadVentanasEmergentes()
        emailUsuario = arguments?.getString("email").toString()
        vistaContenidoMisEventos = rootView.findViewById(R.id.contenidoMisEventos)

        recyclerViewMisEventos = rootView.findViewById(R.id.recyclerViewMisEventos)
        recyclerViewMisEventos.layoutManager = LinearLayoutManager(requireContext())
        seleccionarMisEventosUsuario()
        mAdapter = ControladorRecyclerViewEventoC(eventosList, vistaContenidoMisEventos, emailUsuario)
        recyclerViewMisEventos.adapter = mAdapter
        eliminarItemMisEventos()

        return rootView
    }

    private fun seleccionarMisEventosUsuario() {
        eventosList.clear()
        val usuario = controladorUsuario.selectUsuarios(emailUsuario)
        if (usuario != null) {
            misEventosList = controladorMisEvento.selectEventosUsuario(usuario)
            for (evento in misEventosList) {
                val eventos = controladorEvento.selectEventosId(evento.idEvento)
                eventosList.addAll(eventos)
            }
        }
    }

    private fun eliminarItemMisEventos() {
        val simpleCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            @RequiresApi(Build.VERSION_CODES.S)
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val posicion = viewHolder.adapterPosition
                val evento = misEventosList[posicion]

                entidadVentanasEmergentes.ventanaEmergenteAviso(requireContext(), vistaContenidoMisEventos, "¿Seguro que quieres eliminar el evento? Se reembolsará un 25% del coste total.") { eliminar ->
                    if (eliminar) {
                        controladorMisEvento.eliminarEventoUsuario(evento)
                        eventosList.removeAt(posicion)
                        mAdapter?.notifyItemRemoved(posicion)
                    } else mAdapter?.notifyItemChanged(posicion)
                }
            }
            
        }

        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(recyclerViewMisEventos)
    }

    override fun onResume() {
        super.onResume()
        seleccionarMisEventosUsuario()
        mAdapter = ControladorRecyclerViewEventoC(eventosList, vistaContenidoMisEventos, emailUsuario)
        recyclerViewMisEventos.adapter = mAdapter
    }
}