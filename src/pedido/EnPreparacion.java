package pedido;

public class EnPreparacion extends EstadoDelPedido {

	@Override
	public boolean estaEnEstadoEnPreparación() {
		return true;
	}
	@Override
	public void avanzarEstado(Pedido pedido) {
		pedido.cambiarEstado(new Enviado());
	}

	@Override
	public void cancelarPedido(Pedido pedido) {
		pedido.devolverStock();
		super.cancelarPedido(pedido);
	}

}
