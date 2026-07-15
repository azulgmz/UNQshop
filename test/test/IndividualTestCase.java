package test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import productos.Atributo;
import productos.Individual;
import sistemas.Catalogo;
import sistemas.SistemaDeProductos;
import sistemas.Sucursal;
import ubicacionGeografica.Direccion;

class IndividualTestCase {
	
	private SistemaDeProductos sistemaDeProductos;
	private Catalogo catalogoUNQ;	
	private Sucursal sucursalUNQ;
	private Direccion direccionUNQ;
	private ArrayList<Atributo> atributos;
	private ArrayList<Sucursal> sucursales;
	
	@BeforeEach
	public void setUp() {
		sistemaDeProductos = new SistemaDeProductos();
		
		catalogoUNQ     = new Catalogo();
		sucursales      = new ArrayList<Sucursal>();
		direccionUNQ    = new Direccion("Roque Sáenz Peña 124", -34.76493d, -58.278418d);
		sucursalUNQ     = new Sucursal(28062026, catalogoUNQ, 100000f, direccionUNQ, sucursales);
	                                  // CUIT       CATALOGO   DINERO   DIRECCION    SUCURSALES
		
		sistemaDeProductos.agregarSucursal(sucursalUNQ);
		
		atributos = new ArrayList<Atributo>();
		atributos.add(new Atributo("Color", "Azul"));
		sistemaDeProductos.registrarIndividual("Teclado", "SnapDragon", "Periferico", atributos, 6500f, 10, 530f); // SKU = 1
	}
	
	@Test
	void testSeRegistraProductoIndividualEnElCatalogo() {
		
		assertTrue(catalogoUNQ.tieneProducto(1));   		 // Comprueba si hay al menos un producto que tenga ese SKU
		assertEquals(10, catalogoUNQ.cantidadDe(1));         // Comprueba la cantidad que hay de tal prodcuto (con SKU=1)
		assertEquals(1, catalogoUNQ.cantidadDeProductos());  // Comprueba cuantos productos distintos hay en el catalogo
	}
	
	@Test
	void testLosProductosIndividualesDelCatalagoExponenSuInformación() {
		
		Individual teclado = (Individual) catalogoUNQ.buscarProducto(1);
		
		assertEquals("Teclado", teclado.getNombre());
		assertEquals("Color: Azul.\n", teclado.getDescripcion());
		assertEquals(6500f, teclado.precioFinal());
		assertEquals(530f, teclado.getPeso());
		assertEquals("SnapDragon", teclado.getMarca());
	}
	
	@Test
	void testNoSePuedeRegistrarUnProductoConUnPrecioMenorA0() {
		
		 IllegalArgumentException error = assertThrows(IllegalArgumentException.class,() -> sistemaDeProductos.registrarIndividual("Mousepad", "SnapDragon", "Accesorio", atributos, -1f, 10, 25f));
		
		 assertEquals("El precio no puede ser negativo", error.getMessage());
		
		 assertEquals(1, catalogoUNQ.cantidadDeProductos()); // No se registro el nuevo producto
	}
	
	@Test
	void testNoSePuedeRegistrarUnProductoConUnStockMenorA0() {
		
		 IllegalArgumentException error = assertThrows(IllegalArgumentException.class,() -> sistemaDeProductos.registrarIndividual("Mousepad", "SnapDragon", "Accesorio", atributos, 1000f, -1, 25f));
		
		 assertEquals("El stock no puede ser negativo", error.getMessage());
		
		 assertEquals(1, catalogoUNQ.cantidadDeProductos()); // No se registro el nuevo producto
	}
	
	@Test
	void testSePuedeRegistrarUnProductoConUnPrecio0() {
		
		sistemaDeProductos.registrarIndividual("Mousepad", "SnapDragon", "Accesorio", atributos, 0f, 10, 25f); // SKU = 2
	
		 assertTrue(catalogoUNQ.tieneProducto(2));   		            // Comprueba si hay al menos un producto que tenga ese SKU
		 assertEquals(0, catalogoUNQ.buscarProducto(2).precioFinal()); // Verifica que el prefio sea 0
		 assertEquals(2, catalogoUNQ.cantidadDeProductos());           // Verifica que se sumo al catalogo
		 
	}
	
	@Test
	void testSePuedeRegistrarUnProductoConUnStock0() {
		 
		sistemaDeProductos.registrarIndividual("Mousepad", "SnapDragon", "Accesorio", atributos, 100f, 0, 25f); // SKU = 2
			
		 assertTrue(catalogoUNQ.tieneProducto(2));   	    	// Comprueba si hay al menos un producto que tenga ese SKU
		 assertEquals(0, catalogoUNQ.cantidadDe(2)); 			// Verifica que el prefio sea 0
		 assertEquals(2, catalogoUNQ.cantidadDeProductos());   // Verifica que se sumo al catalogo
	}

	
	
	
}
