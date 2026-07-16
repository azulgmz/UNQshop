package NotaDeCredito;

import java.time.LocalDate;
import java.util.ArrayList;

import pedido.Pedido;
import productos.Producto;

public abstract class NotaDeCredito {
	
	private Pedido    pedido;
	private LocalDate fecha;
	private int       CUIT, nroDeNota;
	
	public NotaDeCredito(int nroDeNota, Pedido pedido, LocalDate fecha, int CUIT) {
		this.nroDeNota = nroDeNota;
		this.pedido    = pedido;
		this.fecha     = fecha;
		this.CUIT      = CUIT;
	}
	
	public int getCUIT() {
		return CUIT;
	}
	
	public Pedido getPedido() {
		return pedido;
	}
	
	public LocalDate getFecha() {
		return fecha;
	}
	
	protected  String detallesDatos() { // Solo quiero que acceden los hijos, es para armar la nota total
		String detallesDatos = "";
		
		detallesDatos += "NOTA DE CREDITO DE " + CUIT + "   NUMERO: " + nroDeNota + ".\n";
		detallesDatos += "Cliente: " + pedido.getMail() + ".\n";
		detallesDatos += "Fecha: " + fecha + ".\n";
		
		return detallesDatos;
	}
	
	protected String detallesProducto() { // Solo quiero que acceden los hijos, es para armar la nota total
		String detalles = "";
		ArrayList<Producto> productos = getPedido().getListaDeProductos();
		
		for(Producto p : productos) {
			detalles += p.getNombre() + ": " + p.precioFinal() + ".\n";
		}
		
		return detalles;
		
	}

	public int getNroDeNota() {
		return nroDeNota;
	}

	public abstract String detalles();
	

}
