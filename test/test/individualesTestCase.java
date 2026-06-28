package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



import src.Atributo;
import src.Catalogo;
import src.Individuales;

class individualesTestCase {

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
		
		assertTrue(catalogo.tieneProducto("Teclado"));    // Comprueba si hay al menos un producto que sea harina
		assertEquals(10, catalogo.cantidadDe(1));        // Comprueba la cantidad que hay de tal prodcuto (con SKU=1)
		assertEquals(1, catalogo.cantidadDeProductos()); // Comprueba cuantos productos distintos hay en el catalogo
	}
	
	@Test
	void testLosProductosIndividualesDelCatalagoExponenSuInformación() {
		
		Individuales teclado = (Individuales) catalogo.buscarProducto(1);
		
		assertEquals("Teclado", teclado.getNombre());
		assertEquals("Peso: 250g.\n", teclado.getDescripcion());
		assertEquals(1500f, teclado.precioFinal());
	}
	
	
}
