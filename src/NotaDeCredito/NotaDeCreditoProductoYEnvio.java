package NotaDeCredito;

import java.time.LocalDate;

import pedido.Pedido;

public class NotaDeCreditoProductoYEnvio extends NotaDeCredito{
	
	private float costoDeProductos, precioEnvio;
	
	public NotaDeCreditoProductoYEnvio(int nroDeNota, Pedido pedido, LocalDate fecha, float costoDeProductos, float precioEnvio, int CUIT) {
		super(nroDeNota, pedido, fecha, CUIT);
		this.costoDeProductos = costoDeProductos;
		this.precioEnvio      = precioEnvio;
	}
	
	public String detalles() {
		String detalles = detallesDatos();
		detalles += "Detalles de montos:" + "\n";
		detalles += detallesProducto();
		detalles += "Envio: " + precioEnvio + ".\n";
		detalles += "Valor total: " + (costoDeProductos + precioEnvio) + ".\n";
		
		return detalles;
		
	}

	public float getCostoDeProductos() {
		return costoDeProductos;
	}

	public float getPrecioEnvio() {
		return precioEnvio;
	}


}
