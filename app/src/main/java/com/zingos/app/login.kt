package com.zingos.app

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.zingos.app.databinding.ActivityLoginBinding
import com.zingos.base_datos.AppDatabase

class login : AppCompatActivity() {
    lateinit var binding : ActivityLoginBinding

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = AppDatabase.getDatabase(this)

        binding.btnIngresar.setOnClickListener{
            val builder = AlertDialog.Builder(this)

            if (binding.etUsuarioLogin.text.isEmpty() ||
                    binding.etPasswordLogin.text.isEmpty()){
                // si esta vacio mostramos alerta
                with(builder)
                {
                    setTitle("Falta un campo")
                    setMessage("Rellene todos los campos e intentelo de nuevo")
                    setPositiveButton("OK", {DialogInterface, Int ->
                        run {
                            Toast.makeText(
                                this@login,
                                "Presiono si", Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
                    setIcon(R.drawable.baseline_error_24)
                    show()
                }
            }else{
                val usuarioIngresado = database.usuarios().get(binding.etUsuarioLogin.text.toString())
                try {
                    usuarioIngresado.observe(this, Observer {
                        if(it != null){
                            if(binding.etUsuarioLogin.text.toString() == it.usuario &&
                                binding.etPasswordLogin.text.toString() == it.password){
                                if(it.tipo == "Prestador" ){
                                    val intent = Intent(this@login, menu_prestador::class.java)
                                    intent.putExtra("usuario", it.usuario)
                                    startActivity(intent)
                                }else if(it.tipo == "Solicitante" ){
                                    val intent = Intent(this@login, menu_solicitante::class.java)
                                    intent.putExtra("usuario", it.usuario)
                                    startActivity(intent)
                                }
                                Toast.makeText(this@login,"Ingresando...",Toast.LENGTH_SHORT).show()
                            }else{
                                //Toast.makeText(this@login,"Contrasena incorrecta",Toast.LENGTH_LONG).show()
                                with(builder)
                                {
                                    setTitle("Error")
                                    setMessage("Contrasena incorrecta")
                                    setPositiveButton("Intentar de nuevo", {DialogInterface, Int ->
                                    })
                                    setIcon(R.drawable.baseline_error_24)
                                    show()
                                }
                            }
                        }else{
                            //Toast.makeText(this@login,"Usuario no registrado",Toast.LENGTH_LONG).show()
                            with(builder)
                            {
                                setTitle("Error")
                                setMessage("Usuario no registrado")
                                setPositiveButton("Intentar de nuevo", {DialogInterface, Int ->
                                })
                                setIcon(R.drawable.baseline_error_24)
                                show()
                            }
                        }
                    })
                }catch (e: Exception){
                    //Toast.makeText(this@login,"Usuario no registrado",Toast.LENGTH_LONG).show()
                    with(builder)
                    {
                        setTitle("Error")
                        setMessage("Usuario no registrado")
                        setPositiveButton("Intentar de nuevo", {DialogInterface, Int ->
                        })
                        setIcon(R.drawable.baseline_error_24)
                        show()
                    }
                }
            }
        }

        binding.btnRegistrarLogin.setOnClickListener {
            val intent = Intent(this@login, registrar_usuario::class.java)
            binding.etUsuarioLogin.setText("")
            binding.etPasswordLogin.setText("")
            startActivity(intent)
        }

    }
}