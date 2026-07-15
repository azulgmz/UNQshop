package test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import productos.Atributo;
import productos.Individual;
import productos.Producto;
import sistemas.Catalogo;

class CatalogoTestCase {
	
	private Catalogo catalogo;
	
	@BeforeEach
	void setUp() {
		catalogo = new Catalogo();
	}
	
	@Test
	void testEnElCatalogoNoSePuedeBuscarPorSKUNegativo() {
		
		IllegalArgumentException error = assertThrows(IllegalArgumentException.class,() -> catalogo.buscarProducto(-1));
		
	    assertEquals("El SKU no puede ser negativo", error.getMessage());
	}
	
	@Test
	void testEnElCatalogoNoSePuedeBuscarPorUnSKUInexistente() {
		// Probamos cuando el catalogo no tiene ningun producto registrado
		IllegalArgumentException error = assertThrows(IllegalArgumentException.class,() -> catalogo.buscarProducto(1));
		
	    assertEquals("No existe un producto con SKU 1", error.getMessage());
	}
	
	@Test
	void testEnElCatalogoNoSePuedeBuscarPorUnSKUInexistenteParteDos() {
		// Probamos cuando el catalogo no tiene al menos un producto registrado
		ArrayList<Atributo> atributosDummy = new ArrayList<Atributo>();
		Individual individual     = new Individual(1, "Individual", "Marca", "Categoria", atributosDummy, 0, 0, 0);
		
		catalogo.agregarProducto(individual);
		
		IllegalArgumentException error = assertThrows(IllegalArgumentException.class,() -> catalogo.buscarProducto(2));
		
	    assertEquals("No existe un producto con SKU 2", error.getMessage());
	}

}
