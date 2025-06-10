package com.example.stagemaster

import androidx.test.core.app.ApplicationProvider
import com.example.stagemaster.controlador.UsuarioController
import com.example.stagemaster.vistas.Hash
import junit.framework.Assert.assertEquals
import org.junit.Test

class UnitariaTest {
    /**
     * Test que comprueba si alguno de los campos se encuentran vacíos
     */
    @Test
    fun camposVacios() {
        val resultado = RestablecerContraValidador.validarInputs("","1230", "1230")
        assertEquals("Verifica que los campos no se encuentren vacíos.", resultado)
    }
    /**
     * Test que comprueba si las claves proporcionadas son iguales o no
     */
    @Test
    fun clavesNoCoinciden() {
        val resultado = RestablecerContraValidador.validarInputs("usuarioprueba@gmail.com","1230", "12310")
        assertEquals("Debes de introducir la contraseña igual en ambos campos.", resultado)
    }
}


object RestablecerContraValidador {
    /**
     * Método que actua como validador de el restablecimiento de la contraseña
     */
    fun validarInputs(email: String, clave: String, claveRepeat: String): String? {
        if (email.isEmpty() || clave.isEmpty() || claveRepeat.isEmpty()) {
            return "Verifica que los campos no se encuentren vacíos."
        }
        if (clave != claveRepeat) {
            return "Debes de introducir la contraseña igual en ambos campos."
        }
        return null
    }
}