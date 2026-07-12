package test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pedido.*;
import notificadores.*;

import productos.Producto;
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

    private Pedido pedido;
    private MailSenderFake mailSender;
    private NotificadorEmailObserver notificadorEmail;
    private GeneradorFacturaObserver generadorFactura;
    private FidelizaciónObserver fidelizacion;

    @BeforeEach
    void setUp() {
        Sucursal sucursal = mock(Sucursal.class);
        pedido = new Pedido(sucursal, new ArrayList<Producto>(), "ana@mail.com", new Direccion("9 de Julio 217", -34.712445d, -58.284493d));
        
        mailSender = new MailSenderFake();
        notificadorEmail = new NotificadorEmailObserver(mailSender);
        generadorFactura = new GeneradorFacturaObserver();
        fidelizacion = new FidelizaciónObserver(mailSender);

        pedido.agregarObserver(notificadorEmail);
        pedido.agregarObserver(generadorFactura);
        pedido.agregarObserver(fidelizacion);
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
        pedido.cambiarEstado(new Confirmado());
        pedido.cambiarEstado(new EnPreparacion());
        assertTrue(generadorFactura.getFacturasGeneradas().isEmpty());

        pedido.cambiarEstado(new Enviado());
        pedido.cambiarEstado(new Entregado());

        assertEquals(1, generadorFactura.getFacturasGeneradas().size());
        assertEquals("ana@mail.com", generadorFactura.getFacturasGeneradas().get(0).getClienteMail());
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