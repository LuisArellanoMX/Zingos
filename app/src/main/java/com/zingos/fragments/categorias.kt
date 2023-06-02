package com.zingos.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.zingos.app.databinding.FragmentCategoriasBinding
import com.zingos.app.menu_solicitante


class categorias : Fragment() {
    lateinit var usr :String
    lateinit var binding : FragmentCategoriasBinding

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
        return FragmentCategoriasBinding
            .inflate(inflater, container,false)
            .apply{binding = this}
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val intent = Intent(super.getContext(), menu_solicitante::class.java)
        intent.putExtra("usuario", usr)

        binding.crTecnologia.setOnClickListener{
            Toast.makeText(super.getContext(), "Tecnologia", Toast.LENGTH_SHORT).show()
            intent.putExtra("categoria","Tecnologia")
            super.getActivity()!!.finish()
            startActivity(intent)
        }
        binding.crAdministracion.setOnClickListener {
            intent.putExtra("categoria","Administracion y Logistica")
            super.getActivity()!!.finish()
            startActivity(intent)
        }
        binding.crAsesorias.setOnClickListener {
            intent.putExtra("categoria","Asesorias")
            super.getActivity()!!.finish()
            startActivity(intent)
        }
        binding.crSalud.setOnClickListener {
            intent.putExtra("categoria","Salud")
            super.getActivity()!!.finish()
            startActivity(intent)
        }
        binding.crMantenimiento.setOnClickListener {
            intent.putExtra("categoria","Mantenimiento")
            super.getActivity()!!.finish()
            startActivity(intent)
        }
        binding.crAutomotriz.setOnClickListener {
            intent.putExtra("categoria","Automotriz")
            super.getActivity()!!.finish()
            startActivity(intent)
        }
    }
}