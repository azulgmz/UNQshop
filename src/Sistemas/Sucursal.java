package Sistemas;

public class Sucursal {
	
	private int CUIT;
	private Catalogo catalogo;
	private float dineroDisponible;
	private String direccion;

	public Sucursal(int CUIT, Catalogo catalogo, float dineroDisponible, String direccion) {
		this.CUIT = CUIT;
		this.catalogo = catalogo;
		this.dineroDisponible = dineroDisponible; 
		this.direccion = direccion;
	}

	public int getCUIT() {
		return CUIT;
	}

	public Boolean tieneCatalogo() {
		return catalogo != null;
	}

	public float getDineroDisponible() {
		return dineroDisponible;
	}

	public String getDireccion() {
		return direccion;
	}

}
