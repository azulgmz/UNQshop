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
import sistemas.SistemaDeProductos;
import sistemas.Sucursal;
import ubicacionGeografica.Direccion;

class PaqueteTestCase {
	
	private SistemaDeProductos sistemaDeProductos;
	private Catalogo catalogoUNQ;	
	private Sucursal sucursalUNQ;
	private Direccion direccionUNQ;
	private ArrayList<Sucursal> sucursales;
	private ArrayList<Atributo> atributos, a2, a3, a4, a5;
	private ArrayList<Producto> productos;
	private Atributo color, p2, p3, p4;
	
	@BeforeEach
	public void setUp() {
		sistemaDeProductos = new SistemaDeProductos();
		
		atributos = new ArrayList<Atributo>();
		a2        = new ArrayList<Atributo>();
		a3        = new ArrayList<Atributo>();
		a4        = new ArrayList<Atributo>();
		a5        = new ArrayList<Atributo>();
		
		
		catalogoUNQ     = new Catalogo();
		sucursales      = new ArrayList<Sucursal>();
		direccionUNQ    = new Direccion("Roque Sáenz Peña 124", -34.76493d, -58.278418d);
		sucursalUNQ     = new Sucursal(28062026, catalogoUNQ, 100000f, direccionUNQ, sucursales);
		
		sistemaDeProductos.agregarSucursal(sucursalUNQ);
		
		color = new Atributo("Color", "Blanco");
		productos = new ArrayList<Producto>();
		
		atributos.add(color);
		
		sistemaDeProductos.registrarIndividual("Teclado", "SnapDragon", "Periferico", atributos, 6500f, 10, 50f); // SKU = 1
		
		p2 = new Atributo("Color", "Esmeralda");
		a2.add(p2);
		sistemaDeProductos.registrarIndividual("Mouse", "SnapDragon", "Periferico", a2, 1500f, 10, 20f);         // SKU = 2
		
		p3 = new Atributo("Color", "Rojo");
		a3.add(p3);
		sistemaDeProductos.registrarIndividual("Auriculares", "SnapDragon", "Periferico", a3, 8500f, 10, 130f);   // SKU = 3
		
		p4 = new Atributo("Color", "Negro");
		a4.add(p4);
		sistemaDeProductos.registrarIndividual("Mousepad", "SnapDragon", "Accesesorio", a4, 6500f, 10, 25f);    // SKU = 4
		
		Individual teclado     = (Individual) catalogoUNQ.buscarProducto(1);
		Individual mouse       = (Individual) catalogoUNQ.buscarProducto(2);
		Individual auriculares = (Individual) catalogoUNQ.buscarProducto(3);
		Individual mousepad    = (Individual) catalogoUNQ.buscarProducto(4);
		
		productos.add(teclado);
		productos.add(mouse);
		productos.add(auriculares);
		productos.add(mousepad);
		sistemaDeProductos.registrarPaquete("Pack para computadora", "Accesesorios y Perifericos", a5, productos, 10, 3);   // SKU = 5
	}

	@Test
	void testSeRegistraPaqueteEnElCatalogo() {
		
		assertTrue(catalogoUNQ.tieneProducto(5));   		 // Comprueba si hay al menos un producto que tenga ese SKU
		assertEquals(3, catalogoUNQ.cantidadDe(5));         // Comprueba la cantidad que hay de tal prodcuto (con SKU=5)
		assertEquals(5, catalogoUNQ.cantidadDeProductos()); // Comprueba cuantos productos distintos hay en el catalogo
	}
	
	@Test
	void testLosProductosPaquetesDelCatalagoExponenSuInformación() {
		
		Paquete packParaComputadora = (Paquete) catalogoUNQ.buscarProducto(5);
		
		assertEquals("Pack para computadora", packParaComputadora.getNombre());
		assertEquals("Color: Blanco.\n" + "Color: Esmeralda.\n" + "Color: Rojo.\n" + "Color: Negro.\n", packParaComputadora.getDescripcion());
		assertEquals(20700, packParaComputadora.precioFinal());
	}
	
	@Test
	void testLosProductosPaquetesTambiénPuedenTenerIncluidosPaquetes() {
		
		sistemaDeProductos.registrarIndividual("Microfono", "SnapDragon", "Periferico", a4, 1500f, 10, 98f);    // SKU = 6
	
		Paquete packParaComputadora = (Paquete) catalogoUNQ.buscarProducto(5);
		Individual microfono = (Individual) catalogoUNQ.buscarProducto(6);
		
		ArrayList<Producto> productos2 = new ArrayList<>(productos);
		productos2.add(packParaComputadora);
		productos2.add(microfono);
		
		sistemaDeProductos.registrarPaquete("Pack para computadora 2", "Accesesorios y Perifericos", a5, productos2, 40, 2);   // SKU = 7
		
		assertEquals(27120.002, catalogoUNQ.buscarProducto(7).precioFinal(), 0.01f);
		// Se suma el precio del paquete 'Pack para computadora' + el precio del 'Microfono' y el descuento del paquete 'Pack para computadora 2'
	}
	
	@Test
	void testNoSePuedeRegistrarUnPaqueteConUnStockMenorA0() {
		Paquete packParaComputadora = (Paquete) catalogoUNQ.buscarProducto(5);
		ArrayList<Producto> productos2 = new ArrayList<>(productos);
		productos2.add(packParaComputadora);
		
		 IllegalArgumentException error = assertThrows(IllegalArgumentException.class,() -> sistemaDeProductos.registrarPaquete("Pack para computadora 2", "Accesesorios y Perifericos", a5, productos2, 40, -1));
																													// SKU = 6
		 assertEquals("El stock no puede ser negativo", error.getMessage());
		
		 assertEquals(5, catalogoUNQ.cantidadDeProductos()); // No se registro el nuevo producto
	}
	
	@Test
	void testSePuedeRegistrarUnPaqueteConUnStock0() {
		Paquete packParaComputadora = (Paquete) catalogoUNQ.buscarProducto(5);
		ArrayList<Producto> productos2 = new ArrayList<>(productos);
		productos2.add(packParaComputadora);
		 
		sistemaDeProductos.registrarPaquete("Pack para computadora 2", "Accesesorios y Perifericos", a5, productos2, 40, 0);
							// SKU = 6
			
		 assertTrue(catalogoUNQ.tieneProducto(6));   		  // Comprueba si hay al menos un producto que tenga ese SKU
		 assertEquals(0, catalogoUNQ.cantidadDe(6)); 		  // Verifica que el prefio sea 0
		 assertEquals(6, catalogoUNQ.cantidadDeProductos()); // Verifica que se sumo al catalogo
	}
	
	@Test
	void testNoSePuedeRegistrarUnPaqueteQueNoTieneProdcutos() {
		
		 ArrayList<Producto> productos2 = new ArrayList<>();
		 
		 IllegalArgumentException error = assertThrows(IllegalArgumentException.class,() -> sistemaDeProductos.registrarPaquete("Pack para computadora 2", "Accesesorios y Perifericos", a5, productos2, 40, 10));
			                                                                                                    // SKU = 6
		 assertEquals("El paquete debe tener al menos un producto", error.getMessage());
		 assertEquals(5, catalogoUNQ.cantidadDeProductos()); // No se registro el nuevo producto
	}
	

}
