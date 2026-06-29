package pedido;

public class Borrador implements EstadoDelPedido {

	public Boolean estaEnEstadoBorrador() {
		return true;
	}

	public Boolean estaEnEstadoConfirmado() {
		return false;
	}

	public void cancelarPedido(Pedido pedido) {
		pedido.cancelarse();
		pedido.getSucursal().eliminarPedidoActivo(pedido);
	}

	public Boolean estaEnEstadoCancelado() {
		return false;
	}

}
