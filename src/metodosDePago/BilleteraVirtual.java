package metodosDePago;

import java.util.List;
import java.util.ArrayList;

public class BilleteraVirtual extends MetodoDePagoTemplate {
	
	private ApiBilleteraVirtual api;
	private int numeroCuenta;
	private List<String> notificacionesEnviadas = new ArrayList<>();
	
	public BilleteraVirtual(ApiBilleteraVirtual api, int numeroCuenta) {
		this.api = api;
		this.numeroCuenta = numeroCuenta;
	}
	@Override
	protected boolean validarDatos(float monto) {
		return api.validarSaldoSuficiente(numeroCuenta, monto);
	}
	@Override
	protected boolean reservarFondos(float monto) {
		return api.bloquearSaldo(numeroCuenta, monto);
	}
	@Override
	protected void ejecutarTransaccion(float monto) {
		api.acreditarTransaccion(numeroCuenta, monto);
	}
	@Override
	public void notificarResultado(int codigoTransaccion) {
		String mensaje = "Pago realizado. Codigo:" + codigoTransaccion;
		api.enviarNotificacion(numeroCuenta, mensaje);
		notificacionesEnviadas.add(mensaje);
	}
	public List<String> getNotificacionesEnviadas() {
		return notificacionesEnviadas;
	}
}
