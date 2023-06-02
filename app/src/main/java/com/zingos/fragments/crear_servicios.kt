package com.zingos.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.zingos.app.R
import com.zingos.app.databinding.FragmentCrearServiciosBinding
import com.zingos.app.menu_prestador
import com.zingos.base_datos.AppDatabase
import com.zingos.base_datos.Servicio
import kotlinx.android.synthetic.main.item_servicio.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class crear_servicios : Fragment() {
    lateinit var usr :String
    lateinit var servicio: Servicio
    lateinit var binding:FragmentCrearServiciosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            usr = it.getString("usuario").toString()
            if(it.getSerializable("servicio") != null){
                servicio = it.getSerializable("servicio") as Servicio
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return FragmentCrearServiciosBinding
            .inflate(inflater, container, false)
            .apply { binding = this }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var idServicio:Int? = null

        if (arguments!!.getSerializable("servicio") != null){
            binding.etNomServicio.setText(servicio.nombre_servicio)
            binding.etHorario.setText(servicio.horario)
            binding.etUbicacion.setText(servicio.ubicacion)
            binding.etDescripcion.setText(servicio.descripcion)

            when(servicio.categoria){
                "Tecnologia" -> binding.spCategoria.setSelection(1)
                "Asesorias" -> binding.spCategoria.setSelection(0)
                "Mantenimiento" -> binding.spCategoria.setSelection(2)
                "Automotriz" -> binding.spCategoria.setSelection(3)
                "Administracion y Logistica" -> binding.spCategoria.setSelection(4)
                "Salud" -> binding.spCategoria.setSelection(5)
            }

            idServicio = servicio.id
        }

        when(binding.spCategoria.selectedItem.toString()){
            "Tecnologia" -> binding.updateImage.setImageResource(R.drawable.cat_tecnologia)
            "Asesorias" -> binding.updateImage.setImageResource(R.drawable.cat_asesorias)
            "Mantenimiento" -> binding.updateImage.setImageResource(R.drawable.cat_mantenimiento)
            "Automotriz" -> binding.updateImage.setImageResource(R.drawable.cat_automotriz)
            "Administracion y Logistica" -> binding.updateImage.setImageResource(R.drawable.cat_administracion)
            "Salud" -> binding.updateImage.setImageResource(R.drawable.cat_salud)
        }

        if(binding.etNomServicio.text.isEmpty()){
            binding.updateImage.setImageResource(R.drawable.baseline_post_add_24)
        }

        val database = AppDatabase.getDatabase(super.getContext()!!)
        binding.btnCrear.setOnClickListener {

            if(!verificarCampos()){
                val nombre_servicio = binding.etNomServicio.text.toString()
                val horario = binding.etHorario.text.toString()
                val ubicacion = binding.etUbicacion.text.toString()
                val categoria = binding.spCategoria.selectedItem.toString()
                val descripcion = binding.etDescripcion.text.toString()
                val usuario_servicio = usr

                var servicioObj = Servicio(nombre_servicio, categoria,horario,ubicacion,descripcion,usuario_servicio)

                if (idServicio != null){
                    servicioObj.id = idServicio

                    CoroutineScope(Dispatchers.IO).launch {
                        database.servicios().update(servicioObj)
                        //Toast.makeText(this,"Registrado con exito!", Toast.LENGTH_SHORT).show()
                    }

                }else{
                    CoroutineScope(Dispatchers.IO).launch {
                        database.servicios().insertAll(servicioObj)
                        //Toast.makeText(this,"Registrado con exito!", Toast.LENGTH_SHORT).show()
                    }
                }

                val intent = Intent(super.getContext(), menu_prestador::class.java)
                intent.putExtra("usuario", usr)
                intent.putExtra("seCreo", true)
                super.getActivity()!!.finish()
                startActivity(intent)
            }else{
                // Campos en blanco
                val builder = AlertDialog.Builder(super.getContext()!!)
                with(builder) {
                    setTitle("Falta un campo")
                    setMessage("Rellene todos los campos e intentelo de nuevo")
                    setPositiveButton("OK", {DialogInterface, Int -> })
                    setIcon(R.drawable.baseline_error_24)
                    show()
                }
            }
        }//Fin evento
    }

    fun verificarCampos():Boolean{
        return binding.etNomServicio.text.isEmpty() ||
        binding.etHorario.text.isEmpty() ||
        binding.etUbicacion.text.isEmpty() ||
        binding.etDescripcion.text.isEmpty()
    }
}
