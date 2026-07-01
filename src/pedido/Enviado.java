package pedido;

public class Enviado extends EstadoDelPedido {

	@Override
	public Boolean estaEnEstadoEnviado() {
		return true;
	}
	@Override
	public void avanzarEstado(Pedido pedido) {
		pedido.cambiarEstado(new Entregado());
	}

	@Override
	public void cancelarPedido(Pedido pedido) {
		pedido.devolverStock();
		super.cancelarPedido(pedido);
	}

}
