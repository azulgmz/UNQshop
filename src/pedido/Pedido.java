package pedido;

import java.util.ArrayList;
import java.util.List;
import notificadores.*;

import envios.Envio;
import envios.EnvioEstandar;
import envios.SinEnvioDefinido;
import envios.TipoEnvio;
import metodosDePago.MetodoDePago;
import metodosDePago.SinMetodoDePagoDefinido;
import productos.Producto;
import sistemas.Sucursal;
import ubicacionGeografica.Direccion;

public class Pedido {

	private Sucursal            sucursal;
	private EstadoDelPedido     estadoActual;
	private ArrayList<Producto> listaDeProductos;
	private String              mail;
	private Direccion              direccion;
	private MetodoDePago        metodoDePago;
	private Envio               envio;
	private List<PedidoObserver> observers = new ArrayList<>();
	
	public Pedido(Sucursal sucursal, ArrayList<Producto> listaDeProductos, String mail, Direccion direccion) {
		this.sucursal         = sucursal;
		this.estadoActual     = new Borrador();
		this.listaDeProductos = listaDeProductos;
		this.mail             = mail;
		this.direccion        = direccion;
		this.metodoDePago     = new SinMetodoDePagoDefinido();
	    this.envio            = new SinEnvioDefinido();
	    
	}

	public String getMail() {
		return mail;
	}

	public Direccion getDireccion() {
		return direccion;
	}


	public void agregarProducto(Producto producto) {
		if (estadoActual.sePuedeModificarPedido()) {
			sucursal.getCatalogo().tieneStockDe(producto);
			listaDeProductos.add(producto);
		}
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
		if (estadoActual.sePuedeModificarPedido()) {
			listaDeProductos.remove(producto);
		}
	}

	public void confirmarPedido(MetodoDePago metodoDePago, Envio envio) {
		this.metodoDePago = metodoDePago;
		this.envio = envio;
		
		metodoDePago.procesarPago(calcularPrecioTotal());
		
		sucursal.getCatalogo().descontarStock(listaDeProductos);
		
		this.cambiarEstado(new Confirmado());
	}

	public Boolean estaSinDefinirMetodoDePago() {
		return metodoDePago.esSinDefinir();
	}

	public void cancelarPedido() {
		estadoActual.cancelarPedido(this);
	}

	public void cancelarse() {
		this.cambiarEstado(new Cancelado());
	}

	public Sucursal getSucursal() {
		return sucursal;
	}


	public ArrayList<Producto> getListaDeProductos() {
		return listaDeProductos;
	}

	public void cambiarEstado(EstadoDelPedido estado) {
		EstadoDelPedido estadoAnterior = estadoActual;
		estadoActual = estado;
		this.notificarObservers(estadoAnterior,estado);
	}
	public void reservarStock() {
		sucursal.getCatalogo().descontarStock(listaDeProductos);
	}
	public void devolverStock() {
		sucursal.getCatalogo().devolverStock(listaDeProductos);
	}
	public void avanzarEstado() {
		estadoActual.avanzarEstado(this);
	}
	public void agregarObserver(PedidoObserver observer) {
		observers.add(observer);
	}
	public void quitarObserver(PedidoObserver observer) {
		observers.remove(observer);
	}
	public void notificarObservers(EstadoDelPedido estadoAnterior, EstadoDelPedido estadoNuevo) {
		for (PedidoObserver observer : observers) {
			observer.enCambioEstado(this,estadoAnterior,estadoNuevo);
		}
	}
	public TipoEstado getEstado() {
		return estadoActual.getTipo();
	}

	public TipoEnvio getEnvio() {
		return envio.getTipo();
	}
	
	public MetodoDePago getMetodoDePago() {
		return metodoDePago;
	}

	public void setEnvio(Envio envio) {
		this.envio = envio;
	}

	public float calcularCosto() {
		return envio.costoEnvio(this);
	}

	public float pesoTotal() {
		
		float pesoTotal = 0;
		int cantidadDeProductos = listaDeProductos.size();
		
		for (int i=0; i < cantidadDeProductos; i++) {
			pesoTotal += listaDeProductos.get(i).getPeso();
		}
		return pesoTotal;
	}

	public String estimacionDeEntrega() {
		return envio.estimacionDeEntrega(sucursal.getDireccion(), direccion);
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}
	
	public float calcularPrecioTotal() {
	    float total = 0;
	    int cantidadDeProductos = listaDeProductos.size();
	    for (int i = 0; i < cantidadDeProductos; i++) {
	        total += listaDeProductos.get(i).precioFinal();
	    }
	    return total;
	}
	
}
