package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import envios.TipoEnvio;
import pedido.Pedido;
import pedido.TipoEstado;
import sistemas.Catalogo;
import sistemas.Sucursal;
import ubicacionGeografica.Direccion;

class SucursalTestCase {

	private Catalogo catalogoUNQ;	
	private Sucursal sucursalUNQ;
	private Direccion direccionUNQ, direccion;
	
	@BeforeEach
	public void setUp() {
		catalogoUNQ = new Catalogo();
		direccionUNQ = new Direccion("Roque Sáenz Peña 124", -34.76493d, -58.278418d);
		sucursalUNQ = new Sucursal(28062026, catalogoUNQ, 100000f, direccionUNQ);
	                             // CUIT       CATALOGO   DINERO   DIRECCION
	}
	
	@Test
	void testLosDatosDeLaSucursal() {
		assertEquals(28062026, sucursalUNQ.getCUIT());
		assertTrue(sucursalUNQ.tieneCatalogo());
		assertEquals(100000f, sucursalUNQ.getDineroDisponible());
		assertEquals(direccionUNQ, sucursalUNQ.getDireccion());
	}
	
	@Test
	void testLaSurcusalCreaYGuardaPedidos() {
		 direccion =  new Direccion("9 de Julio 217", -34.712445d, -58.284493d);
		 Pedido pedido = sucursalUNQ.crearPedido("juan@gmail.com", direccion);
		 
		 assertTrue(sucursalUNQ.tienePedidoActivo(pedido));
		 
		 assertEquals("juan@gmail.com", pedido.getMail());
		 assertEquals(direccion, pedido.getDireccion());
		 assertEquals(pedido.getEstado(), TipoEstado.BORRADOR); // Cuando se crea el pedido debe estar en estado Borrador para que se pueda modificar
		 assertTrue(pedido.estaSinDefinirMetodoDePago());
		 assertEquals(pedido.getEnvio(), TipoEnvio.SINENVIODEFINIDO);
	}

}
