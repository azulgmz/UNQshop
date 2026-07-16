package pedido;

public class Enviado extends EstadoDelPedido {

	public TipoEstado getTipo() {
		return TipoEstado.ENVIADO;
	}
	
	@Override
	public void avanzarEstado(Pedido pedido) {
		pedido.cambiarEstado(new Entregado());
	}

	@Override
	public void cancelarPedido(Pedido pedido) {
		pedido.cancelarse();
		pedido.getSucursal().generarNotaDeCreditoDeProductos(pedido);
	}
	
	public Boolean debeNotificar() {
		return true;
	}
	public String getNombre() {
		return "ENVIADO";
	}
}
