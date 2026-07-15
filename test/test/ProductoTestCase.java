package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import productos.Atributo;
import productos.Individual;
import productos.Paquete;
import productos.Producto;


class ProductoTestCase {
	
	private ArrayList<Atributo> atributosDummy;
	private ArrayList<Producto> productosDummy;  
	private Individual  	    individual;  
	private Paquete     	    paquete;
	
	
	@BeforeEach
	public void setUp() {
		atributosDummy = new ArrayList<Atributo>();
		productosDummy = new ArrayList<Producto>();
		individual     = new Individual(1, "Individual", "Marca", "Categoria", atributosDummy, 0, 0, 0);
		paquete        = new Paquete(2, "Producto", "Categoria", atributosDummy, productosDummy, 0, 0);
	}
	
	@Test
	void testUnProductoNoPuedeSerNull() {
		assertFalse(individual.equals(null));
	    assertFalse(paquete.equals(null));
	}
	
	@Test
	void testDosProductosNoSonIgualesSiNoTienenElMismoSKU() {
		
		assertFalse(individual.equals(paquete));
		assertEquals(1, individual.getSKU());
		
	    assertFalse(paquete.equals(individual));
	    assertEquals(2, paquete.getSKU());
	}
	
	@Test
	void testDosProductosSonIgualesSiTienenElMismoSKU() {
		
		Individual  otroIndividual = new Individual(1, "IndividualOtro", "MarcaOtra", "CategoriaOtra", atributosDummy, 1, 1, 1);
		
		assertTrue(individual.equals(otroIndividual));
		assertEquals(1, individual.getSKU());
		
	    assertTrue(otroIndividual.equals(individual));
	    assertEquals(1, otroIndividual.getSKU());
	}
	
	@Test
	void testDosProductosConMismoSKUTieneElMismoHashCode() {
		
		Paquete  otroPaquete = new Paquete(2, "ProductoOtro", "CategoriaOtra", atributosDummy, productosDummy, 1, 2);
		
		assertEquals(paquete.getSKU(), otroPaquete.getSKU());
		
		assertEquals(paquete.hashCode(), otroPaquete.hashCode());
	}

}
