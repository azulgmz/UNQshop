package reportes;

public class LineaReporteProducto {

	private  String nombreItem;
	private  int cantidadVendida;
	private  float precioPromedio;
 
	public LineaReporteProducto(String nombreItem, int cantidadVendida, float precioPromedio) {
		this.nombreItem = nombreItem;
		this.cantidadVendida = cantidadVendida;
		this.precioPromedio = precioPromedio;
	}
 
	public String getNombreItem() {
		return nombreItem;
	}
 
	public int getCantidadVendida() {
		return cantidadVendida;
	}
 
	public float getPrecioPromedio() {
		return precioPromedio;
	}
 

}
