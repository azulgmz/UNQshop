package sistemas;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import busquedas.Buscador;
import productos.Atributo;
import productos.Individual;
import productos.Paquete;
import productos.Producto;

public class Catalogo {
	
	private ArrayList<Producto> productos;
	private int ultimoSKU;
	private Buscador buscador;
	
	public Catalogo() {
		this.productos = new ArrayList<Producto>();
		this.ultimoSKU = 0;
		this.buscador = new Buscador();
	}

	public void registrarIndividual(String nombre, String marca, String categoria, ArrayList<Atributo> atributos, float precio, int cantidad, float peso) {
		asertarQueNoTengaPrecioNegativo(precio);
		asertarQueNoTengaStockNegativo(cantidad);
		
		ultimoSKU++;
		Individual productoNuevo = new Individual(ultimoSKU, nombre, marca, categoria, atributos, precio, cantidad, peso);
		productos.add(productoNuevo);
	}

	private void asertarQueNoTengaStockNegativo(int cantidad) {
		if(cantidad < 0) {
			throw new IllegalArgumentException("El stock no puede ser negativo");
		}
	}

	private void asertarQueNoTengaPrecioNegativo(float precio) {
		if(precio < 0) {
			throw new IllegalArgumentException("El precio no puede ser negativo");
		}
	}

	public Producto buscarProducto(int SKU) {
		if (SKU < 0) {
		    throw new IllegalArgumentException("El SKU no puede ser negativo");
		}
		int cantidadDeProdcutos = productos.size();
		for(int i=0; i < cantidadDeProdcutos ; i++) {
			if(productos.get(i).getSKU() == SKU) {
				return productos.get(i);
			}
		}
		throw new NoSuchElementException("No existe un producto con SKU " + SKU);
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

	public void registrarPaquete(String nombre, String categoria, ArrayList<Atributo> atributos, ArrayList<Producto> productosQueIncluye, int descuento, int cantidad) {
		asertarQueNoTengaStockNegativo(cantidad);
		asertarQueTengaAlmenosUnProducto(productosQueIncluye);
		
		ultimoSKU++;
		Paquete productoNuevo = new Paquete(ultimoSKU, nombre, categoria, atributos, productosQueIncluye, descuento, cantidad);
		productos.add(productoNuevo);
	}

	private void asertarQueTengaAlmenosUnProducto(ArrayList<Producto> productosQueIncluye) {
		if(productosQueIncluye.isEmpty()) {
			throw new IllegalArgumentException("El paquete debe tener al menos un producto");
		}
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



}
