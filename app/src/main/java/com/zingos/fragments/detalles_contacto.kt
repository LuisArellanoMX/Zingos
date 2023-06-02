package com.zingos.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.zingos.app.ServiciosAdapter
import com.zingos.app.databinding.FragmentDetallesContactoBinding
import com.zingos.app.menu_solicitante
import com.zingos.base_datos.AppDatabase
import com.zingos.base_datos.Servicio

class detalles_contacto : Fragment() {
    lateinit var usr : String
    lateinit var binding : FragmentDetallesContactoBinding
    lateinit var usuarioSeleccionado : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            usr = it.getString("usuario").toString()
            usuarioSeleccionado = it.getString("usuarioSeleccionado").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return FragmentDetallesContactoBinding
            .inflate(inflater,container,false)
            .apply { binding = this }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var listaServicios = emptyList<Servicio>()
        val database = AppDatabase.getDatabase(super.getContext()!!)

        database.servicios().getPorUsuario(usuarioSeleccionado).observe(this, Observer{
            listaServicios = it

            val adapter = ServiciosAdapter(super.getContext()!!, listaServicios)

            binding.listaServ.adapter = adapter
        })

        database.usuarios().get(usuarioSeleccionado).observe(this, Observer {
            binding.titulo.text = it.nombre
            binding.tvCorreoContacto.text = it.correo
            binding.tvTelefonoContacto.text = it.telefono
        })

        binding.listaServ.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(super.getContext(), menu_solicitante::class.java)
            intent.putExtra("usuario", usr)
            intent.putExtra("id", listaServicios[position].id)
            super.getActivity()!!.finish()
            startActivity(intent)
        }

    }
}