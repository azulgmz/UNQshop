package test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import envios.CorreoArgentina;
import envios.EnvioEstandar;
import envios.EnvioExpress;
import envios.TipoEnvio;
import pedido.Pedido;
import sistemas.Catalogo;
import sistemas.Sucursal;
import ubicacionGeografica.Direccion;

class EnvioTestCase {
	
	private Catalogo            catalogoDummy;
	private Sucursal            sucursalUNQ;
	private Pedido              pedido;
	private CorreoArgentina     correoStub;
	private ArrayList<Sucursal> sucursales;
	
	@BeforeEach
	public void setUp() {
		correoStub = mock(CorreoArgentina.class); // Creo un correo Stub
		catalogoDummy  = new Catalogo(); // Creo un catalogo dummy
		sucursales     = new ArrayList<Sucursal>();
	    sucursalUNQ    = new Sucursal(28062026, catalogoDummy, 100000f, new Direccion("Roque Sáenz Peña 124", -34.76493d, -58.278418d), sucursales);
	    
	    pedido = sucursalUNQ.crearPedido("juan@gmail.com", new Direccion("9 de Julio 217", -34.712445d, -58.284493d));
	    
	}             
	
	@Test
	void testElCostoYEstimacionDeUnEnvioCuandoElPedidoEstaHechoComoEstandar() {
		
		when(correoStub.estimarEnvio(pedido.pesoTotal(), pedido.getDireccion())).thenReturn(1000f);

	    EnvioEstandar envio = new EnvioEstandar(correoStub);

	    pedido.setEnvio(envio);

	    assertEquals(TipoEnvio.ESTANDAR, pedido.getEnvio());
	    assertEquals(1000f, pedido.calcularCosto());
	    assertEquals("El envio llegará en 5 días hábiles", pedido.estimacionDeEntrega());
	}
	
	@Test
	void testLaEstimacionDeEntregaEnEnvioEstandarEs5DiasSiLaDistanciaEsHasta20Inclusive() {
	
	    EnvioEstandar envio = new EnvioEstandar(correoStub);
	    
	    pedido.setDireccion(new Direccion("Calle 146", -34.58508d, -58.278418d));
	    pedido.setEnvio(envio);
	    
	    
	    assertEquals(19.998407557024596, pedido.getDireccion().distanciaHasta(sucursalUNQ.getDireccion()));
	    assertEquals("El envio llegará en 5 días hábiles", pedido.estimacionDeEntrega());
	}
	
	@Test
	void testLaEstimacionDeEntregaEnEnvioEstandarEs6DiasSiLaDistanciaEsMasDe20() {
	
	    EnvioEstandar envio = new EnvioEstandar(correoStub);
	    
	    pedido.setDireccion(new Direccion("Calle 189", -34.58504d, -58.278418d));
	    pedido.setEnvio(envio);
	    
	    
	    assertEquals(20.002855354090286, pedido.getDireccion().distanciaHasta(sucursalUNQ.getDireccion()));
	    assertEquals("El envio llegará en 6 días hábiles", pedido.estimacionDeEntrega());
	}
	
	@Test
	void testLaEstimacionDeEntregaEnEnvioEstandarEs6DiasSiLaDistanciaEsHasta100Inclusive() {
	
	    EnvioEstandar envio = new EnvioEstandar(correoStub);
	    
	    pedido.setDireccion(new Direccion("Calle 189", -33.86561d, -58.278418d));
	    pedido.setEnvio(envio);
	    
	    
	    assertEquals(99.99982142998493, pedido.getDireccion().distanciaHasta(sucursalUNQ.getDireccion()));
	    assertEquals("El envio llegará en 6 días hábiles", pedido.estimacionDeEntrega());
	}
	
	@Test
	void testLaEstimacionDeEntregaEnEnvioEstandarEs7DiasSiLaDistanciaEsMasDe100() {
	
	    EnvioEstandar envio = new EnvioEstandar(correoStub);
	    
	    pedido.setDireccion(new Direccion("Calle 189", -33.86560d, -58.278418d));
	    pedido.setEnvio(envio);
	    
	    
	    assertEquals(100.00093337925155, pedido.getDireccion().distanciaHasta(sucursalUNQ.getDireccion()));
	    assertEquals("El envio llegará en 7 días hábiles", pedido.estimacionDeEntrega());
	}
	
	@Test
	void testElCostoYEstimacionDeUnEnvioCuandoElPedidoEstaHechoComoExpress() {

	    EnvioExpress envio = new EnvioExpress(10, 5000f);

	    pedido.setEnvio(envio);

	    assertEquals(TipoEnvio.EXPRESS, pedido.getEnvio());
	    assertEquals(5000f, pedido.calcularCosto());
	    assertEquals("El envio llegará en 1 día hábil", pedido.estimacionDeEntrega());
	}


}
