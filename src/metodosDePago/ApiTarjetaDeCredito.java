package metodosDePago;

public interface ApiTarjetaDeCredito {

	boolean validarDatos(int numeroTarjeta, int cvv, String Vencimiento);
	
	boolean preAutorizar(int numeroTarjeta, float monto);
	
	int transferirInmediato(int numeroTarjeta, float monto);
	
}
