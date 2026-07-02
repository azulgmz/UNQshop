package busquedas;

import java.util.List;

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
		return buscador.buscarPorCategoria(categoriaABuscar, catalogo);
	}

}
