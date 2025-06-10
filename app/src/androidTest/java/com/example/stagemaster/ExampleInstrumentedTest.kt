package com.example.stagemaster

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.stagemaster.controlador.UsuarioController
import com.example.stagemaster.modeloBBDD.Usuario

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun actualizarNombreUsuario() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val controladorUsuario = UsuarioController(context)
        val usuarioExtraido = controladorUsuario.selectUsuariosNombreUsuarios("david0411")
        if (usuarioExtraido != null) {
            assertEquals(1,
                controladorUsuario.actualizarNombreUsuario("david0411", "david12"))
        }
    }
}