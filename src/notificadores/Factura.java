package notificadores;

public class Factura {

	private String clienteMail;
	private int cantidadDeProductos;
	
	public Factura(String clienteMail, int cantidadDeProductos) {
		this.clienteMail = clienteMail;
		this.cantidadDeProductos = cantidadDeProductos;
	}

	public String getClienteMail() {
		return clienteMail;
	}
	public int getCantidadDeProductos() {
		return cantidadDeProductos;
	}
}
