package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.contains;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
 
import java.util.ArrayList;
 
import org.junit.jupiter.api.Test;
 
import envios.Envio;
import metodosDePago.ApiBilleteraVirtual;
import metodosDePago.ApiTarjetaDeCredito;
import metodosDePago.ApiTransferenciaBancaria;
import metodosDePago.BilleteraVirtual;
import metodosDePago.MetodoDePago;
import metodosDePago.SinMetodoDePagoDefinido;
import metodosDePago.TarjetaDeCredito;
import metodosDePago.TransferenciaBancaria;
import pedido.ExceptionMsg;
import pedido.Pedido;
import productos.Atributo;
import sistemas.Catalogo;
import sistemas.Sucursal;
import ubicacionGeografica.Direccion;


public class MetodosDePagoTestCase {
	@Test
	void testTarjetaDeCreditoProcesaElPagoYGeneraUnCupon() {
		ApiTarjetaDeCredito api = mock(ApiTarjetaDeCredito.class);
		when(api.validarDatos(4111, 123, "12/28")).thenReturn(true);
		when(api.preAutorizar(4111, 1000f)).thenReturn(true);
		when(api.transferirInmediato(4111, 1000f)).thenReturn(9001);
 
		TarjetaDeCredito tarjeta = new TarjetaDeCredito(api, 4111, 123, "12/28");
		tarjeta.procesarPago(1000f);
 
		assertEquals(1, tarjeta.getCuponesGenerados().size());
		assertEquals(9001, tarjeta.getCuponesGenerados().get(0).getCodigoTransaccion());
		verify(api).preAutorizar(4111, 1000f);
	}
 
	@Test
	void testTarjetaDeCreditoConDatosInvalidosNoProcesaElPago() {
		ApiTarjetaDeCredito api = mock(ApiTarjetaDeCredito.class);
		when(api.validarDatos(4111, 0, "01/20")).thenReturn(false);
 
		TarjetaDeCredito tarjeta = new TarjetaDeCredito(api, 4111, 0, "01/20");
 
		ExceptionMsg error = assertThrows(ExceptionMsg.class, () -> tarjeta.procesarPago(1000f));
 
		assertEquals("Datos de pago inválidos", error.getMessage());
		verify(api, never()).preAutorizar(4111, 1000f);
	}
 
	@Test
	void testTransferenciaBancariaNoRequiereReservaDeFondosYGeneraComprobante() {
		ApiTransferenciaBancaria api = mock(ApiTransferenciaBancaria.class);
		when(api.validarCbu(123456)).thenReturn(true);
		when(api.transferir(123456, 500f, false)).thenReturn(99);
 
		TransferenciaBancaria transferencia = new TransferenciaBancaria(api, 123456, false);
		transferencia.procesarPago(500f);
 
		assertEquals(1, transferencia.getComprobantesGenerados().size());
		assertEquals(99, transferencia.getComprobantesGenerados().get(0).getNumeroOperacion());
	}
 
	@Test
	void testBilleteraVirtualEnviaNotificacionAlFinalizar() {
		ApiBilleteraVirtual api = mock(ApiBilleteraVirtual.class);
		when(api.validarSaldoSuficiente(1, 250f)).thenReturn(true);
		when(api.bloquearSaldo(1, 250f)).thenReturn(true);
		when(api.acreditarTransaccion(1, 250f)).thenReturn(7777);
 
		BilleteraVirtual billetera = new BilleteraVirtual(api, 1);
		billetera.procesarPago(250f);
 
		verify(api).enviarNotificacion(eq(1), contains("7777"));
		assertEquals(1, billetera.getNotificacionesEnviadas().size());
	}
 
	@Test
	void testBilleteraVirtualSinSaldoSuficienteLanzaExcepcionYNoBloqueaSaldo() {
		ApiBilleteraVirtual api = mock(ApiBilleteraVirtual.class);
		when(api.validarSaldoSuficiente(1, 250f)).thenReturn(false);
 
		BilleteraVirtual billetera = new BilleteraVirtual(api, 1);
 
		assertThrows(ExceptionMsg.class, () -> billetera.procesarPago(250f));
		verify(api, never()).bloquearSaldo(1, 250f);
	}
 
	@Test
	void testSinMetodoDePagoDefinidoNoPuedeProcesarUnPago() {
		SinMetodoDePagoDefinido sinDefinir = new SinMetodoDePagoDefinido();
 
		assertTrue(sinDefinir.esSinDefinir());
		assertThrows(ExceptionMsg.class, () -> sinDefinir.procesarPago(100f));
	}
 
	@Test
	void testConfirmarPedidoInvocaElProcesamientoDelPagoConElMontoTotal() {
		Direccion direccionSucursal = new Direccion("Dirección de la sucursal", -34.6, -58.4);
		Direccion direccionCliente = new Direccion("Dirección del cliente", -34.6, -58.4);
 
		Catalogo catalogo = new Catalogo();
		Sucursal sucursal = new Sucursal(1, catalogo, 0f, direccionSucursal, new ArrayList<>() );
		catalogo.registrarIndividual("Mouse", "Marca", "Categoria", new ArrayList<Atributo>(), 1500f, 10, 300f); // SKU = 1
 
		Pedido pedido = sucursal.crearPedido("ana@mail.com", direccionCliente);
		pedido.agregarProducto(catalogo.buscarProducto(1));
		pedido.agregarProducto(catalogo.buscarProducto(1)); // 2 unidades -> monto esperado 3000
 
		MetodoDePago metodoDePagoFake = mock(MetodoDePago.class);
		Envio envioDummy = mock(Envio.class);
 
		pedido.confirmarPedido(metodoDePagoFake, envioDummy);
 
		verify(metodoDePagoFake).procesarPago(3000f);
	}
 

}
