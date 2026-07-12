package reportes;

import java.time.format.DateTimeFormatter;

public class VisitorTextoPlano implements ReporteVisitor {

	private DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	@Override
	public String visitarProductosMasVendidos(ReporteProductosMasVendidos reporte) {
		StringBuilder texto = new StringBuilder();
		texto.append("Reporte de productos más vendidos\n");
		texto.append("Período: ")
				.append(reporte.getDesde().format(FORMATO_FECHA))
				.append(" al ")
				.append(reporte.getHasta().format(FORMATO_FECHA))
				.append("\n\n");
 
		for (LineaReporteProducto linea : reporte.getLineas()) {
			texto.append(String.format("%-30s unidades: %-5d precio promedio: $%.2f%n",
					linea.getNombreItem(), linea.getCantidadVendida(), linea.getPrecioPromedio()));
		}
		return texto.toString();
	}
 
	
}
