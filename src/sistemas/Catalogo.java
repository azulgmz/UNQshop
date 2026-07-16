package sistemas;

import java.util.ArrayList;
import java.util.List;

import busquedas.Buscador;
import productos.Producto;

public class Catalogo {
	
	private ArrayList<Producto> productos;
	private Buscador buscador;
	
	public Catalogo() {
		this.productos = new ArrayList<Producto>();
		this.buscador = new Buscador();
	}

	public Producto buscarProducto(int SKU) {
		if (SKU < 0) {
		    throw new IllegalArgumentException("El SKU no puede ser negativo");
		}
		if(cantidadDeProductos() == 0) {
			throw new IllegalArgumentException("No existe un producto con SKU " + SKU);
		}
		
		for(int i=0; i < cantidadDeProductos() ; i++) {
			if(productos.get(i).getSKU() == SKU) {
				return productos.get(i);
			}
		}
		throw new IllegalArgumentException("No existe un producto con SKU " + SKU);
	}

	public Boolean tieneProducto(int SKU) {
		return productos.stream().anyMatch(p -> p.getSKU() == SKU);
	}

	public int cantidadDe(int SKU) {
		return (buscarProducto(SKU)).getCantidad();
	}

	public int cantidadDeProductos() {
		return productos.size();
	}


	public void descontarStockDe(Producto producto) {
		producto.descontarUno();
	}

	private void asertarQueNoSePuedeDescontarUnStockQueEs0(Producto producto) {
		if(producto.getCantidad() == 0) {
			throw new IllegalArgumentException("No hay stock de " + producto.getNombre() + ".");
		}
	}

	public void tieneStockDe(Producto producto) {
		asertarQueNoSePuedeDescontarUnStockQueEs0(producto);
	}

	public void devolverStock(ArrayList<Producto> listaDeProductos) {
		int cantidad = listaDeProductos.size();
		for(int i=0; i < cantidad; i++) {
			sumarProducto(listaDeProductos.get(i));
		}
	}

	private void sumarProducto(Producto producto) {
		int cantidadDeProductos = productos.size();
		for(int i=0; i < cantidadDeProductos ; i++) {
			if(productos.get(i).getSKU() == producto.getSKU()) {
				producto.aumentarUno();
			}
		}
	}

	public Buscador getBuscador() {
		return buscador;
	}

	public List<Producto> buscarProductos() {
		return buscador.getTipoDeBusqueda().buscarProductos(this);
	}

	public ArrayList<Producto> getProductos() {
		return productos;
	}

	public void agregarProducto(Producto productoNuevo) {
		productos.add(productoNuevo);
	}



}
