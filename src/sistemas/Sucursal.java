package sistemas;

import java.util.ArrayList;

import pedido.Pedido;
import productos.Producto;
import ubicacionGeografica.Direccion;

public class Sucursal {
	
	private int 				CUIT;
	private Catalogo 			catalogo;
	private float 				dineroDisponible;
	private Direccion 			direccion;
	private ArrayList<String> 	comprobantesFiscales;
	private ArrayList<Sucursal> sucursales;
	private ArrayList<Producto> deposito;

	public Sucursal(int CUIT, Catalogo catalogo, float dineroDisponible, Direccion direccion, ArrayList<Sucursal> sucursales) {
		this.CUIT = CUIT;
		this.catalogo = catalogo;
		this.dineroDisponible = dineroDisponible; 
		this.direccion = direccion;
		this.comprobantesFiscales = new ArrayList<>();
		this.sucursales = sucursales;
		this.deposito = new ArrayList<Producto>();
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
			if(!(catalogo.tieneProducto(p.getSKU())) && catalogo.cantidadDe(p.getSKU()) == 0) {
					Sucursal otraSucursal = sucursalConStockDe(p);
				
					double distancia = this.getDireccion().distanciaHasta(otraSucursal.getDireccion());
				
					if (distancia > distanciaMasLejana) {
						distanciaMasLejana = distancia;
				}
			} 
		}
		return distanciaMasLejana;
	}
	

	public Sucursal sucursalConStockDe(Producto producto) {
		int cantidad = sucursales.size();
		
		for(int i=0; i < cantidad; i++) {
			Catalogo catalogo2 = sucursales.get(i).getCatalogo();
			if(catalogo2.tieneProducto(producto.getSKU()) && catalogo2.cantidadDe(producto.getSKU()) > 0) {
					return sucursales.get(i);
				}		
		}
		return null; // Se deja null porque no haya una sucursal con el stock disponible
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

}
