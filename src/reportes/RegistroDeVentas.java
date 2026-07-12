package reportes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
 


public class RegistroDeVentas {

	private  List<Venta> ventas = new ArrayList<>();
	 
	public void registrarVenta(String nombreItem, float precioCobrado) {
		registrarVenta(nombreItem, precioCobrado, LocalDate.now());
	}
 
	public void registrarVenta(String nombreItem, float precioCobrado, LocalDate fecha) {
		ventas.add(new Venta(nombreItem, precioCobrado, fecha));
	}
 
	public ReporteProductosMasVendidos generarReporteMasVendidos(LocalDate desde, LocalDate hasta) {
		Map<String, List<Venta>> ventasPorItem = new LinkedHashMap<>();
 
		for (Venta venta : ventas) {
			if (estaEnPeriodo(venta, desde, hasta)) {
				ventasPorItem
						.computeIfAbsent(venta.getNombreItem(), nombre -> new ArrayList<>())
						.add(venta);
			}
		}
 
		List<LineaReporteProducto> lineas = new ArrayList<>();
		for (Map.Entry<String, List<Venta>> entrada : ventasPorItem.entrySet()) {
			lineas.add(construirLinea(entrada.getKey(), entrada.getValue()));
		}
		lineas.sort((a, b) -> Integer.compare(b.getCantidadVendida(), a.getCantidadVendida()));
 
		return new ReporteProductosMasVendidos(lineas, desde, hasta);
	}
 
	private boolean estaEnPeriodo(Venta venta, LocalDate desde, LocalDate hasta) {
		return !venta.getFecha().isBefore(desde) && !venta.getFecha().isAfter(hasta);
	}
 
	private LineaReporteProducto construirLinea(String nombreItem, List<Venta> ventasDelItem) {
		int cantidadVendida = ventasDelItem.size();
		float precioPromedio = (float) ventasDelItem.stream()
				.mapToDouble(Venta::getPrecioCobrado)
				.average()
				.orElse(0);
		return new LineaReporteProducto(nombreItem, cantidadVendida, precioPromedio);
	}
 

	
}
