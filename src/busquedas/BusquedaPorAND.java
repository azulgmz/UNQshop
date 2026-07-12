package busquedas;

import java.util.ArrayList;
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
		buscador.setTipoDeBusqueda(busqueda1);					          // Cambio la busqueda por el primero 
		List<Producto> productosBusqueda1 = catalogo.buscarProductos();   // Los guardo en una lista        
		
		buscador.setTipoDeBusqueda(busqueda2);					          // Cambio la busqueda por la segunda 
		List<Producto> productosBusqueda2 = catalogo.buscarProductos();   // Los guardo en una lista
		
		
		List<Producto> resultado = new ArrayList<>();  // Creo una lista en la cual añadire los productos que cumplan 
		
			for (Producto p : productosBusqueda1) {	  // Itero sobre la primer lista
				if(productosBusqueda2.contains(p)) {  // Si esta el producto en la segunda lista
					resultado.add(p);                 // Lo agrego al resultado
				}
			}
			
			buscador.setTipoDeBusqueda(new SinTipoDeBusquedaDefinido());
			return resultado;
	}

}
