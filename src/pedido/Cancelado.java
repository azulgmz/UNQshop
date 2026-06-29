package pedido;

public class Cancelado implements EstadoDelPedido {

	public Boolean estaEnEstadoBorrador() {
		return false;
	}

	
	public Boolean estaEnEstadoConfirmado() {
		return false;
	}

	public void cancelarPedido(Pedido pedido) {
		
	}
	
	public Boolean estaEnEstadoCancelado() {
		return true;
	}

}
