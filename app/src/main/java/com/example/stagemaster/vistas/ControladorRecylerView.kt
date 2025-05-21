package com.example.stagemaster.vistas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.stagemaster.R
import com.example.stagemaster.modeloBBDD.Evento

class ControladorRecylerView(private val eventosList: ArrayList<Evento>): RecyclerView.Adapter<VistaEvento>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VistaEvento {
        val item = LayoutInflater.from(parent.context)
            .inflate(R.layout.evento_item_layout, parent, false)

        return VistaEvento(item)
    }

    override fun getItemCount(): Int {
        return eventosList.size
    }

    override fun onBindViewHolder(holder: VistaEvento, position: Int) {
        val evento = eventosList[position]
        holder.nombreArtista?.text = evento.nombreArtista
        holder.precio?.text = evento.precio.toString()
        holder.sede?.text = evento.sede
        holder.fecha?.text = evento.fecha.toString()
        holder.foto?.setImageResource(evento.foto)
    }
}

class VistaEvento(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
    val nombreArtista: TextView? = itemView?.findViewById(R.id.textArtista)
    val precio: TextView? = itemView?.findViewById(R.id.textPrecio)
    val sede: TextView? = itemView?.findViewById(R.id.textCiudad)
    //val pais: TextView? = itemView?.findViewById(R.id.text)
    val fecha: TextView? = itemView?.findViewById(R.id.textFecha)
    val foto: ImageView? = itemView?.findViewById(R.id.imageView4)
}