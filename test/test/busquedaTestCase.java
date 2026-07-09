package test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import busquedas.Buscador;
import busquedas.BusquedaPorAND;
import busquedas.BusquedaPorNombre;
import busquedas.BusquedaPorOR;
import busquedas.BusquedaPorCategoria;
import busquedas.BusquedaPorDisponibilidad;
import busquedas.BusquedaPorNOT;
import busquedas.BusquedaPorPrecioMax;
import busquedas.TipoDeBusqueda;
import productos.Atributo;
import productos.Producto;
import sistemas.Catalogo;

class busquedaTestCase {

	private Catalogo		    catalogoCorrientes;
	private ArrayList<Atributo> atributosDummy;
	private List<Producto>      productosDeseados;
	private Buscador            buscador;
	
	@BeforeEach
	void setUp() {
		catalogoCorrientes = new Catalogo();
		atributosDummy     = new ArrayList<>();
		productosDeseados  = new ArrayList<>();
		buscador =  catalogoCorrientes.getBuscador();
		
		catalogoCorrientes.registrarIndividual("Monitor", "Snapdragon", "Perifericos", atributosDummy, 100.1f, 1); // SKU = 1
	    catalogoCorrientes.registrarIndividual("CPU", "Snapdragon", "Hardwar", atributosDummy, 100f, 0);           // SKU = 2
	    catalogoCorrientes.registrarIndividual("Mouse", "Snapdragon", "Perifericos", atributosDummy, 101f, 10);    // SKU = 3
	    catalogoCorrientes.registrarIndividual("monitor", "LG", "Perifericos", atributosDummy, 99f, 0);            // SKU = 4
	}
	
	@Test
	void testEnElCatalogoSePuedeBuscarProductosPorNombre() {
		
		// Ejemplo buscando Monitor, deberia devolver el monitor snapdragon y LG, porque no debe hacer distinciones
		
	    productosDeseados.add(catalogoCorrientes.buscarProducto(1)); // 'Monitor'
	    productosDeseados.add(catalogoCorrientes.buscarProducto(4)); // 'monitor'
	    
	    buscador.setTipoDeBusqueda(new BusquedaPorNombre("Monitor", buscador));
	    
	    assertEquals(productosDeseados, catalogoCorrientes.buscarProductos());
	}
	
	@Test
	void testEnElCatalogoSePuedeBuscarProductosPorPrecioMaximo() {
		
	    productosDeseados.add(catalogoCorrientes.buscarProducto(2)); // Precio 100
	    productosDeseados.add(catalogoCorrientes.buscarProducto(4)); // Precio 99
	    
	    buscador.setTipoDeBusqueda(new BusquedaPorPrecioMax(100, buscador));
	    
	    assertEquals(productosDeseados, catalogoCorrientes.buscarProductos());
	}
	
	@Test
	void testEnElCatalogoSePuedeBuscarProductosPorCategoria() {
	
	    productosDeseados.add(catalogoCorrientes.buscarProducto(2)); // Hardwar 
	    
	    buscador.setTipoDeBusqueda(new BusquedaPorCategoria("Hardwar", buscador));
	    
	    assertEquals(productosDeseados, catalogoCorrientes.buscarProductos());
	}
	
	@Test
	void testEnElCatalogoSePuedeBuscarProductosPorStock() {

	    productosDeseados.add(catalogoCorrientes.buscarProducto(1)); // stock = 1 
	    productosDeseados.add(catalogoCorrientes.buscarProducto(3)); // stock = 10
	    
	    buscador.setTipoDeBusqueda(new BusquedaPorDisponibilidad(buscador));
	    
	    assertEquals(productosDeseados, catalogoCorrientes.buscarProductos());
	}
	
	@Test
	void testEnElCatalogoSePuedeBuscarProductosQueNoTenganUnNombre() {
		
	    productosDeseados.add(catalogoCorrientes.buscarProducto(2)); // nombre = CPU
	    productosDeseados.add(catalogoCorrientes.buscarProducto(3)); // nombre = Mouse
	    
	    TipoDeBusqueda busquedaPorNombre = new BusquedaPorNombre("Monitor", buscador);
	    
	    buscador.setTipoDeBusqueda(new BusquedaPorNOT(busquedaPorNombre, buscador));
	    
	    assertEquals(productosDeseados, catalogoCorrientes.buscarProductos());
	}
	
	@Test
	void testEnElCatalogoSePuedeBuscarProductosQueNoTenganUnaCategoria() {
		
	    productosDeseados.add(catalogoCorrientes.buscarProducto(2)); // categoria = Hardwar
	    
	    TipoDeBusqueda busquedaPorCategoria = new BusquedaPorCategoria("Perifericos", buscador);
	    
	    buscador.setTipoDeBusqueda(new BusquedaPorNOT(busquedaPorCategoria, buscador));
	    
	    assertEquals(productosDeseados, catalogoCorrientes.buscarProductos());
	}
	
