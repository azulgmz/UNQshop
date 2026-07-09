package busquedas;

import java.util.List;

import productos.Producto;
import sistemas.Catalogo;

public class BusquedaPorAND implements TipoDeBusqueda {
	
	private TipoDeBusqueda busqueda1, busqueda2;
	private Buscador buscador;

	public BusquedaPorAND(TipoDeBusqueda busqueda1, TipoDeBusqueda busqueda2, Buscador buscador) {
		this.busqueda1 = busqueda1;
		this.busqueda2 = busqueda2;
		this.buscador = buscador;
	}

	public List<Producto> buscarProductos(Catalogo catalogo) {
		return buscador.buscarPorAND(busqueda1, busqueda2, catalogo);
	}

}
