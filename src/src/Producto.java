package src;

import java.util.ArrayList;

public abstract class Producto {

	private int SKU, cantidad;
	private String nombre, marca, categoria;
	private float precio;
	private ArrayList<Atributo> atributos;
	
	
	public Producto(int SKU, String nombre, String marca, String categoria, ArrayList<Atributo> atributos, float precio, int cantidad) { 
		this.SKU = SKU;
		this.nombre = nombre;
		this.marca = marca;
		this.categoria = categoria;
		this.atributos = atributos;
		this.precio = precio;
		this.cantidad = cantidad;
	}


	public int getSKU() {
		return SKU;
	}


	public String getNombre() {
		return nombre;
	}


	public String getMarca() {
		return marca;
	}


	public String getCategoria() {
		return categoria;
	}
	
	public ArrayList<Atributo> getAtributos() {
		return atributos;
	}


	public float getPrecio() {
		return precio;
	}


	public int getCantidad() {
		return cantidad;
	}
	
	public abstract float precioFinal();
	
	public abstract String getDescripcion();
}
