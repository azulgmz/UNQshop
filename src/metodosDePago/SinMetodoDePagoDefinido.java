package metodosDePago;

import pedido.ExceptionMsg;

public class SinMetodoDePagoDefinido implements MetodoDePago {

	public Boolean esSinDefinir() {
		return true;
	}
	public void procesarPago(float monto) {
		throw new ExceptionMsg("Metodo de pago no definido");
	}
}
