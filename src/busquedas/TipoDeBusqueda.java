package busquedas;

import java.util.List;

import productos.Producto;
import sistemas.Catalogo;

public interface TipoDeBusqueda {

	List<Producto> buscarProductos(Catalogo catalogo);


}
