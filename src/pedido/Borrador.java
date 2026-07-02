package pedido;

public class Borrador extends EstadoDelPedido {

	@Override
	public Boolean estaEnEstadoBorrador() {
		return true;
	}

	public void cancelarPedido(Pedido pedido) {
		pedido.cancelarse();
		pedido.getSucursal().eliminarPedidoActivo(pedido);
	}
	public void avanzarEstado(Pedido pedido) {
		pedido.cambiarEstado(new Confirmado());
	}
	@Override
	public Boolean sePuedeModificarPedido() {
		return true;
	}
}
