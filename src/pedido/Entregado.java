package pedido;

public class Entregado extends EstadoDelPedido {

	@Override 
	public Boolean estaEnEstadoEntregado() {
		return true;
	}
	@Override
	public void avanzarEstado(Pedido pedido) {
		throw new ExceptionMsg("Pedido ya entregado");
	}
	@Override
	public void cancelarPedido(Pedido pedido) {
		throw new ExceptionMsg("No se puede cancelar pedido entregado");
	}

}
