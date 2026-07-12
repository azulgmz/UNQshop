package envios;

import pedido.Pedido;
import ubicacionGeografica.Direccion;

public class EnvioEstandar implements Envio{
	
	private Correo correo;

	public EnvioEstandar(Correo correo) {
		this.correo = correo;
	}

	public TipoEnvio getTipo() {
		return TipoEnvio.ESTANDAR;
	}


	public float costoEnvio(Pedido pedido) {
		float peso = pedido.pesoTotal();
		Direccion destino = pedido.getDireccion();
		
		return correo.estimarEnvio(peso, destino);	
	}

	public String estimacionDeEntrega(Direccion origen, Direccion destino) {
		double distancia = origen.distanciaHasta(destino);
		
		if(distancia <= 20) {
			return "El envio llegará en 5 días hábiles";
		}else if(distancia <= 100) {
			return "El envio llegará en 6 días hábiles";
		}else {
			return "El envio llegará en 7 días hábiles";
		}
		
		
	}

}
