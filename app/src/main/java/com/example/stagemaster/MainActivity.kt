package com.example.stagemaster

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.stagemaster.ui.theme.StageMasterTheme
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {
    private lateinit var menuNavegacion: BottomNavigationView
    private lateinit var configuracionFragment: ConfiguracionFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        menuNavegacion = findViewById(R.id.menuNavegacion)
        configuracionFragment = ConfiguracionFragment()


        menuNavegacion.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.seccionConfiguracion -> {
                    supportFragmentManager.beginTransaction().replace(R.id.container,configuracionFragment).commit()
                    true
                }

                else -> false
            }
        }
    }
}
