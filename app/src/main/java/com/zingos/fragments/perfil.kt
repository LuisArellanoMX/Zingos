package com.zingos.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.zingos.app.databinding.FragmentPerfilBinding
import com.zingos.base_datos.AppDatabase

class perfil : Fragment() {
    lateinit var usr : String
    lateinit var binding : FragmentPerfilBinding

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
        return FragmentPerfilBinding
            .inflate(inflater, container, false)
            .apply { binding = this }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val database = AppDatabase.getDatabase(super.getContext()!!)
        val usuarioDetalles = database.usuarios().get(usr)
        usuarioDetalles.observe(this, Observer {
            binding.etNom.text = it.nombre
            binding.etCorr.text = it.correo
            binding.etTel.text = it.telefono
            binding.etTipo.text = it.tipo
            binding.etUsr.text = it.usuario
        })
    }
}