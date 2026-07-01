package pedido;

public class Confirmado extends EstadoDelPedido {

	@Override
	public Boolean estaEnEstadoConfirmado() {
		return true;
	}


	public void cancelarPedido(Pedido pedido) {
		pedido.getSucursal().getCatalogo().devolverStock(pedido.getListaDeProductos());
		pedido.cancelarse();
		pedido.getSucursal().eliminarPedidoActivo(pedido);
	}
	public void avanzarEstado(Pedido pedido) {
		pedido.reservarStock();
		pedido.cambiarEstado(new EnPreparacion());
	}

}
