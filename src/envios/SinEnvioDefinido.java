package envios;

import pedido.Pedido;
import ubicacionGeografica.Direccion;

public class SinEnvioDefinido implements Envio {

	public TipoEnvio getTipo() {
		return TipoEnvio.SINENVIODEFINIDO;
	}
	
	public float costoEnvio(Pedido pedido) {
		throw new IllegalArgumentException("Se debe elegir un tipo de envio para poder calcular el costo de envio");
	}

	public String estimacionDeEntrega(Direccion origen, Direccion destino) {
		throw new IllegalArgumentException("Se debe elegir un tipo de envio para poder estimar la entrega");
	}

}
