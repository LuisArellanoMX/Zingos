package com.zingos.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.zingos.app.ServiciosAdapter
import com.zingos.app.databinding.FragmentMisServiciosBinding
import com.zingos.app.menu_prestador
import com.zingos.base_datos.AppDatabase
import com.zingos.base_datos.Servicio

class mis_servicios : Fragment() {
    lateinit var usr : String
    lateinit var binding : FragmentMisServiciosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            usr = it.getString("usuario").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return FragmentMisServiciosBinding
            .inflate(inflater, container, false)
            .apply { binding = this }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var listaServicios = emptyList<Servicio>()
        val database = AppDatabase.getDatabase(super.getContext()!!)

        database.servicios().getPorUsuario(usr).observe(this, Observer{
            listaServicios = it

            val adapter = ServiciosAdapter(super.getContext()!!, listaServicios)

            binding.lista.adapter = adapter
        })

        binding.lista.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(super.getContext(), menu_prestador::class.java)
            intent.putExtra("usuario", usr)
            intent.putExtra("id", listaServicios[position].id)
            intent.putExtra("seSelecciono",true)
            super.getActivity()!!.finish()
            startActivity(intent)
        }
    }
}