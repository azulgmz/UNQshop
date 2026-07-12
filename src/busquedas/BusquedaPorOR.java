package busquedas;

import java.util.ArrayList;
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
			buscador.setTipoDeBusqueda(busqueda1);					           			     // Cambio la busqueda por el primero 
			List<Producto> productosBusqueda1 = new ArrayList<>(catalogo.buscarProductos()); // Los guardo en la primera lista la 
																							 // cual vamos a añadir los que faltan
			
			buscador.setTipoDeBusqueda(busqueda2);					              // Cambio la busqueda por la segunda 
			List<Producto> productosBusqueda2 = catalogo.buscarProductos();   // Los guardo en una lista
			
				for (Producto p : productosBusqueda2) {      // Itero sobre los prodcutos de la segunda busqueda
					if(!(productosBusqueda1.contains(p))) {  // Si no están en la primera busqueda, los agrego
						productosBusqueda1.add(p);			 // De esta forma evito repetidos y hago una union de  
					}									     // ambas listas
				}
			buscador.setTipoDeBusqueda(new SinTipoDeBusquedaDefinido());
			return productosBusqueda1;
	}
	
}