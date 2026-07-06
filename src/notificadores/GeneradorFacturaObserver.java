package notificadores;

import java.util.ArrayList;
import java.util.List;

import pedido.EstadoDelPedido;
import pedido.Pedido;
import pedido.TipoEstado;

public class GeneradorFacturaObserver implements PedidoObserver {

	private List<Factura> facturasGeneradas = new ArrayList<>();
	
	@Override
	public void enCambioEstado(Pedido pedido, EstadoDelPedido estadoAnterior, EstadoDelPedido estadoNuevo) {
		if (estadoNuevo.getTipo() == TipoEstado.ENTREGADO) {
			facturasGeneradas.add(new Factura(pedido.getMail(), pedido.getListaDeProductos().size()));
		}

	}
	public List<Factura> getFacturasGeneradas(){
		return facturasGeneradas;
	}

}
