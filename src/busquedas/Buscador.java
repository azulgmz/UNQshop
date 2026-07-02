package busquedas;

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
		
		return productos.filter(p -> p.getPrecio() <= precioMax).toList();
	}

	public List<Producto> buscarPorCategoria(String categoriaABuscar, Catalogo catalogo) {
		Stream<Producto> productos = convertirAStream(catalogo);
		
		return productos.filter(p -> p.getCategoria().equals(categoriaABuscar)).toList();
	}

	public List<Producto> buscarPorDisponibilidad(Catalogo catalogo) {
		Stream<Producto> productos = convertirAStream(catalogo);
		
		return productos.filter(p -> p.getCantidad() > 0).toList();
	}
	
}
