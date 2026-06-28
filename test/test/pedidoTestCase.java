package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pedido.Pedido;
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
	void testCuandoUnPedidoAgregaUnProductoBajaElStockDelCatalogo() {
		
		pedido.agregarProducto(catalogoCorrientes.buscarProducto(1)); // Se agrega 'monitor' al pedido
		
		assertEquals(99, catalogoCorrientes.cantidadDe(1));  // Se verifica que se bajo uno en stock de 'Monitor'
		assertEquals(100, catalogoCorrientes.cantidadDe(2)); // Se verifica que el stock de 'CPU' queda intacto
		
		assertTrue(pedido.agregoA(1));  // Se verifica que se agrego un Monitor SnapDragon al pedido
	}

}
