package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import busquedas.Buscador;
import busquedas.BuscarPorNombre;
import busquedas.BusquedaPorPrecioMax;
import productos.Atributo;
import productos.Producto;
import sistemas.Catalogo;

class busquedaTestCase {

	private Catalogo		    catalogoCorrientes;
	private ArrayList<Atributo> atributosDummy;
	private List<Producto>      productosDeseados;
	
	@BeforeEach
	void setUp() {
		catalogoCorrientes = new Catalogo();
		atributosDummy     = new ArrayList<>();
		productosDeseados  = new ArrayList<>();
		
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
	    
	    Buscador buscador =  catalogoCorrientes.getBuscador();
	    
	    buscador.setTipoDeBusqueda(new BuscarPorNombre("Monitor", buscador));
	    
	    assertEquals(productosDeseados, catalogoCorrientes.buscarProductos());
	}
	
	@Test
	void testEnElCatalogoSePuedeBuscarProductosPorPrecioMaximo() {
		
		
	    productosDeseados.add(catalogoCorrientes.buscarProducto(2)); // Precio 100
	    productosDeseados.add(catalogoCorrientes.buscarProducto(4)); // Precio 99
	    
	    Buscador buscador =  catalogoCorrientes.getBuscador();
	    
	    buscador.setTipoDeBusqueda(new BusquedaPorPrecioMax(100, buscador));
	    
	    assertEquals(productosDeseados, catalogoCorrientes.buscarProductos());
	}

}
