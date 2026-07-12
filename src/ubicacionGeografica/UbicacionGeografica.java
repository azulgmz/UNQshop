package ubicacionGeografica;

public class UbicacionGeografica {
	
	private double latitud, longitud;

	public UbicacionGeografica(double latitud, double longitud) {
	this.latitud = latitud;		// Norte(+)/Sur(-)
	this.longitud = longitud;	// Este(+)/Oeste(-)
	}
	
	public double getLatitud() {
		return latitud;
	}
	
	public double getLongitud() {
		return longitud;
	}
	
	public double distanciaHasta(Direccion destino) {
		// Se utiliza la Fórmula de Haversine para calcular la distancia
		double radioTierra = 6371; // Radio aprox. de la tierra
		
		double latitudOrigen   = Math.toRadians(latitud);
		double longitudOrigen  = Math.toRadians(longitud);
		double latitudDestino  = Math.toRadians(destino.getLatitud());
		double longitudDestino = Math.toRadians(destino.getLongitud());
		// Convertimos todas las latitudes y longitudes en radiantes para poder trabajar con coseno, tangente y seno
		
		
		double latitudDiferencia  = latitudDestino - latitudOrigen;
		double longitudDiferencia = longitudDestino - longitudOrigen;
		// Se calcula la diferencia entre las coordenadas
		
		double a = Math.sin(latitudDiferencia / 2) * Math.sin(latitudDiferencia / 2) // Calcula sin^2(latitudDiferencia/2)
				   + Math.cos(latitudOrigen)  // Calcula cos(latitudOrigen)
				   * Math.cos(latitudDestino) // Calcula el cos del destino
				   * Math.sin(longitudDiferencia / 2) * Math.sin(longitudDiferencia / 2); // Calcula sin^2(longitudDiferencia/2)
		// Valor intermedio de la fórmula
		
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)); // Calcula el angulo entre ambos puntos
				  // Arcotangente       raiz cuadrada
				//(devuelve un angulo)
		// Valor del ángulo central entre ambos puntos
		
		return radioTierra * c;
		// Entonces devuelvo la distancia = radio * angulo
	}
}
