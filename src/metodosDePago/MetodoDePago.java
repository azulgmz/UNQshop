package metodosDePago;

public interface MetodoDePago {

	Boolean esSinDefinir();
	
	void procesarPago(float monto);
}
