package productos;

import java.util.ArrayList;

public class Individual extends Producto {

	private String marca;
	private float precio;
	
	public Individual(int SKU, String nombre, String marca, String categoria, ArrayList<Atributo> atributos, float precio, int cantidad) {
		super(SKU, nombre, categoria, atributos, cantidad);
		this.marca = marca;
		this.precio = precio;
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

	public float precioFinal() {
		return precio;
	}
	

}
