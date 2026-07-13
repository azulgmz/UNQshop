package pedido;

public abstract class EstadoDelPedido {

	public abstract TipoEstado getTipo();
	
	public abstract void avanzarEstado(Pedido pedido);
	
	public abstract String getNombre();
	
	public Boolean sePuedeModificarPedido() {
		return false;
	}
	public Boolean debeNotificar() {
		return false;
	}

		public void cancelarPedido(Pedido pedido) {
		pedido.cancelarse();
	}
	
}
