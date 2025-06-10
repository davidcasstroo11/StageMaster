package com.example.stagemaster

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.stagemaster.controlador.UsuarioController
import com.example.stagemaster.vistas.Hash
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class IntegracionTest {

    private val email = "usuarioprueba@gmail.com"
    private val clave = "1234"

    /**
     * Método que comprueba el acceso al sistema en caso de que se proporcione correctamente las credenciales
     */
    @Test
    fun loginCorrecto() {
        val context = ApplicationProvider.getApplicationContext<android.content.Context>()
        val controlador = UsuarioController(context)

        val usuarioExistente = controlador.selectUsuarios(email)
        if (usuarioExistente == null) {
            controlador.insertarUsuario(
                "Prueba", "Usuario", "usuarioprueba08", email, clave
            )
        }

        val usuario = controlador.selectUsuarios(email)
        assertEquals("El usuario se ha logueado correctamente.", true, Hash.md5(clave) == usuario.clave)
        controlador.cerrar()
    }

    /**
     * Método que comprueba el acceso al sistema en caso de que se proporcione un email desconocido
     */
    @Test
    fun loginIncorrecto() {
        val context = ApplicationProvider.getApplicationContext<android.content.Context>()
        val controlador = UsuarioController(context)

        val usuario = controlador.selectUsuarios("pruebausuario@gmail.com")
        assertNull("No se ha podido encontrar el email proporcionado. Registre un nuevo usuario.", usuario)
        controlador.cerrar()
    }
}