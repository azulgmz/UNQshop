package metodosDePago;

import java.util.List;
import java.util.ArrayList;

public class TransferenciaBancaria extends MetodoDePagoTemplate {

	private ApiTransferenciaBancaria api;
	private int cbu;
	private boolean esProgramada;
	private List<ComprobanteCBU> comprobantesGenerados = new ArrayList<>();
	
	public TransferenciaBancaria(ApiTransferenciaBancaria api, int cbu, boolean esProgramada) {
		this.api = api;
		this.cbu = cbu;
		this.esProgramada = esProgramada;
	}
	
	@Override
	protected boolean validarDatos(float monto) {
		return api.validarCbu(cbu);
	}

	@Override
	protected boolean reservarFondos(float monto) {
		return true;
	}

	@Override
	protected int ejecutarTransaccion(float monto) {
		return api.transferir(cbu, monto, esProgramada);

	}

	public void notificarResultado(int codigoTransaccion) {
		comprobantesGenerados.add(new ComprobanteCBU(cbu, codigoTransaccion));
	}
	public List<ComprobanteCBU> getComprobantesGenerados() {
		return comprobantesGenerados;
	}


	public TipoMetodoDePago TipoMetodoDePago() {
		return TipoMetodoDePago.TRANSFERENCIA_BANCARIA;
	}
}
