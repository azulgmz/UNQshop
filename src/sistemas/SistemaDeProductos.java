package sistemas;

import java.util.ArrayList;

import productos.Atributo;
import productos.Individual;
import productos.Paquete;
import productos.Producto;

public class SistemaDeProductos {
	
	private int ultimoSKU;
	private ArrayList<Sucursal> sucursales;
	
	public SistemaDeProductos() {
		this.ultimoSKU  = 0;
		this.sucursales = new ArrayList<Sucursal>();
	}

	public void agregarSucursal(Sucursal sucursal) {
		sucursales.add(sucursal);
	}

	public void registrarIndividual(String nombre, String marca, String categoria, ArrayList<Atributo> atributos, float precio, int cantidad, float peso) {
		asertarQueNoTengaPrecioNegativo(precio);
		asertarQueNoTengaStockNegativo(cantidad);
		
		ultimoSKU++;
		Individual productoNuevo = new Individual(ultimoSKU, nombre, marca, categoria, atributos, precio, cantidad, peso);
		
		agregarProducto(productoNuevo);
	}
	
	private void agregarProducto(Producto productoNuevo) {
		for(Sucursal s : sucursales) {
			s.getCatalogo().agregarProducto(productoNuevo);
		}
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
	
	public void registrarPaquete(String nombre, String categoria, ArrayList<Atributo> atributos, ArrayList<Producto> productosQueIncluye, int descuento, int cantidad) {
		asertarQueNoTengaStockNegativo(cantidad);
		asertarQueTengaAlmenosUnProducto(productosQueIncluye);
		
		ultimoSKU++;
		Paquete productoNuevo = new Paquete(ultimoSKU, nombre, categoria, atributos, productosQueIncluye, descuento, cantidad);
		agregarProducto(productoNuevo);
	}

	private void asertarQueTengaAlmenosUnProducto(ArrayList<Producto> productosQueIncluye) {
		if(productosQueIncluye.isEmpty()) {
			throw new IllegalArgumentException("El paquete debe tener al menos un producto");
		}
	}
}
