package notificadores;

import pedido.*;

public interface PedidoObserver {

	void enCambioEstado(Pedido pedido, EstadoDelPedido estadoAnterior, EstadoDelPedido estadoNuevo);
	
}
