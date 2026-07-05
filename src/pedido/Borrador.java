package pedido;

public class Borrador extends EstadoDelPedido {

	public TipoEstado getTipo() {
		return TipoEstado.BORRADOR;
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
	public String getNombre() {
		return "BORRADOR";
	}
}
