package src;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Catalogo {
	
	private ArrayList<Producto> productos;
	private int ultimoSKU;
	
	public Catalogo() {
		this.productos = new ArrayList<Producto>();
		this.ultimoSKU = 0;
	}

	public void registrarIndividual(String nombre, String marca, String categoria, ArrayList<Atributo> atributos, float precio, int cantidad) {
		asertarQueNoTengaPrecioNegativo(precio);
		asertarQueNoTengaStockNegativo(cantidad);
		
		ultimoSKU++;
		Individuales productoNuevo = new Individuales(ultimoSKU, nombre, marca, categoria, atributos, precio, cantidad);
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

	public Boolean tieneProducto(String nombre) {
		int cantidadDeProdcutos = productos.size();
		for(int i=0; i < cantidadDeProdcutos ; i++) {
			if(productos.get(i).getNombre() == nombre) {
				return true;
			}
		}
		return false;
	}

	public int cantidadDe(int SKU) {
		return (buscarProducto(SKU)).getCantidad();
	}

	public int cantidadDeProductos() {
		return productos.size();
	}

	public void registrarPaquete(String nombre, String marca, String categoria, ArrayList<Atributo> atributos, ArrayList<Producto> productosQueIncluye, float precio, int descuento, int cantidad) {
		asertarQueNoTengaPrecioNegativo(precio);
		asertarQueNoTengaStockNegativo(cantidad);
		ultimoSKU++;
		Paquete productoNuevo = new Paquete(ultimoSKU, nombre, marca, categoria, atributos, productosQueIncluye, precio, descuento,cantidad);
		productos.add(productoNuevo);
	}

}
