package com.zingos.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.zingos.app.ServiciosAdapter
import com.zingos.app.databinding.FragmentListaServiciosBinding
import com.zingos.app.menu_solicitante
import com.zingos.base_datos.AppDatabase
import com.zingos.base_datos.Servicio


class lista_servicios : Fragment() {
    lateinit var binding : FragmentListaServiciosBinding
    lateinit var usr:String
    lateinit var categoria:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            usr = it.getString("usuario").toString()
            categoria = it.getString("categoria").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return FragmentListaServiciosBinding
            .inflate(inflater, container, false)
            .apply { binding = this }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var listaServicios = emptyList<Servicio>()
        val database = AppDatabase.getDatabase(super.getContext()!!)

        if(categoria == "todos"){
            database.servicios().getAll().observe(this, Observer{
                listaServicios = it

                val adapter = ServiciosAdapter(super.getContext()!!, listaServicios)

                binding.lista.adapter = adapter
            })
        }else{
            database.servicios().getPorCategoria(categoria).observe(this, Observer{
                listaServicios = it

                val adapter = ServiciosAdapter(super.getContext()!!, listaServicios)

                binding.lista.adapter = adapter
            })
        }

        binding.lista.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(super.getContext(), menu_solicitante::class.java)
            intent.putExtra("usuario", usr)
            intent.putExtra("id", listaServicios[position].id)
            super.getActivity()!!.finish()
            startActivity(intent)
        }
    }
}