	@Test
	void testEnElCatalogoSePuedeBuscarProductosQueNoTenganPrecioMax() {
		
		productosDeseados.add(catalogoCorrientes.buscarProducto(1)); // Precio 100.1
	    productosDeseados.add(catalogoCorrientes.buscarProducto(3)); // Precio 101
	    
	    TipoDeBusqueda busquedaPorPrecioMax = new BusquedaPorPrecioMax(100, buscador);
	    
	    buscador.setTipoDeBusqueda(new BusquedaPorNOT(busquedaPorPrecioMax, buscador));
	    
	    assertEquals(productosDeseados, catalogoCorrientes.buscarProductos());
	}
	
	@Test
	void testEnElCatalogoSePuedeBuscarProductosQueNoTenganDisponibilidad() {
		
		productosDeseados.add(catalogoCorrientes.buscarProducto(2)); // Stock = 0
		productosDeseados.add(catalogoCorrientes.buscarProducto(4)); // Stock = 0
	    
	    TipoDeBusqueda busquedaPorDisponibilidad = new BusquedaPorDisponibilidad(buscador);
	    
	    buscador.setTipoDeBusqueda(new BusquedaPorNOT(busquedaPorDisponibilidad, buscador));
	    
	    assertEquals(productosDeseados, catalogoCorrientes.buscarProductos());
	}
	
	@Test
	void testEnElCatalogoSePuedeBuscarProductosQueCumplanConDosRequisitos() {
		
		productosDeseados.add(catalogoCorrientes.buscarProducto(4)); // Nombre = monitor y precio = 99
	    
		TipoDeBusqueda busquedaPorNombre = new BusquedaPorNombre("Monitor", buscador); 
	    TipoDeBusqueda busquedaPorPrecioMax = new BusquedaPorPrecioMax(100, buscador);
	    
	    buscador.setTipoDeBusqueda(new BusquedaPorAND(busquedaPorNombre, busquedaPorPrecioMax, buscador));
	    
	    assertEquals(productosDeseados, catalogoCorrientes.buscarProductos());
	}
	
	@Test
	void testEnElCatalogoSePuedeBuscarProductosQueCumplanConUnoDeLosDosRequisitos() {
		
		productosDeseados.add(catalogoCorrientes.buscarProducto(2)); // Categoria = Hardwar
		productosDeseados.add(catalogoCorrientes.buscarProducto(3)); // Nombre = Mouse
	    	
		TipoDeBusqueda busquedaPorCategoria = new BusquedaPorCategoria("Hardwar", buscador);
		TipoDeBusqueda busquedaPorNombre = new BusquedaPorNombre("Mouse", buscador); 
	    
	    buscador.setTipoDeBusqueda(new BusquedaPorOR(busquedaPorCategoria, busquedaPorNombre, buscador));
	    
	    assertEquals(productosDeseados, catalogoCorrientes.buscarProductos());
	}
	
	@Test
	void testEnElCatalogoSePuedeBuscarCombinandoLosComplejos() {
		
		productosDeseados.add(catalogoCorrientes.buscarProducto(2)); // 'CPU' && Hardwar
		productosDeseados.add(catalogoCorrientes.buscarProducto(3)); // 'Mouse' && Perifericos
	    	
		TipoDeBusqueda busquedaPorNombre = new BusquedaPorNombre("Monitor", buscador); 
		TipoDeBusqueda busquedaPorCategoria = new BusquedaPorCategoria("Perifericos", buscador);
		TipoDeBusqueda busquedaAND = new BusquedaPorAND(busquedaPorNombre, busquedaPorCategoria, buscador);
		
	    // Mi AND = ("Monitor" ^ Perifericos), si aplico De Morgan -> (~"Monitor" v ~Periferico)
		// Entonces busco algo que no cumpla que sea Monitor y Perifericos a la vez
	    buscador.setTipoDeBusqueda(new BusquedaPorNOT(busquedaAND, buscador));
	    
	    assertEquals(productosDeseados, catalogoCorrientes.buscarProductos());
	}
	
	@Test
	void testEnElCatalogoNoSePuedeBuscarSiNoSeSeleccionoElTipoDeBusqueda() {
		
		IllegalArgumentException error = assertThrows(IllegalArgumentException.class,() -> catalogoCorrientes.buscarProductos());
		
		 assertEquals("Se debe seleccionar un tipo de busqueda antes", error.getMessage());
	}
	
	
}
