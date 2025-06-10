package com.example.stagemaster.vistas

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
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
import com.example.stagemaster.modeloBBDD.Evento
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class ControladorRecyclerViewEventoC(private val misEventosList: ArrayList<Evento>, private val contenidoRecyler: View, private val emailUsuario: String): RecyclerView.Adapter<VistaEventoCompra>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VistaEventoCompra {
        val item = LayoutInflater.from(parent.context)
            .inflate(R.layout.evento_item_layout_evento_c, parent, false)
        return VistaEventoCompra(item)
    }

    override fun getItemCount(): Int {
        return misEventosList.size
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onBindViewHolder(holder: VistaEventoCompra, position: Int) {
        val evento = misEventosList[position]
        val hoy = LocalDate.now()
        holder.nombreArtista?.text = evento.nombreArtista

        // Se obtiene el numero de dias restantes en comparación a la fecha configurada
        holder.diasRestantes?.text = ChronoUnit.DAYS.between(hoy, LocalDate.parse(evento.fecha)).toString()

        holder.fecha?.text = evento.fecha

        // Se coloca la imagen con un poco de sombreado
        holder.foto?.setImageResource(evento.foto)
        holder.foto?.setColorFilter(Color.parseColor("#80000000"))

        holder.itemView.setOnClickListener {
            val contexto = holder.itemView.context
            val intent = Intent(contexto, EventoQR::class.java)
            intent.putExtra("emailUsuario", emailUsuario)
            intent.putExtra("idEvento",evento.idEvento)
            contexto.startActivity(intent)
        }
    }
}

/**
 * Clase que contiene todos los parametros necesarios para configurar datos sobre el recycler view
 */
class VistaEventoCompra(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
    val nombreArtista: TextView? = itemView?.findViewById(R.id.textArtista)
    val fecha: TextView? = itemView?.findViewById(R.id.textFecha)
    val diasRestantes: TextView? = itemView?.findViewById(R.id.textDiasRestantes)
    val foto: ImageView? = itemView?.findViewById(R.id.imageView4)
}