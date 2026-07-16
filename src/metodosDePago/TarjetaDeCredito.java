package metodosDePago;

import java.util.List;
import java.util.ArrayList;

public class TarjetaDeCredito extends MetodoDePagoTemplate {

	private ApiTarjetaDeCredito api;
	private int numeroTarjeta;
	private int cvv;
	private String vencimiento;
	private List<CuponDePago> cuponesGenerados = new ArrayList<>();
	
	
	public TarjetaDeCredito(ApiTarjetaDeCredito api, int numeroTarjeta, int cvv, String vencimiento) {
		this.api = api;
		this.numeroTarjeta = numeroTarjeta;
		this.cvv = cvv;
		this.vencimiento = vencimiento;
	}
	
	@Override
	protected boolean validarDatos(float monto) {
		return api.validarDatos(numeroTarjeta, cvv, vencimiento);
	}

	@Override
	protected boolean reservarFondos(float monto) {
		return api.preAutorizar(numeroTarjeta, monto);
	}

	@Override
	protected int ejecutarTransaccion(float monto) {
		return api.transferirInmediato(numeroTarjeta, monto);
	}
	@Override 
	public void notificarResultado(int codigoTransaccion) {
		cuponesGenerados.add(new CuponDePago(codigoTransaccion));
	}
	public List<CuponDePago> getCuponesGenerados() {
		return cuponesGenerados;
	}

	@Override
	public TipoMetodoDePago TipoMetodoDePago() {
		return TipoMetodoDePago.TARJETA_DE_CREDITO;
	}
}
