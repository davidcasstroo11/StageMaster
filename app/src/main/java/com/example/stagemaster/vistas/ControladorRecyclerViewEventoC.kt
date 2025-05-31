package com.example.stagemaster.vistas

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.stagemaster.R
import com.example.stagemaster.controlador.EventoController
import com.example.stagemaster.controlador.MisEventosController
import com.example.stagemaster.controlador.UsuarioController
import com.example.stagemaster.modeloBBDD.Evento

class ControladorRecyclerViewEventoC(private val misEventosList: ArrayList<Evento>, private val contenidoRecyler: View, private val emailUsuario: String): RecyclerView.Adapter<VistaEventoCompra>() {
    private lateinit var controladorMisEventos: MisEventosController
    private lateinit var controladorEvento: EventoController
    private lateinit var controladorUsuario: UsuarioController
    private lateinit var entidadVentanasEmergentes: EntidadVentanasEmergentes

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VistaEventoCompra {
        val item = LayoutInflater.from(parent.context)
            .inflate(R.layout.evento_item_layout_evento_c, parent, false)
        controladorMisEventos = MisEventosController(parent.context)
        controladorEvento = EventoController(parent.context)
        controladorUsuario = UsuarioController(parent.context)
        entidadVentanasEmergentes = EntidadVentanasEmergentes()
        return VistaEventoCompra(item)
    }

    override fun getItemCount(): Int {
        return misEventosList.size
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onBindViewHolder(holder: VistaEventoCompra, position: Int) {
        val evento = misEventosList[position]
        holder.nombreArtista?.text = evento.nombreArtista
        holder.fecha?.text = evento.fecha
        holder.sede?.text = evento.sede + "(" + evento.pais + ")"
        holder.foto?.setImageResource(evento.foto)

        holder.itemView.setOnClickListener {
            val contexto = holder.itemView.context
            val intent = Intent(contexto, EventoQR::class.java)
            intent.putExtra("emailUsuario", emailUsuario)
            intent.putExtra("idEvento",evento.idEvento)
            contexto.startActivity(intent)
        }
    }

    /*@RequiresApi(Build.VERSION_CODES.S)
    private fun mostrarMenuEvento(view: View, evento: Evento) {
        val popup = PopupMenu(view.context, view)
        popup.menuInflater.inflate(R.menu.menu_popup, popup.menu)

        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.eliminar_item -> {
                    val controladorEvento = EventoController(view.context)
                    entidadVentanasEmergentes.ventanaEmergenteAviso(view.context,contenidoRecyler,"¿Seguro que quieres eliminar el evento?. Recuerda que el proceso no es reversible." ) { redireccionar ->
                        if (redireccionar) {
                            val usuario = controladorUsuario.selectUsuarios(emailUsuario)
                            val resulado = controladorEvento.eliminarEventoUsuario(usuario,evento)
                            if (resulado > 0) {
                                misEventosList.remove(evento)
                                notifyDataSetChanged()
                            }
                        }
                    }
                    true
                }
                else -> false
            }
        }

        popup.show()
    }*/
}

class VistaEventoCompra(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
    val nombreArtista: TextView? = itemView?.findViewById(R.id.textArtista)
    val fecha: TextView? = itemView?.findViewById(R.id.textFecha)
    val sede: TextView? = itemView?.findViewById(R.id.textCiudad)
    val foto: ImageView? = itemView?.findViewById(R.id.imageView4)
}