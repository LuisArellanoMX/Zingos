package com.zingos.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zingos.app.databinding.FragmentHomePrestadorBinding
import com.zingos.app.menu_prestador


class home_prestador : Fragment() {
    lateinit var usr :String
    lateinit var binding : FragmentHomePrestadorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            usr = it.getString("usuario").toString()
            //Toast.makeText(super.getContext(), "Hola $usr", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return FragmentHomePrestadorBinding
            .inflate(inflater, container, false)
            .apply { binding = this }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.crMisServicios.setOnClickListener {
            val intent = Intent(super.getContext(), menu_prestador::class.java)
            intent.putExtra("usuario",usr)
            intent.putExtra("mis_servicios",true)
            super.getActivity()!!.finish()
            startActivity(intent)
        }
        binding.crCrearServicios.setOnClickListener{
            val intent = Intent(super.getContext(), menu_prestador::class.java)
            intent.putExtra("usuario",usr)
            intent.putExtra("crear",true)
            super.getActivity()!!.finish()
            startActivity(intent)
        }
    }
}