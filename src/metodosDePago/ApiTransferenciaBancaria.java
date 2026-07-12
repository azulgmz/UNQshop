package metodosDePago;

public interface ApiTransferenciaBancaria {

	boolean validarCbu(int cbu);
	
	int transferir(int cbu, float monto, boolean esProgramada);

	
}
