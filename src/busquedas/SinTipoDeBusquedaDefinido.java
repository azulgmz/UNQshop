package busquedas;

import java.util.List;

import productos.Producto;
import sistemas.Catalogo;

public class SinTipoDeBusquedaDefinido implements TipoDeBusqueda {

	private Buscador buscador;
	
	public SinTipoDeBusquedaDefinido(Buscador buscador) {
		this.buscador = buscador;
	}

	public List<Producto> buscarProductos(Catalogo catalogo) {
		return buscador.errorPorSinBusqueda();
	}

}
