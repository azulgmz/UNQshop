package reportes;

import java.time.format.DateTimeFormatter;

public class VisitorHTML implements ReporteVisitor {

	private  DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	@Override
	public String visitarProductosMasVendidos(ReporteProductosMasVendidos reporte) {
		StringBuilder html = new StringBuilder();
		html.append("<html>\n<body>\n");
		html.append("<h1>Reporte de productos más vendidos</h1>\n");
		html.append("<p>Período: ")
				.append(reporte.getDesde().format(FORMATO_FECHA))
				.append(" al ")
				.append(reporte.getHasta().format(FORMATO_FECHA))
				.append("</p>\n");
		html.append("<table border=\"1\">\n");
		html.append("<tr><th>Producto</th><th>Unidades vendidas</th><th>Precio promedio</th></tr>\n");
 
		for (LineaReporteProducto linea : reporte.getLineas()) {
			html.append("<tr><td>").append(escapar(linea.getNombreItem())).append("</td><td>")
					.append(linea.getCantidadVendida()).append("</td><td>$")
					.append(String.format("%.2f", linea.getPrecioPromedio())).append("</td></tr>\n");
		}
		html.append("</table>\n</body>\n</html>");
		return html.toString();
	}
 
	private String escapar(String valor) {
		return valor.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
	}
 

}
