package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.Atributo;
import src.Catalogo;
import src.Individuales;
import src.Producto;
import src.Paquete;

class paquetesTestCase {
	
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
		
		catalogo.registrarIndividual("Teclado", "SnapDragon", "Periferico", atributos, 6500f, 10); // SKU = 1
		
		p2 = new Atributo("Peso", "50g");
		a2.add(p2);
		catalogo.registrarIndividual("Mouse", "SnapDragon", "Periferico", a2, 1500f, 10);         // SKU = 2
		
		p3 = new Atributo("Peso", "150g");
		a3.add(p3);
		catalogo.registrarIndividual("Auriculares", "SnapDragon", "Periferico", a3, 8500f, 10);   // SKU = 3
		
		p4 = new Atributo("Peso", "25g");
		a4.add(p4);
		catalogo.registrarIndividual("Mousepad", "SnapDragon", "Accesesorio", a4, 6500f, 10);    // SKU = 4
		
		Individuales teclado     = (Individuales) catalogo.buscarProducto(1);
		Individuales mouse       = (Individuales) catalogo.buscarProducto(2);
		Individuales auriculares = (Individuales) catalogo.buscarProducto(3);
		Individuales mousepad    = (Individuales) catalogo.buscarProducto(4);
		
		productos.add(teclado);
		productos.add(mouse);
		productos.add(auriculares);
		productos.add(mousepad);
		catalogo.registrarPaquete("Pack para computadora", "SnapDragon", "Accesesorios y Perifericos", a5, productos, 0, 10, 3);   // SKU = 5
	}

	@Test
	void testSeRegistraPaqueteEnElCatalogo() {
		
		assertTrue(catalogo.tieneProducto("Pack para computadora"));    // Comprueba si hay al menos un producto que sea Pack para computadora
		assertEquals(3, catalogo.cantidadDe(5));                        // Comprueba la cantidad que hay de tal prodcuto (con SKU=5)
		assertEquals(5, catalogo.cantidadDeProductos());                // Comprueba cuantos productos distintos hay en el catalogo
	}
	
	@Test
	void testLosProductosPaquetesDelCatalagoExponenSuInformación() {
		
		Paquete packParaComputadora = (Paquete) catalogo.buscarProducto(5);
		
		assertEquals("Pack para computadora", packParaComputadora.getNombre());
		assertEquals("Peso: 250g.\n" + "Peso: 50g.\n" + "Peso: 150g.\n" + "Peso: 25g.\n", packParaComputadora.getDescripcion());
		assertEquals(22999, packParaComputadora.precioFinal());
	}
	
	

}
