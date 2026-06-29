package pedido;

import java.util.ArrayList;

import envios.Envio;
import envios.SinEnvioDefinido;
import metodosDePago.MetodoDePago;
import metodosDePago.SinMetodoDePagoDefinido;
import productos.Producto;
import sistemas.Sucursal;

public class Pedido {

	private Sucursal            sucursal;
	private EstadoDelPedido     estadoActual;
	private ArrayList<Producto> listaDeProductos;
	private String              mail;
	private String              direccion;
	private MetodoDePago        metodoDePago;
	private Envio               envio;
	
	public Pedido(Sucursal sucursal, EstadoDelPedido estadoActual, ArrayList<Producto> listaDeProductos, String mail, String direccion) {
		this.sucursal         = sucursal;
		this.estadoActual     = estadoActual;
		this.listaDeProductos = listaDeProductos;
		this.mail             = mail;
		this.direccion        = direccion;
		this.metodoDePago     = new SinMetodoDePagoDefinido();
	    this.envio            = new SinEnvioDefinido();
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
		sucursal.getCatalogo().tieneStockDe(producto);
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

	public void eliminarProducto(Producto producto) {
		listaDeProductos.remove(producto);
	}

	public void confirmarPedido(MetodoDePago metodoDePago, Envio envio) {
		this.metodoDePago = metodoDePago;
		this.envio = envio;
		
		sucursal.getCatalogo().descontarStock(listaDeProductos);
		
		this.estadoActual = new Confirmado();
	}

	public Boolean estaSinDefinirMetodoDePago() {
		return metodoDePago.esSinDefinir();
	}

	public Boolean estaSinDefinirEnvio() {
		return envio.esSinDefinir();
	}

	public Boolean estaEnEstadoConfirmado() {
		return estadoActual.estaEnEstadoConfirmado();
	}

	public void cancelarPedido() {
		estadoActual.cancelarPedido(this);
	}

	public void cancelarse() {
		this.estadoActual = new Cancelado();
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public Boolean estaEnEstadoCancelado() {
		return estadoActual.estaEnEstadoCancelado();
	}

}
