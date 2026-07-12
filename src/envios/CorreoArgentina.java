package envios;

import ubicacionGeografica.Direccion;

public class CorreoArgentina implements Correo{

	public float estimarEnvio(float peso, Direccion direccionEnvio) {
		return 0;
	} // Como es de libreria y no se debe de implementar hacemos que devuleva 0, para así en los test
	  // configurarlo a gusto
	  // No hacemos que sea Interfaz directamente porque no podriamos de instanciar la clase entonces
}