package metodosDePago;

public interface MetodoDePago {

	boolean esSinDefinir();
	
	void procesarPago(float monto);
}
