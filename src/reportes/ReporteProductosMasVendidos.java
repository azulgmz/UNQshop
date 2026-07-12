package reportes;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
 


public class ReporteProductosMasVendidos implements Reporte {

	private  List<LineaReporteProducto> lineas;
	private  LocalDate desde;
	private  LocalDate hasta;
 
	public ReporteProductosMasVendidos(List<LineaReporteProducto> lineas, LocalDate desde, LocalDate hasta) {
		this.lineas = Collections.unmodifiableList(lineas);
		this.desde = desde;
		this.hasta = hasta;
	}
 
	public List<LineaReporteProducto> getLineas() {
		return lineas;
	}
 
	public LocalDate getDesde() {
		return desde;
	}
 
	public LocalDate getHasta() {
		return hasta;
	}
 
	@Override
	public String aceptar(ReporteVisitor visitor) {
		return visitor.visitarProductosMasVendidos(this);
	}
 

}
