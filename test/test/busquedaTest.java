package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import busquedas.Buscador;
import busquedas.BuscarPorNombre;
import productos.Atributo;
import productos.Producto;
import sistemas.Catalogo;

class busquedaTest {

	private Catalogo catalogoCorrientes;
	private ArrayList<Atributo> atributosDummy;
	private List<Producto> productosDeseados;
	
	@BeforeEach
	void setUp() {
		catalogoCorrientes = new Catalogo();
		atributosDummy     = new ArrayList<>();
		productosDeseados = new ArrayList<>();
	}
	
	@Test
	void testEnElCatalogoSePuedeBuscarProductosPorNombre() {
		
		// Ejemplo buscando Monitor, deberia devolver el monitor snapdragon y LG, porque no debe hacer distinciones
		
		
		
		catalogoCorrientes.registrarIndividual("Monitor", "Snapdragon", "Perifericos", atributosDummy, 8900f, 100); // SKU = 1
	    catalogoCorrientes.registrarIndividual("CPU", "Snapdragon", "Hardwar", atributosDummy, 10000f, 100);        // SKU = 2
	    catalogoCorrientes.registrarIndividual("Mouse", "Snapdragon", "Perifericos", atributosDummy, 10000f, 100);  // SKU = 3
	    catalogoCorrientes.registrarIndividual("monitor", "LG", "Perifericos", atributosDummy, 15000f, 100);        // SKU = 4
		
	    productosDeseados.add(catalogoCorrientes.buscarProducto(1)); // 'Monitor'
	    productosDeseados.add(catalogoCorrientes.buscarProducto(4)); // 'monitor'
	    
	    Buscador buscador =  catalogoCorrientes.getBuscador();
	    
	    buscador.setTipoDeBusqueda(new BuscarPorNombre("Monitor", buscador));
	    
	    assertEquals(productosDeseados, catalogoCorrientes.buscarProductos());
	}

}
