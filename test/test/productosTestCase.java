package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import src.Atributo;
import src.Catalogo;
import src.Individuales;

class productosTestCase {

	private Catalogo catalogo;
	private ArrayList<Atributo> atributos;
	private Atributo peso;
	
	@BeforeEach
	public void setUp() {
		atributos = new ArrayList<Atributo>();
		catalogo = new Catalogo();
		peso = new Atributo("Peso", "500g");
		atributos.add(peso);
		catalogo.registrarIndividual("Harina", "Marolio", "Almacen", atributos, 1500f, 10); // SKU = 1
	}
	
	@Test
	void testSeRegistraProductoEnElCatalogo() {
		
		assertTrue(catalogo.tieneProducto("Harina"));    // Comprueba si hay al menos un producto que sea harina
		assertEquals(10, catalogo.cantidadDe(1));        // Comprueba la cantidad que hay de tal prodcuto (con SKU=1)
		assertEquals(1, catalogo.cantidadDeProductos()); // Comprueba cuantos productos distintos hay en el catalogo
	}
	
	@Test
	void testLosProductosDelCatalagoExponenSuInformación() {
		
		Individuales harina = (Individuales) catalogo.buscarProducto(1);
		
		assertEquals("Harina", harina.getNombre());
		assertEquals("Peso: 500g.\n", harina.getDescripcion());
		assertEquals(1500f, harina.precioBase());
	}

}
