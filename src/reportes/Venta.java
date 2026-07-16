package reportes;

import java.time.LocalDate;

public class Venta {
	
	private  String nombreProducto;
	private  float precioCobrado;
	private  LocalDate fecha;
 
	public Venta(String nombreProducto, float precioCobrado, LocalDate fecha) {
		this.nombreProducto = nombreProducto;
		this.precioCobrado = precioCobrado;
		this.fecha = fecha;
	}
 
	public String getNombreProducto() {
		return nombreProducto;
	}
 
	public float getPrecioCobrado() {
		return precioCobrado;
	}
 
	public LocalDate getFecha() {
		return fecha;
	}
 

}
