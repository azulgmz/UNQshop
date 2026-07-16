package pedido;

public class EnPreparacion extends EstadoDelPedido {

	public TipoEstado getTipo() {
		return TipoEstado.ENPREPARACION;
	}
	
	@Override
	public void avanzarEstado(Pedido pedido) {
		pedido.cambiarEstado(new Enviado());
	}

	@Override
	public void cancelarPedido(Pedido pedido) {
		pedido.devolverStock();
		pedido.cancelarse();
		pedido.getSucursal().generarNotaDeCreditoDeProductosYEnvio(pedido);
	}
	
	public String getNombre() {
		return "ENPREPARACION";
	}
}
