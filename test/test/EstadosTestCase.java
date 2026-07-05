package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
 
import org.junit.jupiter.api.Test;
 
import pedido.Borrador;
import pedido.Cancelado;
import pedido.Confirmado;
import pedido.EnPreparacion;
import pedido.Entregado;
import pedido.Enviado;
import pedido.TipoEstado;


public class EstadosTestCase {
	
    @Test
    void testBorradorExponeSusDatosCorrectamente() {
        Borrador estado = new Borrador();
 
        assertEquals(TipoEstado.BORRADOR, estado.getTipo());
        assertEquals("BORRADOR", estado.getNombre());
        assertTrue(estado.sePuedeModificarPedido());
        assertFalse(estado.debeNotificar()); 
    }
 
    @Test
    void testConfirmadoExponeSusDatosCorrectamente() {
        Confirmado estado = new Confirmado();
 
        assertEquals(TipoEstado.CONFIRMADO, estado.getTipo());
        assertEquals("CONFIRMADO", estado.getNombre());
        assertTrue(estado.debeNotificar());
        assertFalse(estado.sePuedeModificarPedido());
    }
 
    @Test
    void testEnPreparacionExponeSusDatosCorrectamente() {
        EnPreparacion estado = new EnPreparacion();
 
        assertEquals(TipoEstado.ENPREPARACION, estado.getTipo());
        assertEquals("ENPREPARACION", estado.getNombre());
        assertFalse(estado.sePuedeModificarPedido()); 
        assertFalse(estado.debeNotificar());     
    }
 
    @Test
    void testEnviadoExponeSusDatosCorrectamente() {
        Enviado estado = new Enviado();
 
        assertEquals(TipoEstado.ENVIADO, estado.getTipo());
        assertEquals("ENVIADO", estado.getNombre());
        assertTrue(estado.debeNotificar());
    }
 
    @Test
    void testEntregadoExponeSusDatosCorrectamente() {
        Entregado estado = new Entregado();
 
        assertEquals(TipoEstado.ENTREGADO, estado.getTipo());
        assertEquals("ENTREGADO", estado.getNombre());
        assertTrue(estado.debeNotificar());
    }
 
    @Test
    void testCanceladoExponeSusDatosCorrectamente() {
        Cancelado estado = new Cancelado();
 
        assertEquals(TipoEstado.CANCELADO, estado.getTipo());
        assertEquals("CANCELADO", estado.getNombre());
        assertFalse(estado.sePuedeModificarPedido()); 
        assertFalse(estado.debeNotificar());      
    }

	
}
