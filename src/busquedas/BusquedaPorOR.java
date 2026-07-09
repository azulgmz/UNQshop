package busquedas;

import java.util.List;

import productos.Producto;
import sistemas.Catalogo;

public class BusquedaPorOR implements TipoDeBusqueda {

	private TipoDeBusqueda busqueda1, busqueda2;
	private Buscador buscador;
	
	public BusquedaPorOR(TipoDeBusqueda busqueda1, TipoDeBusqueda busqueda2, Buscador buscador) {
		this.busqueda1 = busqueda1;
		this.busqueda2 = busqueda2;
		this.buscador = buscador;
	}
	
	public List<Producto> buscarProductos(Catalogo catalogo) {
		return buscador.buscarPorOR(busqueda1, busqueda2, catalogo);
	}
	
}