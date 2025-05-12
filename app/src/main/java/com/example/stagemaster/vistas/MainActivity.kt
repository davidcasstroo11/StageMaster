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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        menuNavegacion = findViewById(R.id.menuNavegacion)
        principalFragment = MenuPrincipalFragment()
        buscarFragment = BuscarFragment()
        misEventosFragment = MisEventosFragment()
        configuracionFragment = ConfiguracionFragment()

        supportFragmentManager.beginTransaction().replace(R.id.container,principalFragment).commit()

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
                R.id.seccionConfiguracion -> {
                    supportFragmentManager.beginTransaction().replace(R.id.container,configuracionFragment).commit()
                    true
                }
                else -> false
            }
        }
    }
}
