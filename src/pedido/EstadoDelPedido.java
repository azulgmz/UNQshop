package pedido;

public abstract class EstadoDelPedido {

	public Boolean estaEnEstadoBorrador() {
		return false;
	}
	public Boolean estaEnEstadoConfirmado() {
		return false;
	}
	public boolean estaEnEstadoEnPreparación() {
		return false;
	}
	public Boolean estaEnEstadoEnviado() {
		return false;
	}
	public Boolean estaEnEstadoEntregado() {
		return false;
	}
	public Boolean estaEnEstadoCancelado() {
		return false;
	}
	public abstract void avanzarEstado(Pedido pedido);
	
	public void cancelarPedido(Pedido pedido) {
		pedido.cancelarse();
		pedido.getSucursal().eliminarPedidoActivo(pedido);
	}
	public Boolean sePuedeModificarPedido() {
		return false;
	}
}
