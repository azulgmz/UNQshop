package src;

import java.util.ArrayList;

public class Individuales extends Producto {

	public Individuales(int SKU, String nombre, String marca, String categoria, ArrayList<Atributo> atributos, float precio, int cantidad) {
		super(SKU, nombre, marca, categoria, atributos, precio, cantidad);
	}

	public String getDescripcion() {
		String descripcion = "";
		ArrayList<Atributo> atributos = getAtributos();
		int cantidadDeAtributos = getAtributos().size();
		for (int i=0; i < cantidadDeAtributos; i++){
			descripcion += atributos.get(i).getTipo() + ": " + atributos.get(i).getEspecificacion() + ".\n";
		}
		System.out.println(descripcion);
		return descripcion;
	}

	public float precioBase() {
		return getPrecio();
	}
	

}
