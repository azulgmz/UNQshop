package notificadores;

import pedido.EstadoDelPedido;
import pedido.Pedido;

public class FidelizaciónObserver implements PedidoObserver {

	private static double cuponDescuentoPorcentaje = 5.0;

	private MailSender mailSender;

	public FidelizaciónObserver(MailSender mailSender) {
		this.mailSender = mailSender;
	}
	@Override
	public void enCambioEstado(Pedido pedido, EstadoDelPedido estadoAnterior, EstadoDelPedido estadoNuevo) {
		if (estadoNuevo.estaEnEstadoCancelado()) {
			String titulo = "Te regalamos un cupon";
			String mensaje = "Por la cancelación de tu compra, te regalamos un cupon de " + cuponDescuentoPorcentaje + "% para tu proxima compra";
			mailSender.enviarMail(pedido.getMail(), titulo, mensaje, null);
			
		}

	}

}
