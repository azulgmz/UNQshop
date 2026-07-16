package test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import envios.RetiroEnSucursal;
import envios.TipoEnvio;
import metodosDePago.BilleteraVirtual;
import pedido.Enviado;
import pedido.Pedido;
import pedido.TipoEstado;
import productos.Atributo;
import productos.Individual;
import productos.SistemaDeProductos;

import java.util.ArrayList;

import sistemas.Catalogo;
import sistemas.Sucursal;
import ubicacionGeografica.Direccion;

class PedidoTestCase {

	private SistemaDeProductos sistemaDeProductos;
	private Catalogo            catalogoUNQ;
	private Sucursal            sucursalUNQ;
	private ArrayList<Atributo> atributosDummy;
	private Pedido              pedido;
	private ArrayList<Sucursal> sucursales;
	
	@BeforeEach
	public void setUp() {
		sistemaDeProductos = new SistemaDeProductos();
		
		catalogoUNQ    = new Catalogo();
		sucursales      = new ArrayList<Sucursal>();
	    sucursalUNQ    = new Sucursal(28062026, catalogoUNQ, 100000f, new Direccion("Roque Sáenz Peña 124", -34.76493d, -58.278418d), sucursales);
	    atributosDummy = new ArrayList<>();
	    
	    sistemaDeProductos.agregarSucursal(sucursalUNQ);
	    
	    pedido = sucursalUNQ.crearPedido("juan@gmail.com", new Direccion("9 de Julio 217", -34.712445d, -58.284493d));
	    
	    sistemaDeProductos.registrarIndividual("Monitor", "Snapdragon", "Perifericos", atributosDummy, 8900f, 100, 2000f); // SKU = 1
	    sistemaDeProductos.registrarIndividual("CPU", "Snapdragon", "Hardwar", atributosDummy, 10000f, 100, 6840f);        // SKU = 2
	    
	}             
	
	@Test
	void testIUnPedidoRecienCreadoNoTieneDatosDeEnvioBilleteraNiProductosYEstaComoBorrador() {
		
		assertEquals(pedido.getEnvio(), TipoEnvio.SINENVIODEFINIDO);
		assertTrue(pedido.getMetodoDePago().esSinDefinir());
		assertEquals(pedido.getEstado(), TipoEstado.BORRADOR);
	}
	
	
	@Test
	void testUnPedidoPuedeAgregarUnProductoSiEstaEnEstadoBorrador() {

		pedido.agregarProducto(catalogoUNQ.buscarProducto(1)); // Se agrega 'monitor' al pedido
		
		assertTrue(pedido.agregoA(1));              // Se verifica que se agrego un Monitor SnapDragon al pedido
		assertEquals(pedido.getEstado(), TipoEstado.BORRADOR);
	}
	
	@Test
	void testUnPedidoNoPuedeAgregarUnProductoQueTenga0Stock() {
		
		sistemaDeProductos.registrarIndividual("Mouse", "Snapdragon", "Perifericos", atributosDummy, 2000f, 0, 20f); // SKU = 3
		
		IllegalArgumentException error = assertThrows(IllegalArgumentException.class,() -> pedido.agregarProducto(catalogoUNQ.buscarProducto(3)));
																												
		assertEquals("No hay stock de " + catalogoUNQ.buscarProducto(3).getNombre() + ".", error.getMessage());
		
		assertEquals(0, catalogoUNQ.cantidadDe(3)); // Se verifica que el stock de 'Mouse' queda intacto
		assertFalse(pedido.agregoA(3));                      // Se verifica que no se agrego el 'Mouse' al pedido
	}
	
	@Test
	void testIUnPedidoPuedeEliminarUnProductoSiEstaEnEstadoBorrador() {
		
		Individual monitor = (Individual) catalogoUNQ.buscarProducto(1);
		
		pedido.agregarProducto(monitor);
		
		pedido.eliminarProducto(monitor); // Se elimina 'monitor' del pedido
		assertFalse(pedido.agregoA(1));   // Se verifica que se elimino un Monitor SnapDragon del pedido
		assertEquals(pedido.getEstado(), TipoEstado.BORRADOR);
	}
	
