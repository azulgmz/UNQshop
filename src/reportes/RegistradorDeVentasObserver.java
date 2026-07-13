package reportes;

import pedido.EstadoDelPedido;
import pedido.Pedido;
import pedido.TipoEstado;
import productos.Producto;
import notificadores.PedidoObserver;

public class RegistradorDeVentasObserver implements PedidoObserver{
	
	private  RegistroDeVentas registroDeVentas;
	 
	public RegistradorDeVentasObserver(RegistroDeVentas registroDeVentas) {
		this.registroDeVentas = registroDeVentas;
	}
 
	@Override
	public void enCambioEstado(Pedido pedido, EstadoDelPedido estadoAnterior, EstadoDelPedido estadoNuevo) {
		if (estadoNuevo.getTipo() != TipoEstado.CONFIRMADO) {
			return;
		}
		for (Producto producto : pedido.getListaDeProductos()) {
			registroDeVentas.registrarVenta(producto.getNombre(), producto.precioFinal());
		}
	}
 

}
