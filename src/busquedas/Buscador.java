package busquedas;

public class Buscador {
	
	private TipoDeBusqueda tipoDeBusqueda;
	
	public Buscador() {
		this.tipoDeBusqueda = new SinTipoDeBusquedaDefinido();
	}

	public void setTipoDeBusqueda(TipoDeBusqueda nuevoTipo) {
		tipoDeBusqueda = nuevoTipo;
	}

	public TipoDeBusqueda getTipoDeBusqueda() {
		return tipoDeBusqueda;
	}
	
}
