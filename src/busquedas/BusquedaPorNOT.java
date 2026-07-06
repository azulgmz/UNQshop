package busquedas;

import java.util.List;

import productos.Producto;
import sistemas.Catalogo;

public class BusquedaPorNOT implements TipoDeBusqueda {
	
	private TipoDeBusqueda busqueda;
	private Buscador buscador;

	public BusquedaPorNOT(TipoDeBusqueda busqueda, Buscador buscador) {
		this.busqueda = busqueda;
		this.buscador = buscador;
	}

	public List<Producto> buscarProductos(Catalogo catalogo) {
	
		return buscador.buscarPorNOT(busqueda, catalogo);
	}

}
