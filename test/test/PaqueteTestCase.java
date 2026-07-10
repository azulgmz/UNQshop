package test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import productos.Atributo;
import productos.Individual;
import productos.Paquete;
import productos.Producto;
import sistemas.Catalogo;

class PaqueteTestCase {
	
	private Catalogo catalogo;
	private ArrayList<Atributo> atributos, a2, a3, a4, a5;
	private ArrayList<Producto> productos;
	private Atributo peso, p2, p3, p4;
	
	@BeforeEach
	public void setUp() {
		atributos = new ArrayList<Atributo>();
		a2 = new ArrayList<Atributo>();
		a3 = new ArrayList<Atributo>();
		a4 = new ArrayList<Atributo>();
		a5 = new ArrayList<Atributo>();
		catalogo = new Catalogo();
		
		peso = new Atributo("Peso", "250g");
		productos = new ArrayList<Producto>();
		
		atributos.add(peso);
		
		catalogo.registrarIndividual("Teclado", "SnapDragon", "Periferico", atributos, 6500f, 10, 50f); // SKU = 1
		
		p2 = new Atributo("Peso", "50g");
		a2.add(p2);
		catalogo.registrarIndividual("Mouse", "SnapDragon", "Periferico", a2, 1500f, 10, 20f);         // SKU = 2
		
		p3 = new Atributo("Peso", "150g");
		a3.add(p3);
		catalogo.registrarIndividual("Auriculares", "SnapDragon", "Periferico", a3, 8500f, 10, 130f);   // SKU = 3
		
		p4 = new Atributo("Peso", "25g");
		a4.add(p4);
		catalogo.registrarIndividual("Mousepad", "SnapDragon", "Accesesorio", a4, 6500f, 10, 25f);    // SKU = 4
		
		Individual teclado     = (Individual) catalogo.buscarProducto(1);
		Individual mouse       = (Individual) catalogo.buscarProducto(2);
		Individual auriculares = (Individual) catalogo.buscarProducto(3);
		Individual mousepad    = (Individual) catalogo.buscarProducto(4);
		
		productos.add(teclado);
		productos.add(mouse);
		productos.add(auriculares);
		productos.add(mousepad);
		catalogo.registrarPaquete("Pack para computadora", "Accesesorios y Perifericos", a5, productos, 10, 3);   // SKU = 5
	}

	@Test
	void testSeRegistraPaqueteEnElCatalogo() {
		
		assertTrue(catalogo.tieneProducto(5));   		 // Comprueba si hay al menos un producto que tenga ese SKU
		assertEquals(3, catalogo.cantidadDe(5));         // Comprueba la cantidad que hay de tal prodcuto (con SKU=5)
		assertEquals(5, catalogo.cantidadDeProductos()); // Comprueba cuantos productos distintos hay en el catalogo
	}
	
	@Test
	void testLosProductosPaquetesDelCatalagoExponenSuInformación() {
		
		Paquete packParaComputadora = (Paquete) catalogo.buscarProducto(5);
		
		assertEquals("Pack para computadora", packParaComputadora.getNombre());
		assertEquals("Peso: 250g.\n" + "Peso: 50g.\n" + "Peso: 150g.\n" + "Peso: 25g.\n", packParaComputadora.getDescripcion());
		assertEquals(20700, packParaComputadora.precioFinal());
	}
	
	@Test
	void testLosProductosPaquetesTambiénPuedenTenerIncluidosPaquetes() {
		
		catalogo.registrarIndividual("Microfono", "SnapDragon", "Periferico", a4, 1500f, 10, 98f);    // SKU = 6
	
		Paquete packParaComputadora = (Paquete) catalogo.buscarProducto(5);
		Individual microfono = (Individual) catalogo.buscarProducto(6);
		
		ArrayList<Producto> productos2 = new ArrayList<>(productos);
		productos2.add(packParaComputadora);
		productos2.add(microfono);
		
		catalogo.registrarPaquete("Pack para computadora 2", "Accesesorios y Perifericos", a5, productos2, 40, 2);   // SKU = 7
		
		assertEquals(27120.002, catalogo.buscarProducto(7).precioFinal(), 0.01f);
		// Se suma el precio del paquete 'Pack para computadora' + el precio del 'Microfono' y el descuento del paquete 'Pack para computadora 2'
	}
	
	@Test
	void testNoSePuedeRegistrarUnPaqueteConUnStockMenorA0() {
		Paquete packParaComputadora = (Paquete) catalogo.buscarProducto(5);
		ArrayList<Producto> productos2 = new ArrayList<>(productos);
		productos2.add(packParaComputadora);
		
		 IllegalArgumentException error = assertThrows(IllegalArgumentException.class,() -> catalogo.registrarPaquete("Pack para computadora 2", "Accesesorios y Perifericos", a5, productos2, 40, -1));
																													// SKU = 6
		 assertEquals("El stock no puede ser negativo", error.getMessage());
		
		 assertEquals(5, catalogo.cantidadDeProductos()); // No se registro el nuevo producto
	}
	
	@Test
	void testSePuedeRegistrarUnPaqueteConUnStock0() {
		Paquete packParaComputadora = (Paquete) catalogo.buscarProducto(5);
		ArrayList<Producto> productos2 = new ArrayList<>(productos);
		productos2.add(packParaComputadora);
		 
		 catalogo.registrarPaquete("Pack para computadora 2", "Accesesorios y Perifericos", a5, productos2, 40, 0);
							// SKU = 6
			
		 assertTrue(catalogo.tieneProducto(6));   		  // Comprueba si hay al menos un producto que tenga ese SKU
		 assertEquals(0, catalogo.cantidadDe(6)); 		  // Verifica que el prefio sea 0
		 assertEquals(6, catalogo.cantidadDeProductos()); // Verifica que se sumo al catalogo
	}
	
	@Test
	void testNoSePuedeRegistrarUnPaqueteQueNoTieneProdcutos() {
		
		 ArrayList<Producto> productos2 = new ArrayList<>();
		 
		 IllegalArgumentException error = assertThrows(IllegalArgumentException.class,() -> catalogo.registrarPaquete("Pack para computadora 2", "Accesesorios y Perifericos", a5, productos2, 40, 10));
			                                                                                                    // SKU = 6
		 assertEquals("El paquete debe tener al menos un producto", error.getMessage());
		 assertEquals(5, catalogo.cantidadDeProductos()); // No se registro el nuevo producto
	}
	

}
