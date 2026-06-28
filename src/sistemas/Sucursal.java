package sistemas;

import java.util.ArrayList;

import pedido.Borrador;
import pedido.EstadoDelPedido;
import pedido.Pedido;
import productos.Producto;

public class Sucursal {
	
	private int CUIT;
	private Catalogo catalogo;
	private float dineroDisponible;
	private String direccion;
	private ArrayList<Pedido> pedidosActivos;

	public Sucursal(int CUIT, Catalogo catalogo, float dineroDisponible, String direccion) {
		this.CUIT = CUIT;
		this.catalogo = catalogo;
		this.dineroDisponible = dineroDisponible; 
		this.direccion = direccion;
		this.pedidosActivos = new ArrayList<>();
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

	public String getDireccion() {
		return direccion;
	}

	public Pedido crearPedido(String mail, String direccion) {
		EstadoDelPedido estadoActual = new Borrador();
		ArrayList<Producto> listaDeProductos = new ArrayList<>();
		Pedido pedido = new Pedido(this, estadoActual, listaDeProductos, mail, direccion);
		
		pedidosActivos.add(pedido);
		
		return pedido;
	}

	public Boolean tienePedidoActivo(Pedido pedido) {
		return pedidosActivos.contains(pedido);
	}

	public Catalogo getCatalogo() {
		return catalogo;
	}

}
