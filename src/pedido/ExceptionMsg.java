package pedido;

@SuppressWarnings("serial")
public class ExceptionMsg extends RuntimeException {
	
	public ExceptionMsg(String mensaje) {
		super(mensaje);
	}
}
