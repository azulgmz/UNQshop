package pedido;

public class Confirmado extends EstadoDelPedido {

	public TipoEstado getTipo() {
		return TipoEstado.CONFIRMADO;
	}


	public void cancelarPedido(Pedido pedido) {
		pedido.getSucursal().getCatalogo().devolverStock(pedido.getListaDeProductos());
		pedido.cancelarse();
		pedido.getSucursal().eliminarPedidoActivo(pedido);
	}
	public void avanzarEstado(Pedido pedido) {
		pedido.reservarStock();
		pedido.cambiarEstado(new EnPreparacion());
	}
	public Boolean debeNotificar() {
		return true;
	}
	public String getNombre() {
		return "CONFIRMADO";
	}
}
