package metodosDePago;

public abstract class MetodoDePagoTemplate implements MetodoDePago {

	@Override
	public Boolean esSinDefinir() {
		return false;
	}

	@Override
	public void procesarPago(float monto) {
		

	}
	
	protected abstract boolean validarDatos(float monto);
	
	protected abstract boolean reservarFondos(float monto);
	
	protected abstract void ejecutarTransaccion(float monto);
	
	public void notificarResultado(int codigoTransaccion) {
		
	}

}
