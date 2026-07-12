package busquedas;

import java.util.List;
import java.util.stream.Stream;

import productos.Producto;
import sistemas.Catalogo;

public class BusquedaPorPrecioMax implements TipoDeBusqueda {
		
		private float precioMax;
		private Buscador buscador;

		public BusquedaPorPrecioMax(float precioMax, Buscador buscador) {
			this.precioMax = precioMax;
			this.buscador = buscador;
		}

	@Override
	public List<Producto> buscarProductos(Catalogo catalogo) {
			Stream<Producto> productos = catalogo.getProductos().stream();
			
			buscador.setTipoDeBusqueda(new SinTipoDeBusquedaDefinido());
			return productos.filter(p -> p.precioFinal() <= precioMax).toList();
	}

}
