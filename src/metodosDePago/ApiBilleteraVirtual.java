package metodosDePago;

public interface ApiBilleteraVirtual {

	boolean validarSaldoSuficiente(int numeroCuenta , float monto);
	
	boolean bloquearSaldo(int numeroCuenta , float monto);
	

	int acreditarTransaccion(int numeroCuenta, float monto);

	
	void enviarNotificacion(int numeroCuenta, String mensaje);
}
