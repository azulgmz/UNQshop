package notificadores;

import pedido.EstadoDelPedido;
import pedido.Pedido;

public class NotificadorEmailObserver implements PedidoObserver {

	private  MailSender mailSender;
	
	public NotificadorEmailObserver(MailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	@Override
	public void enCambioEstado(Pedido pedido, EstadoDelPedido estadoAnterior, EstadoDelPedido estadoNuevo) {
		if (!seDebeNotificar(estadoNuevo)) {
			return;
		}
		else {
			String titulo = "Tu pedido cambbio de estado";
			String mensaje = "Tu pedido ahora esta en estado " + nombreEstado(estadoNuevo);
			mailSender.enviarMail(pedido.getMail(), titulo, mensaje, null);
		}
	}
	private Boolean seDebeNotificar(EstadoDelPedido estado) {
		return estado.estaEnEstadoConfirmado() || estado.estaEnEstadoEnviado() || estado.estaEnEstadoEntregado();
	}
	private String nombreEstado(EstadoDelPedido estado) {
		if (estado.estaEnEstadoConfirmado()) return "CONFIRMADO";
		if (estado.estaEnEstadoEnviado()) return "ENVIADO";
		if (estado.estaEnEstadoEntregado()) return "ENTREGADO";
		else return "";
	}

}
