package busquedas;

import java.util.List;

import productos.Producto;
import sistemas.Catalogo;

public class BuscarPorNombre implements TipoDeBusqueda {
	
	private String nombreABuscar;
	private Buscador buscador;

	public BuscarPorNombre(String nombreABuscar, Buscador buscador) {
		this.nombreABuscar = nombreABuscar;
		this.buscador = buscador;
	}


	public List<Producto> buscarProductos(Catalogo catalogo) {
		return buscador.buscarPorNombre(nombreABuscar, catalogo);
	}

}
