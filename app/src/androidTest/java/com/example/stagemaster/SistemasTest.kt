package com.example.stagemaster

import androidx.test.platform.app.InstrumentationRegistry
import com.example.stagemaster.controlador.EventoController
import com.example.stagemaster.modeloBBDD.Evento
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class SistemasTest {
    /**
     * Método que comprueba el número de eventos resultantes al proporcionar un nombre de artista cualquiera
     */
    @Test
    fun seleccionarEventosBuscarTest() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val contradorEvento = EventoController(context)
        val listaResultados = ArrayList<Evento>()
        assertEquals(listaResultados, contradorEvento.selectEventosNombres("Maluma"))
        if (listaResultados.size == 0) {
            println("${listaResultados.size} eventos mostrados")
        }
    }
}