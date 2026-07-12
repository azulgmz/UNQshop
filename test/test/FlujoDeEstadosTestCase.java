package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
 
import java.util.ArrayList;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
 
import pedido.ExceptionMsg;
import pedido.Pedido;
import pedido.TipoEstado;
import productos.Atributo;
import productos.Individual;
import sistemas.Catalogo;
import sistemas.Sucursal;
import ubicacionGeografica.Direccion;

public class FlujoDeEstadosTestCase {
    private Catalogo catalogoUNQ;
    private Sucursal sucusalUNQ;
    private ArrayList<Atributo> atributosDummy;
    private Pedido pedido;
    private Individual monitor;
    private ArrayList<Sucursal> sucursales;
 
    @BeforeEach
    void setUp() {
    	catalogoUNQ    = new Catalogo();
    	sucursales     = new ArrayList<Sucursal>();
	    sucusalUNQ     = new Sucursal(28062026, catalogoUNQ, 100000f, new Direccion("Roque Sáenz Peña 124", -34.76493d, -58.278418d), sucursales);
        atributosDummy = new ArrayList<>();
 
        pedido = sucusalUNQ.crearPedido("juan@gmail.com", new Direccion("9 de Julio 217", -34.712445d, -58.284493d));
 
        catalogoUNQ.registrarIndividual("Monitor", "Snapdragon", "Perifericos", atributosDummy, 8900f, 100, 2000f); // SKU = 1
        monitor = (Individual) catalogoUNQ.buscarProducto(1);
        pedido.agregarProducto(monitor);
    }
 
    @Test
    void testElPedidoAvanzaPorTodosLosEstadosHastaEntregado() {
        pedido.avanzarEstado(); 
        assertEquals(TipoEstado.CONFIRMADO, pedido.getEstado());
        assertEquals(100, catalogoUNQ.cantidadDe(1)); 
 
        pedido.avanzarEstado(); 
        assertEquals(TipoEstado.ENPREPARACION, pedido.getEstado());
        assertEquals(99, catalogoUNQ.cantidadDe(1));
 
        pedido.avanzarEstado(); 
        assertEquals(TipoEstado.ENVIADO, pedido.getEstado());
 
        pedido.avanzarEstado(); 
        assertEquals(TipoEstado.ENTREGADO, pedido.getEstado());
 
        ExceptionMsg error = assertThrows(ExceptionMsg.class, pedido::avanzarEstado);
        assertEquals("Pedido ya entregado", error.getMessage());
    }
 
    @Test
    void testCancelarUnPedidoEnPreparacionDevuelveElStock() {
        pedido.avanzarEstado(); 
        pedido.avanzarEstado();
 
        pedido.cancelarPedido();
 
        assertEquals(TipoEstado.CANCELADO, pedido.getEstado());
        assertFalse(sucusalUNQ.tienePedidoActivo(pedido));
        assertEquals(100, catalogoUNQ.cantidadDe(1));
    }
 
    @Test
    void testCancelarUnPedidoEnviadoDevuelveElStock() {
        pedido.avanzarEstado(); // 
        pedido.avanzarEstado(); //
 
        pedido.cancelarPedido();
 
        assertEquals(TipoEstado.CANCELADO, pedido.getEstado());
        assertEquals(100, catalogoUNQ.cantidadDe(1));
    }
 
    @Test
    void testNoSePuedeCancelarUnPedidoEntregado() {
        pedido.avanzarEstado();
        pedido.avanzarEstado();
        pedido.avanzarEstado();
        pedido.avanzarEstado(); 
 
        ExceptionMsg error = assertThrows(ExceptionMsg.class, pedido::cancelarPedido);
        assertEquals("No se puede cancelar pedido entregado", error.getMessage());
    }
 
    @Test
    void testNoSePuedeCancelarUnPedidoYaCancelado() {
        pedido.cancelarPedido();
 
        ExceptionMsg error = assertThrows(ExceptionMsg.class, pedido::cancelarPedido);
        assertEquals("Pedido ya cancelado", error.getMessage());
    }
 
    @Test
    void testNoSePuedeAvanzarUnPedidoCancelado() {
        pedido.cancelarPedido();
 
        ExceptionMsg error = assertThrows(ExceptionMsg.class, pedido::avanzarEstado);
        assertEquals("Pedido cancelado", error.getMessage());
    }
 
    @Test
    void testNoSePuedeAgregarUnProductoAUnPedidoQueYaNoEstaEnBorrador() {
    	catalogoUNQ.registrarIndividual("Mouse", "Snapdragon", "Perifericos", atributosDummy, 2000f, 50, 25f); // SKU = 2
        pedido.avanzarEstado(); 
 
        pedido.agregarProducto(catalogoUNQ.buscarProducto(2));
 
        assertFalse(pedido.agregoA(2)); 
    }
 
    @Test
    void testNoSePuedeEliminarUnProductoDeUnPedidoQueYaNoEstaEnBorrador() {
        pedido.avanzarEstado();
 
        pedido.eliminarProducto(monitor);
 
        assertTrue(pedido.agregoA(1)); 
    }

}
