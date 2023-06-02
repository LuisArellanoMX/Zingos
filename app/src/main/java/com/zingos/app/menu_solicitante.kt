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


class menu_solicitante : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawerLayout: DrawerLayout
    lateinit var usr : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_solicitante)
        usr = intent.getStringExtra("usuario").toString()

        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        if (savedInstanceState == null) {
            if(intent.hasExtra("id")){
                val bundle = Bundle()
                bundle.putString("usuario",usr)
                bundle.putInt("id", intent.getIntExtra("id",0))
                bundle.putInt("tipo", 1)

                val frg = servicio_detalles()
                frg.arguments = bundle

                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, frg).commit()
                navigationView.setCheckedItem(R.id.nav_servicios)
            }else if(intent.hasExtra("categoria")){
                val bundle = Bundle()
                bundle.putString("usuario",usr)
                bundle.putString("categoria",intent.getStringExtra("categoria"))

                val frg = lista_servicios()
                frg.arguments = bundle

                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, frg).commit()
                navigationView.setCheckedItem(R.id.nav_servicios)
            }else if(intent.hasExtra("usuarioSeleccionado")){
                val bundle = Bundle()
                bundle.putString("usuario",usr)
                bundle.putString("usuarioSeleccionado",intent.getStringExtra("usuarioSeleccionado"))

                val frg = detalles_contacto()
                frg.arguments = bundle

                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, frg).commit()
                navigationView.setCheckedItem(R.id.nav_contactos)
            } else if(intent.hasExtra("btnContactos")){
                val bundle = Bundle()
                bundle.putString("usuario",usr)
                val frg = list_contactos()
                frg.arguments = bundle
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, frg).commit()

                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, frg).commit()
                navigationView.setCheckedItem(R.id.nav_contactos)
            }
            else{
                val bundle = Bundle()
                bundle.putString("usuario",usr)
                val frg = home_solicitante()
                frg.arguments = bundle

                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, frg).commit()
                navigationView.setCheckedItem(R.id.nav_home)
            }
        }


    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val bundle = Bundle()
        bundle.putString("usuario",usr)

        when (item.itemId) {
            R.id.nav_home -> {
                val frg = home_solicitante()
                frg.arguments = bundle
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, frg).commit()
            }
            R.id.nav_categorias -> {
                val frg = categorias()
                frg.arguments = bundle
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, frg).commit()
            }
            R.id.nav_servicios -> {
                val frg = lista_servicios()
                bundle.putString("categoria","todos")
                frg.arguments = bundle
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, frg).commit()
            }
            R.id.nav_contactos -> {
                val frg = list_contactos()
                frg.arguments = bundle
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, frg).commit()
            }
            R.id.nav_perfil -> {
                val frg = perfil()
                frg.arguments = bundle
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, frg).commit()
            }
            R.id.nav_logout -> {
                val builder = AlertDialog.Builder(this)
                with(builder)
                {
                    setTitle("Advertencia")
                    setMessage("¿Desea cerrar la sesion?")
                    setPositiveButton("Si", { DialogInterface, Int ->
                        Toast.makeText(this@menu_solicitante, "Cerrando sesion...", Toast.LENGTH_SHORT).show()
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