package reportes;

import java.time.LocalDate;

public class Venta {
	
	private  String nombreItem;
	private  float precioCobrado;
	private  LocalDate fecha;
 
	public Venta(String nombreItem, float precioCobrado, LocalDate fecha) {
		this.nombreItem = nombreItem;
		this.precioCobrado = precioCobrado;
		this.fecha = fecha;
	}
 
	public String getNombreItem() {
		return nombreItem;
	}
 
	public float getPrecioCobrado() {
		return precioCobrado;
	}
 
	public LocalDate getFecha() {
		return fecha;
	}
 

}
