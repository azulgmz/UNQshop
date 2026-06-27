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
		ultimoSKU++;
		Individuales productoNuevo = new Individuales(ultimoSKU, nombre, marca, categoria, atributos, precio, cantidad);
		productos.add(productoNuevo);
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

}
