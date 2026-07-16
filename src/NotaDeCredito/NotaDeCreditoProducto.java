package NotaDeCredito;

import java.time.LocalDate;

import pedido.Pedido;

public class NotaDeCreditoProducto extends NotaDeCredito{

	private float costoDeProductos;
	
	public NotaDeCreditoProducto(int nroDeNota, Pedido pedido, LocalDate fecha, float costoDeProductos, int CUIT) {
		super(nroDeNota, pedido, fecha, CUIT);
		this.costoDeProductos = costoDeProductos;
	}
	
	public String detalles() {
		String detalles = detallesDatos();
		detalles += "Detalles de montos: " + "\n";
		detalles += detallesProducto();
		detalles += "Valor total: " + costoDeProductos + ".\n";
		
		return detalles;
		
	}





}
