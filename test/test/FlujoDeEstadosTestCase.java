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
import productos.Individuales;
import sistemas.Catalogo;
import sistemas.Sucursal;

public class FlujoDeEstadosTestCase {
    private Catalogo catalogoCorrientes;
    private Sucursal sucursalCorrientes;
    private ArrayList<Atributo> atributosDummy;
    private Pedido pedido;
    private Individuales monitor;
 
    @BeforeEach
    void setUp() {
        catalogoCorrientes = new Catalogo();
        sucursalCorrientes = new Sucursal(28062026, catalogoCorrientes, 100000f, "Avenida Corrientes 1661");
        atributosDummy = new ArrayList<>();
 
        pedido = sucursalCorrientes.crearPedido("juan@gmail.com", "Juan Domingo Peron 133");
 
        catalogoCorrientes.registrarIndividual("Monitor", "Snapdragon", "Perifericos", atributosDummy, 8900f, 100); // SKU = 1
        monitor = (Individuales) catalogoCorrientes.buscarProducto(1);
        pedido.agregarProducto(monitor);
    }
 
    @Test
    void testElPedidoAvanzaPorTodosLosEstadosHastaEntregado() {
        pedido.avanzarEstado(); 
        assertEquals(TipoEstado.CONFIRMADO, pedido.getEstado());
        assertEquals(100, catalogoCorrientes.cantidadDe(1)); 
 
        pedido.avanzarEstado(); 
        assertEquals(TipoEstado.ENPREPARACION, pedido.getEstado());
        assertEquals(99, catalogoCorrientes.cantidadDe(1));
 
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
        assertFalse(sucursalCorrientes.tienePedidoActivo(pedido));
        assertEquals(100, catalogoCorrientes.cantidadDe(1));
    }
 
    @Test
    void testCancelarUnPedidoEnviadoDevuelveElStock() {
        pedido.avanzarEstado(); // 
        pedido.avanzarEstado(); //
 
        pedido.cancelarPedido();
 
        assertEquals(TipoEstado.CANCELADO, pedido.getEstado());
        assertEquals(100, catalogoCorrientes.cantidadDe(1));
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
        catalogoCorrientes.registrarIndividual("Mouse", "Snapdragon", "Perifericos", atributosDummy, 2000f, 50); // SKU = 2
        pedido.avanzarEstado(); 
 
        pedido.agregarProducto(catalogoCorrientes.buscarProducto(2));
 
        assertFalse(pedido.agregoA(2)); 
    }
 
    @Test
    void testNoSePuedeEliminarUnProductoDeUnPedidoQueYaNoEstaEnBorrador() {
        pedido.avanzarEstado();
 
        pedido.eliminarProducto(monitor);
 
        assertTrue(pedido.agregoA(1)); 
    }

}
