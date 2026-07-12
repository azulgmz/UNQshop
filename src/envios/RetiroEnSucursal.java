package envios;

import ubicacionGeografica.Direccion;
import pedido.Pedido;

public class RetiroEnSucursal implements Envio {

	public TipoEnvio getTipo() {
		return TipoEnvio.RETIROENSUCURSAL;
	}
	public String estimacionDeEntrega(Direccion dirEnvio, Direccion dirDestino) {
		return "Ninguna";
	}
	public float costoEnvio(Pedido pedido) {
		return 0;
	}
}
