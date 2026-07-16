package test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import productos.Atributo;
import productos.Individual;
import productos.Paquete;
import productos.Producto;
import productos.SistemaDeProductos;
import sistemas.Catalogo;
import sistemas.Sucursal;
import ubicacionGeografica.Direccion;
import reportes.RegistroDeVentas;

class PaqueteTestCase {
	
	private SistemaDeProductos  sistemaDeProductos;
	private Catalogo            catalogoUNQ;	
	private Sucursal            sucursalUNQ;
	private Direccion           direccionDummy;
	private ArrayList<Sucursal> sucursales;
	private ArrayList<Atributo> atributos, a2, a3, a4, a5;
	private ArrayList<Producto> productos;
	private Atributo            color, p2, p3, p4;
	private Paquete             packParaComputadora;
	private Individual          microfono;
	private ArrayList<Producto> productos2;
	
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
		direccionDummy  = mock(Direccion.class);
		sucursalUNQ     = new Sucursal(28062026, catalogoUNQ, 100000f, direccionDummy, sucursales, new RegistroDeVentas());
		
		sistemaDeProductos.agregarSucursal(sucursalUNQ);
		
		registrarIndividuales();
	
		productos.add((Individual) catalogoUNQ.buscarProducto(1));
		productos.add((Individual) catalogoUNQ.buscarProducto(2));
		productos.add((Individual) catalogoUNQ.buscarProducto(3));
		productos.add((Individual) catalogoUNQ.buscarProducto(4));
		
		sistemaDeProductos.registrarPaquete("Pack para computadora", "Accesesorios y Perifericos", a5, productos, 10, 3);   // SKU = 5
		
		productos2 = new ArrayList<>(productos);
		
		packParaComputadora = (Paquete) catalogoUNQ.buscarProducto(5);
		
		sistemaDeProductos.registrarIndividual("Microfono", "SnapDragon", "Periferico", a4, 1500f, 10, 98f);    // SKU = 6
		microfono = (Individual) catalogoUNQ.buscarProducto(6);
		
		productos2.add(packParaComputadora);
		productos2.add(microfono);
	
	}

	private void registrarIndividuales() {
		color     = new Atributo("Color", "Blanco");
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
	}

	@Test
	void testSeRegistraPaqueteEnElCatalogo() {
		
		assertTrue(catalogoUNQ.tieneProducto(5));   	    // Comprueba si hay al menos un producto que tenga ese SKU
		assertEquals(3, catalogoUNQ.cantidadDe(5));         // Comprueba la cantidad que hay de tal prodcuto (con SKU=5)
		assertEquals(6, catalogoUNQ.cantidadDeProductos()); // Comprueba cuantos productos distintos hay en el catalogo
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
		
		sistemaDeProductos.registrarPaquete("Pack para computadora 2", "Accesesorios y Perifericos", a5, productos2, 40, 2);   // SKU = 7
		
		Paquete paquete = (Paquete) catalogoUNQ.buscarProducto(7);
		
		assertEquals(7, catalogoUNQ.cantidadDeProductos()); // Se registra el nuevo producto
		assertTrue(paquete.tieneProducto(microfono));
		assertTrue(paquete.tieneProducto(packParaComputadora));
	}
	
	@Test
	void testNoSePuedeRegistrarUnPaqueteConUnStockMenorA0() {
		
		 IllegalArgumentException error = assertThrows(IllegalArgumentException.class,() -> sistemaDeProductos.registrarPaquete("Pack para computadora 2", "Accesesorios y Perifericos", a5, productos2, 40, -1));
																													// SKU = 6
		 assertEquals("El stock no puede ser negativo", error.getMessage());
		
		 assertEquals(6, catalogoUNQ.cantidadDeProductos()); // No se registro el nuevo producto
	}
	
	@Test
	void testSePuedeRegistrarUnPaqueteConUnStock0() {
		
		sistemaDeProductos.registrarPaquete("Pack para computadora 2", "Accesesorios y Perifericos", a5, productos2, 40, 0);
							// SKU = 7
			
		 assertTrue(catalogoUNQ.tieneProducto(7));   		  // Comprueba si hay al menos un producto que tenga ese SKU
		 assertEquals(0, catalogoUNQ.cantidadDe(7)); 		  // Verifica que el prefio sea 0
		 assertEquals(7, catalogoUNQ.cantidadDeProductos());  // Verifica que se sumo al catalogo
	}
	
	@Test
	void testNoSePuedeRegistrarUnPaqueteQueNoTieneProdcutos() {
		
		 ArrayList<Producto> productos3 = new ArrayList<>();
		 
		 IllegalArgumentException error = assertThrows(IllegalArgumentException.class,() -> sistemaDeProductos.registrarPaquete("Pack para computadora 2", "Accesesorios y Perifericos", a5, productos3, 40, 10));
			                                                                                                    // SKU = 6
		 assertEquals("El paquete debe tener al menos un producto", error.getMessage());
		 assertEquals(6, catalogoUNQ.cantidadDeProductos()); // No se registro el nuevo producto
	}
	
	@Test
	void testCuandoUnPaqueteTieneDescuentoDel100PorCientoSuPrecioEsGratis() {
		sistemaDeProductos.registrarPaquete("Pack para computadora 2", "Accesesorios y Perifericos", a5, productos2, 100, 0);
		// SKU = 7
		
		assertEquals(0, catalogoUNQ.buscarProducto(7).precioFinal());
	}
	
	@Test
	void testElPaqueteSabeSuPrecioFinal() {
		sistemaDeProductos.registrarPaquete("Pack para computadora 2", "Accesesorios y Perifericos", a5, productos2, 40, 0);
		// SKU = 7
		
		assertEquals(27120.002, catalogoUNQ.buscarProducto(7).precioFinal(), 0.01f);
		// Se suma el precio del paquete 'Pack para computadora' + el precio del 'Microfono' y el descuento del paquete 'Pack para computadora 2'
	}
	
	
	@Test
	void testElPaqueteSabeSuPeso() {
		sistemaDeProductos.registrarPaquete("Pack para computadora 2", "Accesesorios y Perifericos", a5, productos2, 40, 0);
		// SKU = 7
		
		assertEquals(548, catalogoUNQ.buscarProducto(7).getPeso());
		// Se suma el peso del paquete 'Pack para computadora' + el peso del 'Microfono'
	}
	
}
