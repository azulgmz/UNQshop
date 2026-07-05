package test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import envios.RetiroEnSucursal;
import metodosDePago.BilleteraVirtual;
import pedido.Pedido;
import pedido.TipoEstado;
import productos.Atributo;
import productos.Individuales;

import java.util.ArrayList;

import sistemas.Catalogo;
import sistemas.Sucursal;

class pedidoTestCase {

	private int                 CUIT;
	private Catalogo            catalogoCorrientes;
	private float               dineroDisponible;
	private String              direccion;
	private Sucursal            sucursalCorrientes;
	private ArrayList<Atributo> atributosDummy;
	private Pedido              pedido;
	
	@BeforeEach
	public void setUp() {
		catalogoCorrientes = new Catalogo();
	    sucursalCorrientes = new Sucursal(28062026, catalogoCorrientes, 100000f, "Avenida Corrientes 1661");
	    atributosDummy     = new ArrayList<>();
	    
	    pedido = sucursalCorrientes.crearPedido("juan@gmail.com", "Juan Domingo Peron 133");
	    
	    catalogoCorrientes.registrarIndividual("Monitor", "Snapdragon", "Perifericos", atributosDummy, 8900f, 100); // SKU = 1
	    catalogoCorrientes.registrarIndividual("CPU", "Snapdragon", "Hardwar", atributosDummy, 10000f, 100);        // SKU = 2
	    
	}             
	
	@Test
	void testUnPedidoPuedeAgregarUnProductoSiEstaEnEstadoBorrador() {

		pedido.agregarProducto(catalogoCorrientes.buscarProducto(1)); // Se agrega 'monitor' al pedido
		
		assertTrue(pedido.agregoA(1));              // Se verifica que se agrego un Monitor SnapDragon al pedido
		assertEquals(pedido.getEstado(), TipoEstado.BORRADOR);
	}
	
	@Test
	void testUnPedidoNoPuedeAgregarUnProductoQueTenga0Stock() {
		
		catalogoCorrientes.registrarIndividual("Mouse", "Snapdragon", "Perifericos", atributosDummy, 2000f, 0); // SKU = 3
		
		IllegalArgumentException error = assertThrows(IllegalArgumentException.class,() -> pedido.agregarProducto(catalogoCorrientes.buscarProducto(3)));
																												
		assertEquals("No hay stock de " + catalogoCorrientes.buscarProducto(3).getNombre() + ".", error.getMessage());
		
		assertEquals(0, catalogoCorrientes.cantidadDe(3)); // Se verifica que el stock de 'Mouse' queda intacto
		assertFalse(pedido.agregoA(3));                      // Se verifica que no se agrego el 'Mouse' al pedido
	}
	
	@Test
	void testIUnPedidoPuedeEliminarUnProductoSiEstaEnEstadoBorrador() {
		
		Individuales monitor = (Individuales) catalogoCorrientes.buscarProducto(1);
		
		pedido.agregarProducto(monitor);
		
		pedido.eliminarProducto(monitor); // Se elimina 'monitor' del pedido
		assertFalse(pedido.agregoA(1));   // Se verifica que se elimino un Monitor SnapDragon del pedido
		assertEquals(pedido.getEstado(), TipoEstado.BORRADOR);
	}
	
	@Test
	void testIUnPedidoCuandoSeConfirmaBajaElStockDelCatalogo() {
		
		Individuales monitor = (Individuales) catalogoCorrientes.buscarProducto(1);
		BilleteraVirtual billeteraDummy = mock(BilleteraVirtual.class);
		RetiroEnSucursal retiroDummy = mock(RetiroEnSucursal.class);
		
		pedido.agregarProducto(monitor);
		pedido.agregarProducto(monitor);
		pedido.agregarProducto(monitor);
		pedido.agregarProducto(monitor);
		
		pedido.confirmarPedido(billeteraDummy, retiroDummy);
		assertEquals(96, catalogoCorrientes.cantidadDe(1)); // Se verifica que el stock de 'Monitor' baja
		assertEquals(pedido.getEstado(), TipoEstado.CONFIRMADO);        // Se verifica que cambio el estado del pedido
	}
	
	@Test
	void testIUnPedidoEnEstadoBorradorSePuedeCancelar() {
		
		assertEquals(pedido.getEstado(), TipoEstado.BORRADOR);                 // Se verifica que esta como Borrador
		pedido.cancelarPedido();
		assertEquals(pedido.getEstado(), TipoEstado.CANCELADO);                // Se verifica que cambio el estado del pedido
		assertFalse(sucursalCorrientes.tienePedidoActivo(pedido)); // Se verifica que se elimino de la sucursal el pedido
	}
	
	@Test
	void testIUnPedidoEnEstadoConfirmadoSePuedeCancelarYDevuelveElStock() {
		Individuales monitor = (Individuales) catalogoCorrientes.buscarProducto(1);
		BilleteraVirtual billeteraDummy = mock(BilleteraVirtual.class);
		RetiroEnSucursal retiroDummy = mock(RetiroEnSucursal.class);
		
		pedido.agregarProducto(monitor);
		pedido.agregarProducto(monitor);
		pedido.agregarProducto(monitor);
		pedido.agregarProducto(monitor);
		
		pedido.confirmarPedido(billeteraDummy, retiroDummy);
		// El stock de 'Monitor' en el catalogo pasa de 100 -> 96
		
		pedido.cancelarPedido();
		
		assertEquals(pedido.getEstado(), TipoEstado.CANCELADO);                // Se verifica que cambio el estado del pedido
		assertFalse(sucursalCorrientes.tienePedidoActivo(pedido)); // Se verifica que se elimino de la sucursal el pedido
		assertEquals(100, catalogoCorrientes.cantidadDe(1));       // Se verifica que el stock de 'Monitor' vuelve a 100
	}
		

}
