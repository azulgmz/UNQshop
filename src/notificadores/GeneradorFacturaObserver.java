package notificadores;

import java.time.LocalDate;

import pedido.EstadoDelPedido;
import pedido.Pedido;
import pedido.TipoEstado;

public class GeneradorFacturaObserver implements PedidoObserver {
	
	private int nroComprobante;
	
	public GeneradorFacturaObserver() {
		this.nroComprobante = nroComprobante;
	}
	
	@Override
	public void enCambioEstado(Pedido pedido, EstadoDelPedido estadoAnterior, EstadoDelPedido estadoNuevo) {
		if (estadoNuevo.getTipo() == TipoEstado.ENTREGADO) {
			nroComprobante++;
			pedido.getSucursal().agregarComprobanteFiscal(pedido, LocalDate.now(), pedido.getMetodoDePago(), nroComprobante, "C");
		}

	}

}
