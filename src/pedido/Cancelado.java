package pedido;

public class Cancelado extends EstadoDelPedido {

	public TipoEstado getTipo() {
		return TipoEstado.CANCELADO;
	}

	public void cancelarPedido(Pedido pedido) {
		throw new ExceptionMsg("Pedido ya cancelado");
	}

	public void avanzarEstado(Pedido pedido) {
		throw new ExceptionMsg("Pedido cancelado");
	}
	public String getNombre() {
		return "CANCELADO";
	}
}
