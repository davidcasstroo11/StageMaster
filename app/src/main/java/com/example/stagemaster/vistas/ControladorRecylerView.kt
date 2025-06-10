package com.example.stagemaster.vistas

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.stagemaster.R
import com.example.stagemaster.modeloBBDD.Evento

class ControladorRecylerView(private val eventosList: ArrayList<Evento>, private var emailUsuario: String): RecyclerView.Adapter<VistaEvento>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VistaEvento {
        val item = LayoutInflater.from(parent.context)
            .inflate(R.layout.evento_item_layout, parent, false)

        return VistaEvento(item)
    }

    override fun getItemCount(): Int {
        return eventosList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VistaEvento, position: Int) {
        val evento = eventosList[position]
        holder.nombreArtista?.text = evento.nombreArtista
        holder.precio?.text = "Desde " + evento.precio.toString() + " €"
        holder.sede?.text = evento.sede + "(" + evento.pais + ")"
        holder.fecha?.text = evento.fecha
        holder.foto?.setImageResource(evento.foto)

        holder.precio?.setOnClickListener {
            val contexto = holder.itemView.context
            val intent = Intent(contexto, RealizarPago::class.java)
            intent.putExtra("nombreArtista", holder.nombreArtista?.text)
            intent.putExtra("precio", holder.precio.text)
            intent.putExtra("email", emailUsuario)
            intent.putExtra("sede", holder.sede?.text)
            intent.putExtra("fecha", holder.fecha?.text)
            contexto.startActivity(intent)
        }
    }
}

/**
 * Clase que contiene todos los parametros necesarios para configurar datos sobre el recycler view
 */
class VistaEvento(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
    val nombreArtista: TextView? = itemView?.findViewById(R.id.textArtista)
    val precio: TextView? = itemView?.findViewById(R.id.textPrecio)
    val sede: TextView? = itemView?.findViewById(R.id.textCiudad)
    val fecha: TextView? = itemView?.findViewById(R.id.textFecha)
    val foto: ImageView? = itemView?.findViewById(R.id.imageView4)
}