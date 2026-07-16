package sistemas;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.BooleanSupplier;

import NotaDeCredito.NotaDeCreditoProducto;
import NotaDeCredito.SistemaDeNotaDeCredito;
import pedido.Pedido;
import productos.Producto;
import ubicacionGeografica.Direccion;

public class Sucursal {
	
	private int 				     CUIT;
	private Catalogo 			     catalogo;
	private float 				     dineroDisponible;
	private Direccion 			     direccion;
	private ArrayList<String> 	     comprobantesFiscales;
	private ArrayList<Sucursal>      sucursales;
	private ArrayList<Producto>      deposito;
	private SistemaDeNotaDeCredito   sistemaDeNotaDeCredito;

	public Sucursal(int CUIT, Catalogo catalogo, float dineroDisponible, Direccion direccion, ArrayList<Sucursal> sucursales) {
		this.CUIT                   = CUIT;
		this.catalogo               = catalogo;
		this.dineroDisponible       = dineroDisponible; 
		this.direccion              = direccion;
		this.comprobantesFiscales   = new ArrayList<>();
		this.sucursales             = sucursales;
		this.deposito               = new ArrayList<Producto>();
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



}
