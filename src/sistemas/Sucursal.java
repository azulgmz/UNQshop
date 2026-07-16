package sistemas;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.BooleanSupplier;

import NotaDeCredito.SistemaDeNotaDeCredito;
import metodosDePago.MetodoDePago;
import notificadores.ComprobanteFiscal;
import pedido.Pedido;
import productos.Producto;
import ubicacionGeografica.Direccion;
import reportes.RegistroDeVentas;
import reportes.RegistradorDeVentasObserver;

public class Sucursal {
	
	private int 				          CUIT;
	private Catalogo 			          catalogo;
	private float 				          dineroDisponible;
	private Direccion 			          direccion;
	private ArrayList<ComprobanteFiscal>  comprobantesFiscales;
	private ArrayList<Sucursal>           sucursales;
	private ArrayList<Producto>           deposito;
	private SistemaDeNotaDeCredito        sistemaDeNotaDeCredito;
	private RegistroDeVentas              registroDeVentas;


	public Sucursal(int CUIT, Catalogo catalogo, float dineroDisponible, Direccion direccion, ArrayList<Sucursal> sucursales, RegistroDeVentas registroDeVentas) {
		this.CUIT = CUIT;
		this.catalogo = catalogo;
		this.dineroDisponible = dineroDisponible; 
		this.direccion = direccion;
		this.sucursales = sucursales;
		this.registroDeVentas = registroDeVentas;
		this.comprobantesFiscales = new ArrayList<ComprobanteFiscal>();
		this.deposito = new ArrayList<Producto>();
		this.sistemaDeNotaDeCredito = new SistemaDeNotaDeCredito();

	}

	public int getCUIT() {
		return CUIT;
	}

	public Boolean tieneCatalogo() {
		return catalogo != null;
	}

	public float getDineroDisponible() {
		return dineroDisponible;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public Pedido crearPedido(String mail, Direccion direccion) {
		ArrayList<Producto> listaDeProductos = new ArrayList<>();
		Pedido pedido = new Pedido(this, listaDeProductos, mail, direccion);
		pedido.agregarObserver(new RegistradorDeVentasObserver(registroDeVentas));
		
		return pedido;
	}


	public Catalogo getCatalogo() {
		return catalogo;
	}

	public void agregarSucursal(Sucursal sucursal) {
		sucursales.add(sucursal);
	}

	public double distanciaDeSucursalMasLejanaABuscarUnStock(ArrayList<Producto> listaDeProductos) {
		double distanciaMasLejana = 0;
		for(Producto p : listaDeProductos) {
			if(!tieneEnElDeposito(p)) {
					Sucursal otraSucursal = sucursalConStockDe(p);
					double distancia = direccion.distanciaHasta(otraSucursal.getDireccion());
					if (distancia > distanciaMasLejana) {
						distanciaMasLejana = distancia;
					}
			} 
		}
		return distanciaMasLejana;
	}
	

	public boolean tieneEnElDeposito(Producto producto) {
		return deposito.contains(producto);
	}

	public Sucursal sucursalConStockDe(Producto producto) {
		int cantidad = sucursales.size();
		
		for(int i=0; i < cantidad; i++) {
			ArrayList<Producto> deposito = sucursales.get(i).getDeposito();
			if(deposito.contains(producto)) {
					return sucursales.get(i);
				}		
		}
		return null; // Se deja null porque no haya una sucursal con el stock disponible
	}

	private ArrayList<Producto> getDeposito() {
		return deposito;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	public void eliminarSucursal(Sucursal sucursal) {
		sucursales.remove(sucursales.indexOf(sucursal));
	}

	public void reservarEnElDeposito(ArrayList<Producto> listaDeProductos) {
		for(Producto p : listaDeProductos) {
			deposito.add(p);
			catalogo.descontarStockDe(p);
		}
	}


	public void generarNotaDeCreditoDeProductos(Pedido pedido) {
		sistemaDeNotaDeCredito.agregarNotaProducto(pedido, LocalDate.now(), pedido.precioTotal(), CUIT);
	}

	public int cantidadDeNotasDeCreditos() {
		return sistemaDeNotaDeCredito.cantidadDeNotasDeCreditos();
	}

	public boolean tieneNotaDeCreditoNro(int nro) {
		return sistemaDeNotaDeCredito.tieneNotaDeCreditoNro(nro);
	}

	public String detallesNotaDeCredito(int nro) {
		return sistemaDeNotaDeCredito.detallesDe(nro);
	}

	public void generarNotaDeCreditoDeProductosYEnvio(Pedido pedido) {
		sistemaDeNotaDeCredito.agregarNotaProductoYEnvio(pedido, LocalDate.now(), pedido.precioTotal(), pedido.calcularCosto(), CUIT);
	}




	public ArrayList<ComprobanteFiscal> getComprobantesFiscales(){
		return comprobantesFiscales;
	}

	public boolean tieneSucursal(Sucursal otraSucursal) {
		return sucursales.contains(otraSucursal);
	}

	public void agregarComprobanteFiscal(Pedido pedido, LocalDate fechaEmision, MetodoDePago metodoDePago, int nroComprobante,
			String claseDelComprobante) {
		comprobantesFiscales.add(new ComprobanteFiscal(this, pedido, fechaEmision, metodoDePago, nroComprobante, claseDelComprobante));
		
	}

	public boolean tieneComprobanteFiscal(int nroComprobante) {
		return comprobantesFiscales.stream().anyMatch(cf -> cf.getNroComprobante() == nroComprobante);

	}

	public String detallesDelComprobanteFiscal(int nroComprobante) {
		for(ComprobanteFiscal cf : comprobantesFiscales) {
			if(cf.getNroComprobante() == nroComprobante) {
				return cf.detalles();
			}
		}
		throw new IllegalArgumentException("No existe ningun comprobante fiscal con numero  " + nroComprobante);
	}

}
