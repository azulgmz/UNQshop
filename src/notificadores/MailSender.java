package notificadores;

public interface MailSender extends PedidoObserver {

	void enviarMail(String destino, String titulo, String mensaje, Object adjunto);
}
