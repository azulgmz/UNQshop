package busquedas;

import java.util.List;

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
		return buscador.buscarPorPrecioMax(precioMax, catalogo);
	}

}
