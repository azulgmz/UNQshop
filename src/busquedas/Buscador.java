package busquedas;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import productos.Producto;
import sistemas.Catalogo;

public class Buscador {
	
	private TipoDeBusqueda tipoDeBusqueda;
	
	public Buscador() {
		this.tipoDeBusqueda = new SinTipoDeBusquedaDefinido();
	}

	public void setTipoDeBusqueda(TipoDeBusqueda nuevoTipo) {
		tipoDeBusqueda = nuevoTipo;
	}

	public TipoDeBusqueda getTipoDeBusqueda() {
		return tipoDeBusqueda;
	}

	public List<Producto> buscarPorNombre(String nombreABuscar, Catalogo catalogo) {
		
		Stream<Producto> productos = convertirAStream(catalogo);
		
		return productos.filter(p -> p.getNombre().equalsIgnoreCase(nombreABuscar)).toList();
	}

	private Stream<Producto> convertirAStream(Catalogo catalogo) {
		return catalogo.getProductos().stream();
	}

	public List<Producto> buscarPorPrecioMax(float precioMax, Catalogo catalogo) {
		Stream<Producto> productos = convertirAStream(catalogo);
		
		return productos.filter(p -> p.precioFinal() <= precioMax).toList();
	}

	public List<Producto> buscarPorCategoria(String categoriaABuscar, Catalogo catalogo) {
		Stream<Producto> productos = convertirAStream(catalogo);
		
		return productos.filter(p -> p.getCategoria().equals(categoriaABuscar)).toList();
	}

	public List<Producto> buscarPorDisponibilidad(Catalogo catalogo) {
		Stream<Producto> productos = convertirAStream(catalogo);
		
		return productos.filter(p -> p.getCantidad() > 0).toList();
	}

	public List<Producto> buscarPorNOT(TipoDeBusqueda busqueda, Catalogo catalogo) {
		List<Producto> productos = new ArrayList<>(catalogo.getProductos()); 		// Todos los productos del catalogo
		this.setTipoDeBusqueda(busqueda);											// Cambio la busqueda por el tipo al que no queremos 
		List<Producto> productosNOT = catalogo.buscarProductos();					// Todos esos prodcutos que no queremos
		
		productos.removeAll(productosNOT);
		
		return productos; // Devolvemos los productos que no cumplan con aquello
	}

	public List<Producto> buscarPorAND(TipoDeBusqueda busqueda1, TipoDeBusqueda busqueda2, Catalogo catalogo) {
		this.setTipoDeBusqueda(busqueda1);					              // Cambio la busqueda por el primero 
		List<Producto> productosBusqueda1 = catalogo.buscarProductos();   // Los guardo en una lista        
		
		this.setTipoDeBusqueda(busqueda2);					              // Cambio la busqueda por la segunda 
		List<Producto> productosBusqueda2 = catalogo.buscarProductos();   // Los guardo en una lista
		
		
		List<Producto> resultado = new ArrayList<>();
		if(productosBusqueda1.size() <= productosBusqueda2.size()) {
			for (Producto p : productosBusqueda1) {
				if(productosBusqueda2.contains(p)) {
					resultado.add(p);
				}
			}
			return resultado;
		}
		for (Producto p : productosBusqueda2) {
			if(productosBusqueda1.contains(p)) {
				resultado.add(p);
			}
		}
		return resultado;
		
	}

	
}
