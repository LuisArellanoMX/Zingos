package com.zingos.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.zingos.app.databinding.ActivityRegistrarUsuarioBinding
import com.zingos.base_datos.AppDatabase
import com.zingos.base_datos.Usuario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class registrar_usuario : AppCompatActivity() {
    lateinit var binding : ActivityRegistrarUsuarioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrarUsuarioBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_registrar_usuario)
        setContentView(binding.root)

        val database = AppDatabase.getDatabase(this)
        binding.btnRegistrar.setOnClickListener {

            if(!verificarCampos()){
                val nombre = binding.etNombre.text.toString()
                val usuario = binding.etUsuario.text.toString()
                val password = binding.etPassword.text.toString()
                val tipo = binding.etTipo.selectedItem.toString()
                val correo = binding.etCorreoR.text.toString()
                val telefono = binding.etTelefono.text.toString()

                val usuarioObj = Usuario(usuario,nombre,password,tipo,correo,telefono)

                var toast = Toast.makeText(this@registrar_usuario,"Usuario ya registrado :(", Toast.LENGTH_LONG)
                CoroutineScope(Dispatchers.IO).launch {
                    try{
                        database.usuarios().insertAll(usuarioObj)
                        this@registrar_usuario.finish()
                    } catch(e: Exception){
                        toast.show()
                    }
                }
            }else {
                // Algun campo esta vacio
                //Toast.makeText(this,"Rellene todos los campos :(", Toast.LENGTH_SHORT).show()
                val builder = AlertDialog.Builder(this)
                with(builder) {
                    setTitle("Falta un campo")
                    setMessage("Rellene todos los campos e intentelo de nuevo")
                    setPositiveButton("OK", { DialogInterface, Int -> })
                    setIcon(R.drawable.baseline_error_24)
                    show()
                }
            }
        }

    }

    fun verificarCampos() : Boolean{
        return binding.etUsuario.text.toString().isEmpty()
                || binding.etNombre.text.toString().isEmpty()
                || binding.etPassword.text.toString().isEmpty()
                || binding.etCorreoR.text.toString().isEmpty()
                || binding.etTelefono.text.toString().isEmpty()
    }
}