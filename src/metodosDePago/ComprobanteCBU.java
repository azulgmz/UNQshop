package metodosDePago;

public class ComprobanteCBU {

	private int cbu;
	private int numeroOperacion;
	
	public ComprobanteCBU(int cbu, int numeroOperacion) {
		this.cbu = cbu;
		this.numeroOperacion = numeroOperacion;
	}
	public int getCbu( ) {
		return cbu;
	}
	public int getNumeroOperacion() {
		return numeroOperacion;
	}
}
