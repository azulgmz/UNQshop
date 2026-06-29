package pedido;

public interface EstadoDelPedido {

	Boolean estaEnEstadoBorrador();

	Boolean estaEnEstadoConfirmado();

	void cancelarPedido(Pedido pedido);

	Boolean estaEnEstadoCancelado();

}
