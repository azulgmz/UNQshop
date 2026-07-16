package NotaDeCredito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Stream;

import pedido.Pedido;

public class SistemaDeNotaDeCredito {
	
	private int                      nroDeNota;
	private ArrayList<NotaDeCredito> notasDeCredito;
	
	public SistemaDeNotaDeCredito() {
		this.nroDeNota      = 0;
		this.notasDeCredito = new ArrayList<NotaDeCredito>();
	}

	public void agregarNotaProducto(Pedido pedido, LocalDate fecha, float precioTotal, int CUIT) {
		nroDeNota++;
		
		NotaDeCreditoProducto notaDeCredito = new NotaDeCreditoProducto(nroDeNota, pedido, fecha, precioTotal, CUIT); 
		
		notasDeCredito.add(notaDeCredito);
	}

	public int cantidadDeNotasDeCreditos() {
		return notasDeCredito.size();
	}

	public boolean tieneNotaDeCreditoNro(int nro) {
		Stream<NotaDeCredito> notas = notasDeCredito.stream();
		return notas.anyMatch(n -> n.getNroDeNota() == nro);
	}

	public String detallesDe(int nro) {
		for(NotaDeCredito nota : notasDeCredito) {
			if (nota.getNroDeNota() == nro) {
				return nota.detalles();
			}
		}
		throw new IllegalArgumentException("No hay registro de alguna nota con ese numero");
	}

	public void agregarNotaProductoYEnvio(Pedido pedido, LocalDate fecha, float precioTotal, float precioEnvio,
			int CUIT) {
		nroDeNota++;
		
		NotaDeCreditoProductoYEnvio notaDeCredito = new NotaDeCreditoProductoYEnvio(nroDeNota, pedido, fecha, precioTotal, precioEnvio, CUIT); 
		
		notasDeCredito.add(notaDeCredito);
	}
	
}
