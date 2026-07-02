package notificadores;

import java.util.ArrayList;
import java.util.List;

import pedido.EstadoDelPedido;
import pedido.Pedido;

public class GeneradorFacturaObserver implements PedidoObserver {

	private List<Factura> facturasGeneradas = new ArrayList<>();
	
	@Override
	public void enCambioEstado(Pedido pedido, EstadoDelPedido estadoAnterior, EstadoDelPedido estadoNuevo) {
		if (estadoNuevo.estaEnEstadoEntregado()) {
			facturasGeneradas.add(new Factura(pedido.getMail(), pedido.getListaDeProductos().size()));
		}
		else {
			return;
		}
	}
	public List<Factura> getFacturasGeneradas(){
		return facturasGeneradas;
	}

}
