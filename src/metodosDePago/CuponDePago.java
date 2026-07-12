package metodosDePago;

public class CuponDePago {

	private int codigoTransaccion;
	
	public CuponDePago(int codigoTransaccion) {
		this.codigoTransaccion = codigoTransaccion;
	}
	public int getCodigoTransaccion() {
		return codigoTransaccion;
	}
	public String imprimir() {
		return "Cupon de Pago. N° Transaccion: " + codigoTransaccion;
	}
	
}
