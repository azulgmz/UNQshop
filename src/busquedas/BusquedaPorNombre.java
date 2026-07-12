package busquedas;

import java.util.List;
import java.util.stream.Stream;

import productos.Producto;
import sistemas.Catalogo;

public class BusquedaPorNombre implements TipoDeBusqueda {
	
	private String nombreABuscar;
	private Buscador buscador;

	public BusquedaPorNombre(String nombreABuscar, Buscador buscador) {
		this.nombreABuscar = nombreABuscar;
		this.buscador = buscador;
	}


	public List<Producto> buscarProductos(Catalogo catalogo) {
		Stream<Producto> productos = catalogo.getProductos().stream();
		
		buscador.setTipoDeBusqueda(new SinTipoDeBusquedaDefinido());
		return productos.filter(p -> p.getNombre().equalsIgnoreCase(nombreABuscar)).toList();
	}

}
