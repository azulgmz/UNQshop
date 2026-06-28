package pedido;

import java.util.ArrayList;
import productos.Producto;
import sistemas.Sucursal;

public class Pedido {

	private Sucursal            sucursal;
	private EstadoDelPedido     estadoActual;
	private ArrayList<Producto> listaDeProductos;
	private String              mail;
	private String              direccion;
	
	public Pedido(Sucursal sucursal, EstadoDelPedido estadoActual, ArrayList<Producto> listaDeProductos, String mail, String direccion) {
		this.sucursal         = sucursal;
		this.estadoActual     = estadoActual;
		this.listaDeProductos = listaDeProductos;
		this.mail             = mail;
		this.direccion        = direccion;
	}

	public String getMail() {
		return mail;
	}

	public String getDireccion() {
		return direccion;
	}

	public Boolean estaEnEstadoBorrador() {
		return estadoActual.estaEnEstadoBorrador();
	}

	public void agregarProducto(Producto producto) {
		sucursal.getCatalogo().descontarStockDe(producto);
		listaDeProductos.add(producto);
	}

	public Boolean agregoA(int SKU) {
		int cantidadDeProductos = listaDeProductos.size();
		for (int i=0; i < cantidadDeProductos; i++) {
			if(listaDeProductos.get(i).getSKU() == SKU) {
				return true;
			}
		}
		return false;
	}

}
