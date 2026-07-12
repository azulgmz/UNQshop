package envios;

import pedido.Pedido;
import ubicacionGeografica.Direccion;

public class EnvioExpress implements Envio{

	private int   porcentajeFijo;
	private float cargoBase;
	
	public EnvioExpress(int porcentajeFijo, float cargoBase) {
		this.porcentajeFijo = porcentajeFijo;
		this.cargoBase = cargoBase;
	}

	public TipoEnvio getTipo() {
		return TipoEnvio.EXPRESS;
	}

	public float costoEnvio(Pedido pedido) {
		return (pedido.precioTotal() * (porcentajeFijo / 100)) + cargoBase;
	}

	public String estimacionDeEntrega(Direccion origen, Direccion destino) {
		return "El envio llegará en 1 día hábil";
	}

}
