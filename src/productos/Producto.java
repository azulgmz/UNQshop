package productos;

import java.util.ArrayList;

public abstract class Producto {

	private int SKU, cantidad;
	private String nombre, categoria;
	private ArrayList<Atributo> atributos;
	
	
	public Producto(int SKU, String nombre, String categoria, ArrayList<Atributo> atributos, int cantidad) { 
		this.SKU = SKU;
		this.nombre = nombre;
		this.categoria = categoria;
		this.atributos = atributos;
		this.cantidad = cantidad;
	}

	public boolean equals(Object obj) {
		if (this == obj) { // Si son el mismo objeto en memoria devuelve true
			return true;
		}
		
		if(obj == null || getClass() != obj.getClass()) { // Si el otro objeto es null o es otra clase,
			return false;                                 // Devuelve false
		}
		
		Producto otro = (Producto) obj; // Son la misma clase
		
		return this.SKU == otro.getSKU(); // Hace la igualdad segun su SKU
	}
	
	@Override
	public int hashCode() {
	    return Integer.hashCode(SKU);
	}
	
	
	
	public int getSKU() {
		return SKU;
	}


	public String getNombre() {
		return nombre;
	}

	public String getCategoria() {
		return categoria;
	}
	
	public ArrayList<Atributo> getAtributos() {
		return atributos;
	}

	public int getCantidad() {
		return cantidad;
	}
	
	public abstract float precioFinal();
	
	public abstract String getDescripcion();

	public void descontarUno() {
		this.cantidad--;
	}

	public void aumentarUno() {
		this.cantidad++;
	}
	
}
