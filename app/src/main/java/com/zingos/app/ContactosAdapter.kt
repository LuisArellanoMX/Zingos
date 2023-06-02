package com.zingos.app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.zingos.base_datos.Usuario
import kotlinx.android.synthetic.main.item_contacto.view.*
import kotlinx.android.synthetic.main.item_contacto.view.imageView
import kotlinx.android.synthetic.main.item_contacto.view.nombre

class ContactosAdapter(private val mContext: Context, private val listaUsuarios: List<Usuario>) : ArrayAdapter<Usuario>(mContext, 0, listaUsuarios) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layout = LayoutInflater.from(mContext).inflate(R.layout.item_contacto, parent, false)

        val contacto = listaUsuarios[position]

        layout.nombre.text = contacto.nombre
        layout.telefono.text = contacto.telefono
        layout.imageView.setImageResource(R.drawable.baseline_person_24)

        return layout
    }
}