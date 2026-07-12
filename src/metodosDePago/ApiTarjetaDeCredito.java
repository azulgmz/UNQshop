package metodosDePago;

public interface ApiTarjetaDeCredito {

	boolean validarDatos(int numeroTarjeta, int cvv, String Vencimiento);
	
	boolean preAutorizar(int numeroTarjeta, float monto);
	
	void transferirInmediato(int numeroTarjeta, float monto);
	
}
