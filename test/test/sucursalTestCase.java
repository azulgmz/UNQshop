package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Sistemas.Catalogo;
import Sistemas.Sucursal;

class sucursalTestCase {

	private int CUIT;
	private Catalogo catalogoCorrientes;
	private float dineroDisponible;
	private String direccion;
	private Sucursal sucursalCorrientes;
	
	@BeforeEach
	public void setUp() {
		catalogoCorrientes = new Catalogo();
	    sucursalCorrientes = new Sucursal(28062026, catalogoCorrientes, 100000f, "Avenida Corrientes 1661");
	}
	
	@Test
	void testLosDatosDeLaSucursal() {
		assertEquals(28062026, sucursalCorrientes.getCUIT());
		assertTrue(sucursalCorrientes.tieneCatalogo());
		assertEquals(100000f, sucursalCorrientes.getDineroDisponible());
		assertEquals("Avenida Corrientes 1661", sucursalCorrientes.getDireccion());
	}

}
