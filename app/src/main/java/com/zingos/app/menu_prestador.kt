package com.zingos.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.zingos.fragments.*

class menu_prestador : AppCompatActivity(),  NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawerLayout: DrawerLayout
    lateinit var usr : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_prestador)

        usr = intent.getStringExtra("usuario").toString()
        //Toast.makeText(this," menu de $usr",Toast.LENGTH_SHORT).show()

        if(intent.getBooleanExtra("seCreo",false)){
            Toast.makeText(this,"Servicio guardado con exito!",Toast.LENGTH_SHORT).show()
        }

        if(intent.getBooleanExtra("seSelecciono",false)){
            val bundle = Bundle()
            bundle.putString("usuario",usr)
            bundle.putInt("id",intent.getIntExtra("id",0))
            val frg = servicio_detalles()
            frg.arguments = bundle

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_p, frg).commit()
        }

        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout_p)
        val toolbar = findViewById<Toolbar>(R.id.toolbar_p)
        setSupportActionBar(toolbar)
        val navigationView = findViewById<NavigationView>(R.id.nav_view_p)
        navigationView.setNavigationItemSelectedListener(this)
        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        if (savedInstanceState == null) {
            //Toast.makeText(this,intent.getBooleanExtra("seSelecciono",false).toString(),Toast.LENGTH_SHORT).show()
            if(intent.getBooleanExtra("seSelecciono",false)){
                val bundle = Bundle()
                bundle.putString("usuario",usr)
                bundle.putInt("id",intent.getIntExtra("id",0))
                val frg = servicio_detalles()
                frg.arguments = bundle

                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_p, frg).commit()
                navigationView.setCheckedItem(R.id.nav_mis_servicios)
            } else if(intent.hasExtra("servicio")){
                val bundle = Bundle()
                bundle.putString("usuario",usr)
                bundle.putSerializable("servicio",intent.getSerializableExtra("servicio"))
                val frg = crear_servicios()
                frg.arguments = bundle

                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_p, frg).commit()
                navigationView.setCheckedItem(R.id.nav_crear_servicio)
            }else if (intent.hasExtra("mis_servicios")){
                val bundle = Bundle()
                bundle.putString("usuario", usr)
                val frg = mis_servicios()
                frg.arguments = bundle

                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_p, frg).commit()
                navigationView.setCheckedItem(R.id.nav_mis_servicios)
            }else if(intent.hasExtra("crear")){
                val bundle = Bundle()
                bundle.putString("usuario", usr)
                val frg = crear_servicios()
                frg.arguments = bundle

                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_p, frg).commit()
                navigationView.setCheckedItem(R.id.nav_crear_servicio)
            }
            else{
                val bundle = Bundle()
                bundle.putString("usuario",usr)
                val frg = home_prestador()
                frg.arguments = bundle

                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_p, frg).commit()
                navigationView.setCheckedItem(R.id.nav_home_p)
            }

        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val bundle = Bundle()
        bundle.putString("usuario", usr)

        when (item.itemId) {
            R.id.nav_home_p -> {
                val frg = home_prestador()
                frg.arguments = bundle
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_p, frg).commit()
            }
            R.id.nav_mis_servicios -> {
                val frg = mis_servicios()
                frg.arguments = bundle

                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_p, frg).commit()
            }
            R.id.nav_crear_servicio -> {
                val frg = crear_servicios()
                frg.arguments = bundle

                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_p, frg).commit()
            }
            R.id.nav_perfil_p -> {
                val frg = perfil()
                frg.arguments = bundle

                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_p, frg).commit()
            }
            R.id.nav_logout_p -> {
                val builder = AlertDialog.Builder(this)
                with(builder)
                {
                    setTitle("Advertencia")
                    setMessage("¿Desea cerrar la sesion?")
                    setPositiveButton("Si", { DialogInterface, Int ->
                        Toast.makeText(this@menu_prestador, "Cerrando sesion...", Toast.LENGTH_SHORT).show()
                        onBackPressedDispatcher.onBackPressed()
                    })
                    setNegativeButton("No", { DialogInterface, Int ->

                    })
                    setIcon(R.drawable.baseline_question_mark_24)
                    show()
                }

            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            //onBackPressedDispatcher.onBackPressed()
        }
    }
}