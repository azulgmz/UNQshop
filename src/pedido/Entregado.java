package pedido;

public class Entregado extends EstadoDelPedido {

	public TipoEstado getTipo() {
		return TipoEstado.ENTREGADO;
	}
	@Override
	public void avanzarEstado(Pedido pedido) {
		throw new ExceptionMsg("Pedido ya entregado");
	}
	@Override
	public void cancelarPedido(Pedido pedido) {
		throw new ExceptionMsg("No se puede cancelar pedido entregado");
	}
	public Boolean debeNotificar() {
		return true;
	}
	public String getNombre() {
		return "ENTREGADO";
	}
}
