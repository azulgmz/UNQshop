package notificadores;

public interface MailSender  {

	void enviarMail(String destino, String titulo, String mensaje, Object adjunto);
}
