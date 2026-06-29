package pedido;

public class Confirmado implements EstadoDelPedido {

	public Boolean estaEnEstadoBorrador() {
		return false;
	}

	public Boolean estaEnEstadoConfirmado() {
		return true;
	}
	
	public Boolean estaEnEstadoCancelado() {
		return false;
	}

	public void cancelarPedido(Pedido pedido) {
		pedido.getSucursal().getCatalogo().devolverStock(pedido.getListaDeProductos());
		pedido.cancelarse();
		pedido.getSucursal().eliminarPedidoActivo(pedido);
	}


}
