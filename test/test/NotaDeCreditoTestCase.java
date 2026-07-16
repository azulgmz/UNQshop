package test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import NotaDeCredito.NotaDeCreditoProducto;
import NotaDeCredito.NotaDeCreditoProductoYEnvio;
import pedido.Pedido;

class NotaDeCreditoTestCase {
	
	private Pedido                      pedidoDummy;
	private NotaDeCreditoProducto       notaProducto;
	private NotaDeCreditoProductoYEnvio notaProductoYEnvio;
	
	@BeforeEach
	public void setUp() {
		pedidoDummy = mock(Pedido.class);
	}
	
	@Test
	void testUnaNotaDeCreditoProductoConoceSusDatos() {
		notaProducto = new NotaDeCreditoProducto(1, pedidoDummy, LocalDate.now(), 1000, 14655);
		
		assertEquals(1, notaProducto.getNroDeNota());
		assertEquals(pedidoDummy, notaProducto.getPedido());
		assertEquals(LocalDate.now(), notaProducto.getFecha());
		assertEquals(1000, notaProducto.getCostoDeProductos());
		assertEquals(14655, notaProducto.getCUIT());
	}
	
	@Test
	void testUnaNotaDeCreditoProductoYEnvioConoceSusDatos() {
		notaProductoYEnvio = new NotaDeCreditoProductoYEnvio(2, pedidoDummy, LocalDate.now(), 1000, 500, 14655);
		
		assertEquals(2, notaProductoYEnvio.getNroDeNota());
		assertEquals(pedidoDummy, notaProductoYEnvio.getPedido());
		assertEquals(LocalDate.now(), notaProductoYEnvio.getFecha());
		assertEquals(1000, notaProductoYEnvio.getCostoDeProductos());
		assertEquals(500, notaProductoYEnvio.getPrecioEnvio());
		assertEquals(14655, notaProductoYEnvio.getCUIT());
	}

}
