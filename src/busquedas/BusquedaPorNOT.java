package busquedas;

import java.util.ArrayList;
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
			List<Producto> productos = new ArrayList<>(catalogo.getProductos()); // Todos los productos del catalogo
			buscador.setTipoDeBusqueda(busqueda);								 // Cambio la busqueda por el tipo al que no queremos 
			List<Producto> productosNOT = catalogo.buscarProductos();			 // Todos esos prodcutos que no queremos
			
			productos.removeAll(productosNOT);
			
			buscador.setTipoDeBusqueda(new SinTipoDeBusquedaDefinido());
			return productos; // Devolvemos los productos que no cumplan con aquello
	}

}
