package com.example.grizel.recyclerview

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.single_person.view.*

class PersonasAdapter(var personas: ArrayList<Persona>, var context:Context):RecyclerView.Adapter<PersonasAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_person, parent, false)
        val viewHolder = ViewHolder(view)
        view.tag = viewHolder
        return viewHolder
    }

    override fun getItemCount(): Int {
        return personas.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nombre!!.text = personas[position].nombre
        holder.genero!!.text = personas[position].genero
        Picasso.get().load(personas[position].foto)
            .placeholder(R.drawable.ic_launcher_foreground).into(holder.imagen)
    }

    class ViewHolder(vista: View):RecyclerView.ViewHolder(vista){
        var imagen:ImageView? = null
        var nombre:TextView? = null
        var genero:TextView? = null

        init {
            imagen = vista.ivFoto
            nombre = vista.tvNombre
            genero = vista.tvGenero
        }
    }
}