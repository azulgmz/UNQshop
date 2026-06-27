package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import src.Atributo;
import src.Catalogo;

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
	}
	
	@Test
	void testSeRegistraProductoEnElCatalogo() {
		
		catalogo.registrarIndividual("Harina", "Marolio", "Almacen", atributos, 1500f, 10);
		
		assertTrue(catalogo.tieneProducto("Harina"));    // Comprueba si hay al menos un producto que sea harina
		assertEquals(10, catalogo.cantidadDe(1));        // Comprueba la cantidad que hay de tal prodcuto
		assertEquals(1, catalogo.cantidadDeProductos()); // Comprueba cuantos productos distintos hay en el catalogo
	}

}
