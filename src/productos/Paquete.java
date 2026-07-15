package productos;

import java.util.ArrayList;

public class Paquete extends Producto {
	
	private  ArrayList<Producto> productosQueIncluye;
	private int descuento;

	public Paquete(int SKU, String nombre, String categoria, ArrayList<Atributo> atributos, ArrayList<Producto> productosQueIncluye, int descuento, int cantidad) {
		super(SKU, nombre, categoria, atributos, cantidad);
		this.productosQueIncluye = productosQueIncluye;
		this.descuento = descuento;
	}

	public String getDescripcion() {
		String descripcion = "";
		int cantidadDeProductos = productosQueIncluye.size();
		
		for (int i=0; i < cantidadDeProductos; i++) {
			descripcion += productosQueIncluye.get(i).getDescripcion();
		}
		System.out.println(descripcion);
		return descripcion;
	}
	
	public float precioFinal() {
		float precioTotal = 0;
		
		if (descuento == 100) {
			return precioTotal;
		}
		
		int cantidadDeProductos = productosQueIncluye.size(); 
		
		for (int i=0; i < cantidadDeProductos; i++) {
			precioTotal += productosQueIncluye.get(i).precioFinal();
		}
		
		return precioTotal *= (1 - (descuento/100f));
	}

	public float getPeso() {
		float pesoTotal = 0;
		int cantidadDeProductos = productosQueIncluye.size(); 
		
		for (int i=0; i < cantidadDeProductos; i++) {
			pesoTotal += productosQueIncluye.get(i).getPeso();
		}
		return pesoTotal;
	}
	

	public Boolean tieneProducto(Producto producto) {
		return productosQueIncluye.contains(producto);
	}

}