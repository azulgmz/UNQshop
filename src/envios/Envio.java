package envios;

import pedido.Pedido;
import ubicacionGeografica.Direccion;

public interface Envio {
	
	public TipoEnvio getTipo();

	public float costoEnvio(Pedido pedido);

	public String estimacionDeEntrega(Direccion origen, Direccion destino);
}
