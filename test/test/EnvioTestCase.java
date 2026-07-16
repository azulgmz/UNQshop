package test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import envios.CorreoArgentina;
import envios.EnvioEstandar;
import envios.EnvioExpress;
import envios.RetiroEnSucursal;
import envios.TipoEnvio;
import metodosDePago.TarjetaDeCredito;
import pedido.Pedido;
import productos.Atributo;
import productos.SistemaDeProductos;
import sistemas.Catalogo;
import sistemas.Sucursal;
import ubicacionGeografica.Direccion;
import reportes.RegistroDeVentas;

class EnvioTestCase {
	
	private SistemaDeProductos sistemaDeProductos;
	private Catalogo            catalogoUNQ, catalogoCorrientes;
	private Sucursal            sucursalUNQ, sucursalCorrientes;
	private Pedido              pedido;
	private CorreoArgentina     correoMock;
	private ArrayList<Sucursal> sucursales;
	private ArrayList<Atributo> atributosDummy;
	private TarjetaDeCredito    tarjetaDummy;

	
	@BeforeEach
	public void setUp() {
		sistemaDeProductos = new SistemaDeProductos();
		
		correoMock     = mock(CorreoArgentina.class);
		catalogoUNQ    = new Catalogo();
		sucursales     = new ArrayList<Sucursal>();
	    sucursalUNQ    = new Sucursal(28062026, catalogoUNQ, 100000f, new Direccion("Roque Sáenz Peña 124", -34.76493d, -58.278418d), sucursales, new RegistroDeVentas());
	    
	    atributosDummy = new ArrayList<Atributo>();
	    
	    pedido = sucursalUNQ.crearPedido("juan@gmail.com", new Direccion("9 de Julio 217", -34.712445d, -58.284493d));
	    
	    catalogoCorrientes = new Catalogo();
		sucursalCorrientes = new Sucursal(895422, catalogoCorrientes, 20000, new Direccion("Direccion a 20km aprox", -34.58508d, -58.278418d), sucursales, new RegistroDeVentas());
		
		sistemaDeProductos.agregarSucursal(sucursalUNQ);
		sistemaDeProductos.agregarSucursal(sucursalCorrientes);
		
		sistemaDeProductos.registrarIndividual("Monitor", "Snapdragon", "Perifericos", atributosDummy, 8900f, 100, 2000f);
		
		sucursalUNQ.agregarSucursal(sucursalCorrientes);
		
		sucursalCorrientes.agregarSucursal(sucursalUNQ);
		
		tarjetaDummy = mock(TarjetaDeCredito.class);
		
		
	}             
	
	@Test
	void testElCostoYEstimacionDeUnEnvioCuandoElPedidoEstaHechoComoEstandar() {
		
		when(correoMock.estimarEnvio(pedido.pesoTotal(), pedido.getDireccion())).thenReturn(1000f);

	    EnvioEstandar envio = new EnvioEstandar(correoMock);

	    pedido.setEnvio(envio);

	    assertEquals(TipoEnvio.ESTANDAR, pedido.getEnvio());
	    assertEquals(1000f, pedido.calcularCosto());
	    verify(correoMock).estimarEnvio(pedido.pesoTotal(), pedido.getDireccion());
	    assertEquals("El envio llegará en 5 días hábiles", pedido.estimacionDeEntrega());
	}
	
	@Test
	void testLaEstimacionDeEntregaEnEnvioEstandarEs5DiasSiLaDistanciaEsHasta20Inclusive() {
	
	    EnvioEstandar envio = new EnvioEstandar(correoMock);
	    
	    pedido.setDireccion(new Direccion("Calle 146", -34.58508d, -58.278418d));
	    pedido.setEnvio(envio);
	    
	    
	    assertEquals(19.998407557024596, pedido.getDireccion().distanciaHasta(sucursalUNQ.getDireccion()));
	    assertEquals("El envio llegará en 5 días hábiles", pedido.estimacionDeEntrega());
	}
	
	@Test
	void testLaEstimacionDeEntregaEnEnvioEstandarEs6DiasSiLaDistanciaEsMasDe20() {
	
	    EnvioEstandar envio = new EnvioEstandar(correoMock);
	    
	    pedido.setDireccion(new Direccion("Calle 189", -34.58504d, -58.278418d));
	    pedido.setEnvio(envio);
	    
	    
	    assertEquals(20.002855354090286, pedido.getDireccion().distanciaHasta(sucursalUNQ.getDireccion()));
	    assertEquals("El envio llegará en 6 días hábiles", pedido.estimacionDeEntrega());
	}
	
