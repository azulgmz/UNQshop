package envios;

import java.util.ArrayList;

import pedido.Pedido;
import sistemas.Sucursal;
import ubicacionGeografica.Direccion;

public class RetiroEnSucursal implements Envio {
	
	private Sucursal            sucursal;
	private Pedido              pedido;
	
	public RetiroEnSucursal(Sucursal sucursal, Pedido pedido) {
		this.sucursal = sucursal;
		this.pedido = pedido;
	}

	public TipoEnvio getTipo() {
		return TipoEnvio.RETIROENSUCURSAL;
	}

	public float costoEnvio(Pedido pedido) {
		return 0;
	}

	public String estimacionDeEntrega(Direccion origen, Direccion destino) {
		double distancia = sucursal.distanciaDeSucursalMasLejanaABuscarUnStock(pedido.getListaDeProductos());
		
		if(distancia == 0) {
			return "El pedido se puede retirar hoy";
		}else if(distancia <= 20) {
			return "El pedido se puede retirar en 1 día hábil";
		}else if(distancia <= 100) {
			return "El pedido se puede retirar en 2 días hábiles";
		}else {
			return "El pedido se puede retirar en 3 días hábiles";
		}
		
		
		
		
	}

}
