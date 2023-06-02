package com.zingos.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.zingos.app.ContactosAdapter
import com.zingos.app.databinding.FragmentListContactosBinding
import com.zingos.app.menu_solicitante
import com.zingos.base_datos.AppDatabase
import com.zingos.base_datos.Usuario


class list_contactos : Fragment() {
    lateinit var usr : String
    lateinit var binding : FragmentListContactosBinding

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
        return FragmentListContactosBinding
            .inflate(inflater, container, false)
            .apply { binding = this }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var listaContactos = emptyList<Usuario>()
        val database = AppDatabase.getDatabase(super.getContext()!!)

        database.usuarios().getAll().observe(this, Observer{
            listaContactos = it

            val adapter = ContactosAdapter(super.getContext()!!, listaContactos)

            binding.lista.adapter = adapter
        })

        binding.lista.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(super.getContext(), menu_solicitante::class.java)
            intent.putExtra("usuario", usr)
            intent.putExtra("usuarioSeleccionado", listaContactos[position].usuario)
            super.getActivity()!!.finish()
            startActivity(intent)
        }
    }
}