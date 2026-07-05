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

class IndividualTestCase {

	private Catalogo catalogo;
	private ArrayList<Atributo> atributos;
	private Atributo peso;
	
	@BeforeEach
	public void setUp() {
		atributos = new ArrayList<Atributo>();
		catalogo = new Catalogo();
		peso = new Atributo("Peso", "250g");
		atributos.add(peso);
		catalogo.registrarIndividual("Teclado", "SnapDragon", "Periferico", atributos, 6500f, 10); // SKU = 1
	}
	
	@Test
	void testSeRegistraProductoIndividualEnElCatalogo() {
		
		assertTrue(catalogo.tieneProducto("Teclado"));    // Comprueba si hay al menos un producto que sea teclado
		assertEquals(10, catalogo.cantidadDe(1));        // Comprueba la cantidad que hay de tal prodcuto (con SKU=1)
		assertEquals(1, catalogo.cantidadDeProductos()); // Comprueba cuantos productos distintos hay en el catalogo
	}
	
	@Test
	void testLosProductosIndividualesDelCatalagoExponenSuInformación() {
		
		Individual teclado = (Individual) catalogo.buscarProducto(1);
		
		assertEquals("Teclado", teclado.getNombre());
		assertEquals("Peso: 250g.\n", teclado.getDescripcion());
		assertEquals(6500f, teclado.precioFinal());
	}
	
	@Test
	void testNoSePuedeRegistrarUnProductoConUnPrecioMenorA0() {
		
		 IllegalArgumentException error = assertThrows(IllegalArgumentException.class,() -> catalogo.registrarIndividual("Mousepad", "SnapDragon", "Accesorio", atributos, -1f, 10));
		
		 assertEquals("El precio no puede ser negativo", error.getMessage());
		
		 assertEquals(1, catalogo.cantidadDeProductos()); // No se registro el nuevo producto
	}
	
	@Test
	void testNoSePuedeRegistrarUnProductoConUnStockMenorA0() {
		
		 IllegalArgumentException error = assertThrows(IllegalArgumentException.class,() -> catalogo.registrarIndividual("Mousepad", "SnapDragon", "Accesorio", atributos, 1000f, -1));
		
		 assertEquals("El stock no puede ser negativo", error.getMessage());
		
		 assertEquals(1, catalogo.cantidadDeProductos()); // No se registro el nuevo producto
	}
	
	@Test
	void testSePuedeRegistrarUnProductoConUnPrecio0() {
		
		 catalogo.registrarIndividual("Mousepad", "SnapDragon", "Accesorio", atributos, 0f, 10); // SKU = 2
	
		 assertTrue(catalogo.tieneProducto("Mousepad"));          // Verifica que se registro el producto 
		 assertEquals(0, catalogo.buscarProducto(2).precioFinal()); // Verifica que el prefio sea 0
		 assertEquals(2, catalogo.cantidadDeProductos());         // Verifica que se sumo al catalogo
		 
	}
	
	@Test
	void testSePuedeRegistrarUnProductoConUnStock0() {
		 
		 catalogo.registrarIndividual("Mousepad", "SnapDragon", "Accesorio", atributos, 100f, 0); // SKU = 2
			
		 assertTrue(catalogo.tieneProducto("Mousepad"));    // Verifica que se registro el producto 
		 assertEquals(0, catalogo.cantidadDe(2)); 			// Verifica que el prefio sea 0
		 assertEquals(2, catalogo.cantidadDeProductos());   // Verifica que se sumo al catalogo
	}

	
	
	
}
