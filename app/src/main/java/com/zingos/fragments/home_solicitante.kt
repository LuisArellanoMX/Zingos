package com.zingos.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zingos.app.databinding.FragmentHomeSolicitanteBinding
import com.zingos.app.menu_solicitante

class home_solicitante : Fragment() {
    lateinit var usr :String
    lateinit var binding : FragmentHomeSolicitanteBinding

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
        return FragmentHomeSolicitanteBinding
            .inflate(inflater, container, false)
            .apply { binding = this }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.crServiciosSoli.setOnClickListener {
            val intent = Intent(super.getContext(), menu_solicitante::class.java)
            intent.putExtra("usuario",usr)
            intent.putExtra("categoria","todos")
            super.getActivity()!!.finish()
            startActivity(intent)
        }
        binding.crContactos.setOnClickListener {
            val intent = Intent(super.getContext(), menu_solicitante::class.java)
            intent.putExtra("usuario",usr)
            intent.putExtra("btnContactos","yes")
            super.getActivity()!!.finish()
            startActivity(intent)
        }
    }

}