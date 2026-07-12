package envios;

import pedido.Pedido;
import ubicacionGeografica.Direccion;

public class SinEnvioDefinido implements Envio {

	public TipoEnvio getTipo() {
		return TipoEnvio.SINENVIODEFINIDO;
	}
	public float costoEnvio(Pedido pedido) {
		return 0;
	}

	public String estimacionDeEntrega(Direccion origen, Direccion destino) {
		return "Sin Envio";
	}

}
