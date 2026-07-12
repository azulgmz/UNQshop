package metodosDePago;

import pedido.ExceptionMsg;

public abstract class MetodoDePagoTemplate implements MetodoDePago {

	@Override
	public Boolean esSinDefinir() {
		return false;
	}

	@Override
	public void procesarPago(float monto) {
		if (!validarDatos(monto)) {
			throw new ExceptionMsg("Datos de pago inválidos");
		}
		if (!reservarFondos(monto)) {
			throw new ExceptionMsg("No se pudieron reservar los fondos");
		}
		int codigoTransaccion = ejecutarTransaccion(monto);
		notificarResultado(codigoTransaccion);


	}
	
	protected abstract boolean validarDatos(float monto);
	
	protected abstract boolean reservarFondos(float monto);
	
	protected abstract int ejecutarTransaccion(float monto);
	
	public void notificarResultado(int codigoTransaccion) {
		
	}

}
