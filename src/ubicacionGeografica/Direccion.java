package ubicacionGeografica;

public class Direccion extends UbicacionGeografica{

	private String direccion;
	
	public Direccion(String direccion, double latitud, double longitud) {
		super(latitud, longitud);
		this.direccion = direccion;
	}

	
	
}
