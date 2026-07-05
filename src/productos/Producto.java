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
