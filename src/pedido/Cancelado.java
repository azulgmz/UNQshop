package pedido;

public class Cancelado extends EstadoDelPedido {

	@Override
	public Boolean estaEnEstadoCancelado() {
		return true;
	}

	public void cancelarPedido(Pedido pedido) {
		throw new ExceptionMsg("Pedido ya cancelado");
	}

	public void avanzarEstado(Pedido pedido) {
		throw new ExceptionMsg("Pedido cancelado");
	}

}
