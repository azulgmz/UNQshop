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

	public Object getDireccion() {
		return direccion;
	}

	public Boolean estaEnEstadoBorrador() {
		return estadoActual.estaEnEstadoBorrador();
	}

}
