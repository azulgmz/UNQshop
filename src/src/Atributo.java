package src;

public class Atributo {
	private String tipo, especificacion;
	
	public Atributo(String tipo, String especificacion) {
		this.tipo = tipo;
		this.especificacion = especificacion;
	}
	
	public String getTipo() {
		return tipo;
	}

	public String getEspecificacion() {
		return especificacion;
	}

}
