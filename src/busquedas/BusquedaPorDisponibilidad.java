package busquedas;

import java.util.List;

import productos.Producto;
import sistemas.Catalogo;

public class BusquedaPorDisponibilidad implements TipoDeBusqueda {

	private Buscador buscador;

	public BusquedaPorDisponibilidad(Buscador buscador) {
		this.buscador = buscador;
	}

	public List<Producto> buscarProductos(Catalogo catalogo) {
		return buscador.buscarPorDisponibilidad(catalogo);
	}

}
