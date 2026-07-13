package reportes;

import java.util.Locale;

public class VisitorCSV implements ReporteVisitor {

		@Override
		public String visitarProductosMasVendidos(ReporteProductosMasVendidos reporte) {
			StringBuilder csv = new StringBuilder();
			csv.append("producto,unidades_vendidas,precio_promedio\n");
	 
			for (LineaReporteProducto linea : reporte.getLineas()) {
				csv.append(escapar(linea.getNombreItem())).append(",")
						.append(linea.getCantidadVendida()).append(",")
						.append(String.format(Locale.ROOT,"%.2f", linea.getPrecioPromedio()))
						.append("\n");
			}
			return csv.toString();
		}
	 
		private String escapar(String valor) {
			if (valor.contains(",") || valor.contains("\"")) {
				return "\"" + valor.replace("\"", "\"\"") + "\"";
			}
			return valor;
		}
	 
}
