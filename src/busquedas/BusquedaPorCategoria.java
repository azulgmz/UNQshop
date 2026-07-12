package busquedas;

import java.util.List;
import java.util.stream.Stream;

import productos.Producto;
import sistemas.Catalogo;

public class BusquedaPorCategoria implements TipoDeBusqueda {

	private String categoriaABuscar;
	private Buscador buscador;

	public BusquedaPorCategoria(String categoriaABuscar, Buscador buscador) {
		this.categoriaABuscar = categoriaABuscar;
		this.buscador = buscador;
	}

	public List<Producto> buscarProductos(Catalogo catalogo) {
		Stream<Producto> productos = catalogo.getProductos().stream();
		
		buscador.setTipoDeBusqueda(new SinTipoDeBusquedaDefinido());
		return productos.filter(p -> p.getCategoria().equals(categoriaABuscar)).toList();
	}

}
