package productos;

import java.util.ArrayList;

public class Paquete extends Producto {
	
	private  ArrayList<Producto> productosQueIncluye;
	private int descuento;

	public Paquete(int SKU, String nombre, String marca, String categoria, ArrayList<Atributo> atributos, ArrayList<Producto> productosQueIncluye,float precio, int descuento,
			int cantidad) {
		super(SKU, nombre, marca, categoria, atributos, precio, cantidad);
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
		int cantidadDeProductos = productosQueIncluye.size(); 
		
		for (int i=0; i < cantidadDeProductos; i++) {
			precioTotal += productosQueIncluye.get(i).precioFinal();
		}
		if (descuento == 100) {
			return precioTotal;
		}
		return precioTotal *= (1 - (descuento/100f));
	}

}