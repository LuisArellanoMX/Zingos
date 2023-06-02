package com.zingos.app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.zingos.base_datos.Servicio
import kotlinx.android.synthetic.main.item_servicio.view.*

class ServiciosAdapter(private val mContext: Context, private val listaServicios: List<Servicio>) : ArrayAdapter<Servicio>(mContext, 0, listaServicios) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layout = LayoutInflater.from(mContext).inflate(R.layout.item_servicio, parent, false)

        val servicio = listaServicios[position]

        layout.nombre.text = servicio.nombre_servicio
        layout.descripcion.text = servicio.descripcion
        when(servicio.categoria){
            "Tecnologia" -> layout.imageView.setImageResource(R.drawable.cat_tecnologia)
            "Asesorias" -> layout.imageView.setImageResource(R.drawable.cat_asesorias)
            "Mantenimiento" -> layout.imageView.setImageResource(R.drawable.cat_mantenimiento)
            "Automotriz" -> layout.imageView.setImageResource(R.drawable.cat_automotriz)
            "Administracion y Logistica" -> layout.imageView.setImageResource(R.drawable.cat_administracion)
            "Salud" -> layout.imageView.setImageResource(R.drawable.cat_salud)
        }
        return layout
    }

}

/*
class ProductosAdapter(private val mContext: Context, private val listaProductos: List<Producto>) : ArrayAdapter<Producto>(mContext, 0, listaProductos) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layout = LayoutInflater.from(mContext).inflate(R.layout.item_producto, parent, false)

        val producto = listaProductos[position]

        layout.nombre.text = producto.nombre
        layout.precio.text = "$${producto.precio}"
        layout.imageView.setImageResource(producto.imagen)

        return layout
    }

}
 */