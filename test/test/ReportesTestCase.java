package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
 
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
 
import envios.Envio;
import metodosDePago.MetodoDePago;
import pedido.Pedido;
import productos.Atributo;
import productos.SistemaDeProductos;
import reportes.LineaReporteProducto;
import reportes.RegistradorDeVentasObserver;
import reportes.RegistroDeVentas;
import reportes.Reporte;
import reportes.ReporteProductosMasVendidos;
import reportes.VisitorCSV;
import reportes.VisitorHTML;
import reportes.VisitorTextoPlano;
import sistemas.Catalogo;
import sistemas.Sucursal;
import ubicacionGeografica.Direccion;


public class ReportesTestCase {
	private RegistroDeVentas registroDeVentas;
	 
	@BeforeEach
	void setUp() {
		registroDeVentas = new RegistroDeVentas();
	}
 
	@Test
	void testAgrupaPorItemYOrdenaPorCantidadDescendente() {
		registroDeVentas.registrarVenta("Auriculares Bluetooth", 8000f, LocalDate.of(2026, 6, 1));
		registroDeVentas.registrarVenta("Auriculares Bluetooth", 8000f, LocalDate.of(2026, 6, 2));
		registroDeVentas.registrarVenta("Cable USB-C", 800f, LocalDate.of(2026, 6, 3));
 
		ReporteProductosMasVendidos reporte =
				registroDeVentas.generarReporteMasVendidos(LocalDate.of(2026, 6, 1), LocalDate.of(2026, 6, 30));
 
		List<LineaReporteProducto> lineas = reporte.getLineas();
		assertEquals(2, lineas.size());
		assertEquals("Auriculares Bluetooth", lineas.get(0).getNombreItem());
		assertEquals(2, lineas.get(0).getCantidadVendida());
		assertEquals("Cable USB-C", lineas.get(1).getNombreItem());
	}
 
	@Test
	void testCalculaElPrecioPromedioCorrectamenteConDescuentosDePaquete() {
		registroDeVentas.registrarVenta("Pack Audio Móvil", 8755f, LocalDate.of(2026, 6, 1));
		registroDeVentas.registrarVenta("Pack Audio Móvil", 8000f, LocalDate.of(2026, 6, 2));
 
		ReporteProductosMasVendidos reporte =
				registroDeVentas.generarReporteMasVendidos(LocalDate.of(2026, 6, 1), LocalDate.of(2026, 6, 30));
 
		assertEquals(8377.5f, reporte.getLineas().get(0).getPrecioPromedio(), 0.01);
	}
 
	@Test
	void testExcluyeVentasFueraDelPeriodo() {
		registroDeVentas.registrarVenta("Cable USB-C", 800f, LocalDate.of(2026, 5, 15)); // 
		registroDeVentas.registrarVenta("Cable USB-C", 800f, LocalDate.of(2026, 6, 15)); // 
 
		ReporteProductosMasVendidos reporte =
				registroDeVentas.generarReporteMasVendidos(LocalDate.of(2026, 6, 1), LocalDate.of(2026, 6, 30));
 
		assertEquals(1, reporte.getLineas().get(0).getCantidadVendida());
	}
 
	@Test
	void testElMismoReporteSeExportaEnLosTresFormatos() {
		registroDeVentas.registrarVenta("Funda protectora", 1500f, LocalDate.of(2026, 6, 1));
		Reporte reporte = registroDeVentas.generarReporteMasVendidos(
				LocalDate.of(2026, 6, 1), LocalDate.of(2026, 6, 30));
 
		String texto = reporte.aceptar(new VisitorTextoPlano());
		String csv = reporte.aceptar(new VisitorCSV());
		String html = reporte.aceptar(new VisitorHTML());
 
		assertTrue(texto.contains("Funda protectora"));
		assertTrue(csv.contains("Funda protectora,1,1500.00"));
		assertTrue(html.contains("<td>Funda protectora</td>"));
	}
 
	@Test
	void testConfirmarUnPedidoRegistraLaVentaDeSusItems() {
		Direccion direccionSucursal = new Direccion("Dirección de la sucursal", -34.6, -58.4);
		Direccion direccionCliente = new Direccion("Dirección del cliente", -34.6, -58.4);
		
		SistemaDeProductos sistemaDeProductos = new SistemaDeProductos();
		
		Catalogo catalogo = new Catalogo();
		Sucursal sucursal = new Sucursal(1, catalogo, 0f, direccionSucursal, new ArrayList<Sucursal>(), new RegistroDeVentas());
		
		sistemaDeProductos.agregarSucursal(sucursal);
		
		sistemaDeProductos.registrarIndividual("Mouse inalámbrico", "MarcaX", "Electrónica", new ArrayList<Atributo>(), 5000f, 10, 300f); // SKU = 1
 
		Pedido pedido = sucursal.crearPedido("ana@mail.com", direccionCliente);
		pedido.agregarProducto(catalogo.buscarProducto(1));
		pedido.agregarObserver(new RegistradorDeVentasObserver(registroDeVentas));
 
		MetodoDePago metodoDePagoFake = mock(MetodoDePago.class);
		Envio envioDummy = mock(Envio.class);
		pedido.confirmarPedido(metodoDePagoFake, envioDummy);
 
		ReporteProductosMasVendidos reporte = registroDeVentas.generarReporteMasVendidos(
				LocalDate.now(), LocalDate.now());
 
		assertEquals(1, reporte.getLineas().size());
		assertEquals("Mouse inalámbrico", reporte.getLineas().get(0).getNombreItem());
		assertEquals(5000f, reporte.getLineas().get(0).getPrecioPromedio(), 0.01);
	}
 

	
}
