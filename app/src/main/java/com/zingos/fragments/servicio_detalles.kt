package com.zingos.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.zingos.app.R
import com.zingos.app.databinding.FragmentServicioDetallesBinding
import com.zingos.app.menu_prestador
import com.zingos.base_datos.AppDatabase
import com.zingos.base_datos.Servicio
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class servicio_detalles : Fragment() {
    lateinit var usr :String
    var idServicio:Int = 0
    lateinit var binding : FragmentServicioDetallesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            usr = it.getString("usuario").toString()
            idServicio = it.getInt("id")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return FragmentServicioDetallesBinding
            .inflate(inflater, container, false)
            .apply { binding = this }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val database = AppDatabase.getDatabase(super.getContext()!!)
        val servicioDetalles = database.servicios().get(idServicio)
        lateinit var servicioObj : Servicio
        var usuarioSolicitado = "a"


        servicioDetalles.observe(this, Observer{
            if(it != null){
                servicioObj = it
                binding.etNomServicio.text = it.nombre_servicio
                binding.etDescripcion.text = it.descripcion
                binding.etHorario.text = it.horario
                binding.etUbicacion.text = it.ubicacion
                binding.spCategoria.text = it.categoria
                usuarioSolicitado = it.usuario

                when(it.categoria){
                    "Tecnologia" -> binding.imageView.setImageResource(R.drawable.cat_tecnologia)
                    "Asesorias" -> binding.imageView.setImageResource(R.drawable.cat_asesorias)
                    "Mantenimiento" -> binding.imageView.setImageResource(R.drawable.cat_mantenimiento)
                    "Automotriz" -> binding.imageView.setImageResource(R.drawable.cat_automotriz)
                    "Administracion y Logistica" -> binding.imageView.setImageResource(R.drawable.cat_administracion)
                    "Salud" -> binding.imageView.setImageResource(R.drawable.cat_salud)
                }
            }

            // Obteniendo datos del usuario
            //servicioDetalles.removeObservers(this)
            var usuarioObj = database.usuarios().get(usuarioSolicitado)

            usuarioObj.observe(this, Observer{
                binding.etUsuario.text = it.nombre.toString()
                binding.etTelefono.text = it.telefono.toString()
                binding.etCorreo.text = it.correo.toString()
            })

        })



        if(arguments!!.getInt("tipo",0) > 0){
            binding.lyBotones.visibility = View.INVISIBLE
        }


        binding.btnEliminar.setOnClickListener {
            val builder = AlertDialog.Builder(super.getContext()!!)
            with(builder)
            {
                setTitle("Advertencia")
                setMessage("¿Seguro que desea eliminar el servicio?")
                setPositiveButton("Si", { DialogInterface, Int ->
                    // Se remueve el servicio
                    servicioDetalles.removeObservers(this@servicio_detalles)
                    CoroutineScope(Dispatchers.IO).launch {
                        database.servicios().delete(servicioObj)
                    }
                    val intent = Intent(super.getContext(), menu_prestador::class.java)
                    intent.putExtra("usuario", usr)
                    super.getActivity()!!.finish()
                    startActivity(intent)
                })
                setNegativeButton("No", { DialogInterface, Int ->

                })
                setIcon(R.drawable.baseline_question_mark_24)
                show()
            }
        }

        binding.btnEditar.setOnClickListener {
            val intent = Intent(super.getContext(), menu_prestador::class.java)
            intent.putExtra("usuario", usr)
            intent.putExtra("servicio",servicioObj)
            super.getActivity()!!.finish()
            startActivity(intent)
        }
    }
}