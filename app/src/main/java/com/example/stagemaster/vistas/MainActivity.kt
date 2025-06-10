package com.example.stagemaster.vistas

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.stagemaster.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var menuNavegacion: BottomNavigationView
    private lateinit var principalFragment: MenuPrincipalFragment
    private lateinit var buscarFragment: BuscarFragment
    private lateinit var misEventosFragment: MisEventosFragment
    private lateinit var configuracionFragment: ConfiguracionFragment

    private lateinit var nombreLogin: String
    private lateinit var apellidosLogin: String
    private lateinit var nombreUsuarioLogin: String
    private lateinit var emailLogin: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        menuNavegacion = findViewById(R.id.menuNavegacion)
        principalFragment = MenuPrincipalFragment()
        buscarFragment = BuscarFragment()
        misEventosFragment = MisEventosFragment()
        configuracionFragment = ConfiguracionFragment()

        nombreLogin = intent.getStringExtra("nombre").toString()
        apellidosLogin = intent.getStringExtra("apellidos").toString()
        nombreUsuarioLogin = intent.getStringExtra("usuarioLogueado").toString()
        emailLogin = intent.getStringExtra("email").toString()

        // Asignación de argumentos en los parametros recibidos por el intent
        val bundle = Bundle()
        bundle.putString("nombre",nombreLogin)
        bundle.putString("apellidos",apellidosLogin)
        bundle.putString("usuarioLogueado",nombreUsuarioLogin)
        bundle.putString("email",emailLogin)
        principalFragment.arguments = bundle
        buscarFragment.arguments = bundle
        misEventosFragment.arguments = bundle
        configuracionFragment.arguments = bundle

        // Se inicializa la aplicación con la sección del menú principal
        supportFragmentManager.beginTransaction().replace(R.id.container,principalFragment).commit()

        // Selector del menú de navegación inferior
        menuNavegacion.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.seccionPrincipal -> {
                    supportFragmentManager.beginTransaction().replace(R.id.container,principalFragment).commit()
                    true
                }
                R.id.seccionBuscar -> {
                    supportFragmentManager.beginTransaction().replace(R.id.container,buscarFragment).commit()
                    true
                }
                R.id.seccionMisEventos -> {
                    supportFragmentManager.beginTransaction().replace(R.id.container,misEventosFragment).commit()
                    true
                }
                R.id.seccionAjustes -> {
                    supportFragmentManager.beginTransaction().replace(R.id.container,configuracionFragment).commit()
                    true
                }
                else -> false
            }
        }
    }
}
