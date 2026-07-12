package busquedas;

import java.util.List;
import java.util.stream.Stream;

import productos.Producto;
import sistemas.Catalogo;

public class BusquedaPorDisponibilidad implements TipoDeBusqueda {

	private Buscador buscador;

	public BusquedaPorDisponibilidad(Buscador buscador) {
		this.buscador = buscador;
	}

	public List<Producto> buscarProductos(Catalogo catalogo) {
		Stream<Producto> productos = catalogo.getProductos().stream();
			
		buscador.setTipoDeBusqueda(new SinTipoDeBusquedaDefinido());
		return productos.filter(p -> p.getCantidad() > 0).toList();
	}

}
