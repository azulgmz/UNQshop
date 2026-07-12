package envios;

import ubicacionGeografica.Direccion;

public interface Correo {
	
	public float estimarEnvio(float peso, Direccion direccionEnvio);
}	

// Creamos la clase Correo como interfaz para dejar la posibilidad de poder usar otros correos para el envio estandar
