package metodosDePago;

import pedido.ExceptionMsg;

public class SinMetodoDePagoDefinido implements MetodoDePago {

	public boolean esSinDefinir() {
		return true;
	}
	public void procesarPago(float monto) {
		throw new ExceptionMsg("Metodo de pago no definido");
	}

	public TipoMetodoDePago TipoMetodoDePago() {
		return TipoMetodoDePago.SIN_METODO_DE_PAGO;
	}
}
