package test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import envios.RetiroEnSucursal;
import metodosDePago.ApiBilleteraVirtual;
import metodosDePago.BilleteraVirtual;
import pedido.*;
import notificadores.*;
import productos.Atributo;
import productos.Individual;
import productos.Producto;
import productos.SistemaDeProductos;
import reportes.RegistroDeVentas;
import sistemas.Catalogo;
import sistemas.Sucursal;
import ubicacionGeografica.Direccion;

class NotificadoresTestCase {


    private static class MailSenderFake implements MailSender {
        final List<String> destinatarios = new ArrayList<>();

        @Override
        public void enviarMail(String direccionDestino, String titulo, String mensaje, Object adjunto) {
            destinatarios.add(direccionDestino);
        }
    }

    private Pedido                   pedido;
    private MailSenderFake           mailSender;
    private NotificadorEmailObserver notificadorEmail;
    private GeneradorFacturaObserver generadorFactura;
    private FidelizaciónObserver     fidelizacion;
    private Sucursal                 sucursalUNQ;
    private Direccion                direccionUNQ;
    private SistemaDeProductos		 sistemaDP;
    private Catalogo                 catalogoUNQ;

    @BeforeEach
    void setUp() {
    	sistemaDP = new SistemaDeProductos();
    	
    	catalogoUNQ			                 = new Catalogo();
		ArrayList<Sucursal> sucursalesDummy  = new ArrayList<Sucursal>();
		direccionUNQ                         = new Direccion("Roque Sáenz Peña 124", -34.76493d, -58.278418d);
		sucursalUNQ                          = new Sucursal(28062026, catalogoUNQ, 100000f, direccionUNQ, sucursalesDummy, new RegistroDeVentas());
        pedido                               = new Pedido(sucursalUNQ, new ArrayList<Producto>(), "ana@mail.com", new Direccion("9 de Julio 217", -34.712445d, -58.284493d));
        
        mailSender = new MailSenderFake();
        notificadorEmail = new NotificadorEmailObserver(mailSender);
        generadorFactura = new GeneradorFacturaObserver();
        fidelizacion = new FidelizaciónObserver(mailSender);

        pedido.agregarObserver(notificadorEmail);
        pedido.agregarObserver(generadorFactura);
        pedido.agregarObserver(fidelizacion);
        
        sistemaDP.agregarSucursal(sucursalUNQ);
        sistemaDP.registrarIndividual("Consola", "Nintendo", "Videojuegos", new ArrayList<Atributo>(), 100, 10, 1);
    }

    @Test
    void notificaPorEmailAlConfirmar() {
        pedido.cambiarEstado(new Confirmado());

        assertEquals(1, mailSender.destinatarios.size());
        assertEquals("ana@mail.com", mailSender.destinatarios.get(0));
    }

    @Test
    void noNotificaPorEmailAlPasarAEnPreparacion() {
        pedido.cambiarEstado(new EnPreparacion());

        assertTrue(mailSender.destinatarios.isEmpty());
    }

    @Test
    void notificaPorEmailAlEnviarYAlEntregar() {
        pedido.cambiarEstado(new Enviado());
        pedido.cambiarEstado(new Entregado());

        assertEquals(2, mailSender.destinatarios.size());
    }

    @Test
    void generaFacturaSoloAlEntregar() {
    	pedido.agregarProducto(catalogoUNQ.buscarProducto(1));
    	ApiBilleteraVirtual api = mock(ApiBilleteraVirtual.class);
    	when(api.validarSaldoSuficiente(250, 100)).thenReturn(true);
    	when(api.bloquearSaldo(250, 100)).thenReturn(true);
    	
    	pedido.confirmarPedido(new BilleteraVirtual(api, 250) , mock(RetiroEnSucursal.class));
        pedido.cambiarEstado(new EnPreparacion());
        
        assertTrue(pedido.getSucursal().getComprobantesFiscales().isEmpty());

        pedido.cambiarEstado(new Entregado());
        
        assertTrue(pedido.getSucursal().tieneComprobanteFiscal(1));
        
        String detalles ="						     ORIGINAL					"                   + "\n" +
        				 "							   [C]		"                                   + "\n" +
        				 "Receptor: ana@mail.com			|		Factura(1)"                     + "\n" +
        				 "Direccion: 9 de Julio 217		|		Fecha emision: 2026-07-16"          + "\n" +
        				 "								|		Direcion: Roque Sáenz Peña 124"     + "\n" +
        				 "								|		CUIT: 28062026"                     + "\n" +
        				 "------------------------------------------------------------------------" + "\n" +
        				 "[Codigo]                [Nombre]                [Precio]"                 + "\n" +
        				 "1        		  	  	 Consola       	  	    100.0"                      + "\n" +
        				 "------------------------------------------------------------------------" + "\n" +
        				 "Importe total: 0.0"                                                       + "\n" +
        				 "Metodo de pago: BILLETERA_VIRTUAL";
        
        
        assertEquals(detalles, pedido.getSucursal().detallesDelComprobanteFiscal(1));
    }

    @Test
    void enviaCuponDeFidelizacionAlCancelarDesdeBorrador() {
        pedido.cancelarPedido();

        assertEquals(pedido.getEstado(), TipoEstado.CANCELADO);
        assertEquals(1, mailSender.destinatarios.size());
        assertEquals("ana@mail.com", mailSender.destinatarios.get(0));
    }

    @Test
    void noEnviaCuponSiNoSeCancela() {
        pedido.cambiarEstado(new Confirmado());
        assertTrue(mailSender.destinatarios.size() > 0);
    }

    @Test
    void unNuevoObserverPuedeSuscribirseSinModificarPedido() {
        final boolean[] fueLlamado = {false};
        pedido.agregarObserver((p, anterior, nuevo) -> fueLlamado[0] = true);

        pedido.cambiarEstado(new Confirmado());

        assertTrue(fueLlamado[0]);
    }

    @Test
    void unObserverQuitadoDejaDeSerNotificado() {
        pedido.quitarObserver(notificadorEmail);

        pedido.cambiarEstado(new Confirmado());

        assertTrue(mailSender.destinatarios.isEmpty());
    }
}