	@Test
	void testLaEstimacionDeEntregaEnEnvioEstandarEs6DiasSiLaDistanciaEsHasta100Inclusive() {
	
	    EnvioEstandar envio = new EnvioEstandar(correoMock);
	    
	    pedido.setDireccion(new Direccion("Calle 189", -33.86561d, -58.278418d));
	    pedido.setEnvio(envio);
	    
	    
	    assertEquals(99.99982142998493, pedido.getDireccion().distanciaHasta(sucursalUNQ.getDireccion()));
	    assertEquals("El envio llegará en 6 días hábiles", pedido.estimacionDeEntrega());
	}
	
	@Test
	void testLaEstimacionDeEntregaEnEnvioEstandarEs7DiasSiLaDistanciaEsMasDe100() {
	
	    EnvioEstandar envio = new EnvioEstandar(correoMock);
	    
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
	
	@Test
	void testElCostoYEstimacionDeUnEnvioCuandoElPedidoEstaHechoComoRetiroEnSucursal() {
		
	    RetiroEnSucursal envio = new RetiroEnSucursal(sucursalUNQ, pedido);
	    sistemaDeProductos.registrarIndividual("Monitor", "Snapdragon", "Perifericos", atributosDummy, 8900f, 100, 2000f);
	    
	    pedido.agregarProducto(catalogoUNQ.buscarProducto(1));
	    pedido.confirmarPedido(tarjetaDummy, envio);

	    assertEquals(TipoEnvio.RETIROENSUCURSAL, pedido.getEnvio());
	    assertEquals(0f, pedido.calcularCosto());
	    assertEquals("El pedido se puede retirar hoy", pedido.estimacionDeEntrega());
	}
	
	@Test
	void testLaEstimacionDeEntregaEsDe1DiaCuandoLaSucursalNoTieneElProductoYLaOtraSucursalEstaA20OMenosKilometros() {
		
	    confirmarPedidoConEnvio();

	    assertEquals("El pedido se puede retirar en 1 día hábil", pedido.estimacionDeEntrega());

	}
	
	@Test
	void testLaEstimacionDeEntregaEsDe2DiaCuandoLaSucursalNoTieneElProductoYLaOtraSucursalEstaA100OMenosKilometros() {
		
		Direccion direccion = new Direccion("Direccion a 100km aprox", -34.58504d, -58.278418d);
		sucursalCorrientes.setDireccion(direccion);
		
	    confirmarPedidoConEnvio();

	    assertEquals("El pedido se puede retirar en 2 días hábiles", pedido.estimacionDeEntrega());

	}
	
	@Test
	void testLaEstimacionDeEntregaEsDe2DiaCuandoLaSucursalNoTieneElProductoYLaOtraSucursalEstaAMasDe100Km() {

		Direccion direccion = new Direccion("Direccion a +100km", -33.86560d, -58.278418d);
		sucursalCorrientes.setDireccion(direccion);
		
		confirmarPedidoConEnvio();

	    assertEquals("El pedido se puede retirar en 3 días hábiles", pedido.estimacionDeEntrega());

	}

	private void confirmarPedidoConEnvio() {
		RetiroEnSucursal envio = new RetiroEnSucursal(sucursalCorrientes, pedido);

	    pedido.agregarProducto(catalogoUNQ.buscarProducto(1));
	    pedido.confirmarPedido(tarjetaDummy, envio);
	}
	
	@Test
	void testNoSePuedeEstimarLaEntregaSiEsSinEnvioDefinido() {
		
		IllegalArgumentException error = assertThrows(IllegalArgumentException.class,() -> pedido.estimacionDeEntrega());
		
	    assertEquals("Se debe elegir un tipo de envio para poder estimar la entrega", error.getMessage());
	    assertEquals(pedido.getEnvio(), TipoEnvio.SINENVIODEFINIDO);

	}
	
	@Test
	void testNoSePuedeCalcularElCostoDeEnvioSiEsSinEnvioDefinido() {
		
		IllegalArgumentException error = assertThrows(IllegalArgumentException.class,() -> pedido.calcularCosto());
		
	    assertEquals("Se debe elegir un tipo de envio para poder calcular el costo de envio", error.getMessage());
	    assertEquals(pedido.getEnvio(), TipoEnvio.SINENVIODEFINIDO);

	}
	

}
