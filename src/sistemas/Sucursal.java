package sistemas;

import java.util.ArrayList;

import pedido.Pedido;
import productos.Producto;
import ubicacionGeografica.Direccion;

public class Sucursal {
	
	private int CUIT;
	private Catalogo catalogo;
	private float dineroDisponible;
	private Direccion direccion;
	private ArrayList<Pedido> pedidosActivos;
	private ArrayList<Sucursal> sucursales;

	public Sucursal(int CUIT, Catalogo catalogo, float dineroDisponible, Direccion direccion, ArrayList<Sucursal> sucursales) {
		this.CUIT = CUIT;
		this.catalogo = catalogo;
		this.dineroDisponible = dineroDisponible; 
		this.direccion = direccion;
		this.pedidosActivos = new ArrayList<>();
		this.sucursales = sucursales;
	}

	public int getCUIT() {
		return CUIT;
	}

	public Boolean tieneCatalogo() {
		return catalogo != null;
	}

	public float getDineroDisponible() {
		return dineroDisponible;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public Pedido crearPedido(String mail, Direccion direccion) {
		ArrayList<Producto> listaDeProductos = new ArrayList<>();
		Pedido pedido = new Pedido(this, listaDeProductos, mail, direccion);
		
		pedidosActivos.add(pedido);
		
		return pedido;
	}

	public Boolean tienePedidoActivo(Pedido pedido) {
		return pedidosActivos.contains(pedido);
	}

	public Catalogo getCatalogo() {
		return catalogo;
	}

	public void eliminarPedidoActivo(Pedido pedido) {
		pedidosActivos.remove(pedido);
	}

}
