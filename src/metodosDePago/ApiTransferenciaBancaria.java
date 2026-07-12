package metodosDePago;

public interface ApiTransferenciaBancaria {

	boolean validarCbu(int cbu);
	
	void transferir(int cbu, float monto, boolean esProgramada);
	
}
