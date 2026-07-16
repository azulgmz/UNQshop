package notificadores;

import java.time.LocalDate;
import java.util.ArrayList;

import metodosDePago.MetodoDePago;
import pedido.Pedido;
import productos.Producto;
import sistemas.Sucursal;

public class ComprobanteFiscal {

	private Sucursal     sucursal;
	private Pedido       pedido;
	private LocalDate    fechaEmision;
	private MetodoDePago metodoDePago;
	private int          nroComprobante;
	private String       claseDelComprobante;
	
	
	public ComprobanteFiscal(Sucursal sucursal, Pedido pedido, LocalDate fechaEmision, MetodoDePago metodoDePago,
			int nroComprobante, String claseDelComprobante) {
		this.sucursal            = sucursal;
		this.pedido              = pedido;
		this.fechaEmision        = fechaEmision;
		this.metodoDePago        = metodoDePago;
		this.nroComprobante      = nroComprobante;
		this.claseDelComprobante = claseDelComprobante;
	}
	
	
	public int getNroComprobante() {
		return nroComprobante;
	}


	public String detalles() {
		String detalles = "						     ORIGINAL					"  + "\n"
						+ "							   ["+ claseDelComprobante  +"]		" + "\n"
						+ "Receptor: " + pedido.getMail() + "			|		"  + "Factura(" + nroComprobante +")"  + "\n"
						+ "Direccion: " + pedido.getDireccion().getDireccion() + "		|		" + "Fecha emision: " + fechaEmision + "\n"
						+ "								|		Direcion: " + sucursal.getDireccion().getDireccion() + "\n"
						+ "								|		CUIT: " + sucursal.getCUIT() + "\n"
						+ "------------------------------------------------------------------------" + "\n"
						+ "[Codigo]                [Nombre]                [Precio]" + "\n";
		
		ArrayList<Producto> productos = pedido.getListaDeProductos();
		for(Producto p : productos) {
			detalles += p.getSKU() + "        		  	  	 " + p.getNombre() + "       	  	    " + p.precioFinal() + "\n";
		}
		
		detalles += "------------------------------------------------------------------------" + "\n";
		detalles += "Importe total: " + pedido.calcularCosto() + "\n";
		detalles += "Metodo de pago: " + metodoDePago.TipoMetodoDePago();
		
		return detalles;
	}


}
