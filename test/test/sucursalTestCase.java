package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pedido.Pedido;
import pedido.TipoEstado;
import sistemas.Catalogo;
import sistemas.Sucursal;

class sucursalTestCase {

	private int CUIT;
	private Catalogo catalogoCorrientes;
	private float dineroDisponible;
	private String direccion;
	private Sucursal sucursalCorrientes;
	
	@BeforeEach
	public void setUp() {
		catalogoCorrientes = new Catalogo();
	    sucursalCorrientes = new Sucursal(28062026, catalogoCorrientes, 100000f, "Avenida Corrientes 1661");
	                                    //  CIUT        CATALOGO         DINERO         DIRECCION
	}
	
	@Test
	void testLosDatosDeLaSucursal() {
		assertEquals(28062026, sucursalCorrientes.getCUIT());
		assertTrue(sucursalCorrientes.tieneCatalogo());
		assertEquals(100000f, sucursalCorrientes.getDineroDisponible());
		assertEquals("Avenida Corrientes 1661", sucursalCorrientes.getDireccion());
	}
	
	@Test
	void testLaSurcusalCreaYGuardaPedidos() {
		 Pedido pedido = sucursalCorrientes.crearPedido("juan@gmail.com", "Juan Domingo Peron 133");
		 
		 assertTrue(sucursalCorrientes.tienePedidoActivo(pedido));
		 
		 assertEquals("juan@gmail.com", pedido.getMail());
		 assertEquals("Juan Domingo Peron 133", pedido.getDireccion());
		 assertEquals(pedido.getEstado(), TipoEstado.BORRADOR); // Cuando se crea el pedido debe estar en estado Borrador para que se pueda modificar
		 assertTrue(pedido.estaSinDefinirMetodoDePago());
		 assertTrue(pedido.estaSinDefinirEnvio());
	}

}
