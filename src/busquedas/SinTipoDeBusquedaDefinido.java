package busquedas;

import java.util.List;

import productos.Producto;
import sistemas.Catalogo;

public class SinTipoDeBusquedaDefinido implements TipoDeBusqueda {
	
	public SinTipoDeBusquedaDefinido() {}

	public List<Producto> buscarProductos(Catalogo catalogo) {
		throw new IllegalArgumentException("Se debe seleccionar un tipo de busqueda antes");
	}

}