	@Test
	void testIUnPedidoCuandoSeConfirmaBajaElStockDelCatalogo() {
		
		Individual monitor = (Individual) catalogoUNQ.buscarProducto(1);
		BilleteraVirtual billeteraDummy = mock(BilleteraVirtual.class);
		RetiroEnSucursal retiroDummy = mock(RetiroEnSucursal.class);
		
		pedido.agregarProducto(monitor);
		pedido.agregarProducto(monitor);
		pedido.agregarProducto(monitor);
		pedido.agregarProducto(monitor);
		
		pedido.confirmarPedido(billeteraDummy, retiroDummy);
		assertEquals(96, catalogoUNQ.cantidadDe(1)); // Se verifica que el stock de 'Monitor' baja
		assertEquals(pedido.getEstado(), TipoEstado.CONFIRMADO);        // Se verifica que cambio el estado del pedido
	}
	
	@Test
	void testIUnPedidoEnEstadoBorradorSePuedeCancelar() {
		
		assertEquals(pedido.getEstado(), TipoEstado.BORRADOR);                 // Se verifica que esta como Borrador
		pedido.cancelarPedido();
		assertEquals(pedido.getEstado(), TipoEstado.CANCELADO);                // Se verifica que cambio el estado del pedido
	}
	
	@Test
	void testIUnPedidoEnEstadoConfirmadoSePuedeCancelarYDevuelveElStock() {
		Individual monitor = (Individual) catalogoUNQ.buscarProducto(1);
		BilleteraVirtual billeteraDummy = mock(BilleteraVirtual.class);
		RetiroEnSucursal retiroDummy = mock(RetiroEnSucursal.class);
		
		pedido.agregarProducto(monitor);
		pedido.agregarProducto(monitor);
		pedido.agregarProducto(monitor);
		pedido.agregarProducto(monitor);
		
		pedido.confirmarPedido(billeteraDummy, retiroDummy);
		// El stock de 'Monitor' en el catalogo pasa de 100 -> 96
		
		pedido.cancelarPedido();
		
		assertEquals(pedido.getEstado(), TipoEstado.CANCELADO); // Se verifica que cambio el estado del pedido
		assertEquals(100, catalogoUNQ.cantidadDe(1));           // Se verifica que el stock de 'Monitor' vuelve a 100
	}
	
	@Test
	void testIUnPedidoSabeSuPesoTotal() {
		Individual monitor = (Individual) catalogoUNQ.buscarProducto(1);
		
		pedido.agregarProducto(monitor);
		pedido.agregarProducto(monitor);
		
		assertEquals(4000, pedido.pesoTotal()); // Se suma el pedo de los dos monitores
	}
	
	@Test
	void testIUnPedidoEnEstadoEnviadoCuandoSeCancelaGeneraUnaNotaDeCreditoQueLaGuardaLaSucursal() {
		Individual monitor = (Individual) catalogoUNQ.buscarProducto(1);
		Individual CPU     = (Individual) catalogoUNQ.buscarProducto(2);
		
		pedido.agregarProducto(monitor);
		pedido.agregarProducto(CPU);
		
		pedido.cambiarEstado(new Enviado());
		
		pedido.cancelarPedido();
		
		String detallesNotaDeCredito = "NOTA DE CREDITO DE 28062026   NUMERO: 1." + "\n"
				+ "Cliente: juan@gmail.com." + "\n"
				+ "Fecha: 2026-07-16." + "\n"
				+ "Detalles de montos." + "\n"
				+ "NOTA DE CREDITO DE 28062026   NUMERO: 1." + "\n"
				+ "Cliente: juan@gmail.com." + "\n"
				+ "Fecha: 2026-07-16." + "\n"
				+ "Monitor: 8900.0." + "\n"
				+ "CPU: 10000.0." + "\n"
				+ "Valor total: 18900.0." + "\n";
		
		assertEquals(1, sucursalUNQ.cantidadDeNotasDeCreditos());
		assertTrue(sucursalUNQ.tieneNotaDeCreditoNro(1));
		assertEquals(detallesNotaDeCredito, sucursalUNQ.detallesNotaDeCredito(1));
	}
	
	
	

}
