package pedido;

public class Confirmado implements EstadoDelPedido {

	public Boolean estaEnEstadoBorrador() {
		return false;
	}

	public Boolean estaEnEstadoConfirmado() {
		return true;
	}

}
