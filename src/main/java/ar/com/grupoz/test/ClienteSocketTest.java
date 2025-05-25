package ar.com.grupoz.test;

import ar.com.grupoz.clients.ClienteSocket;
import ar.com.grupoz.model.RespuestaServidor;

public class ClienteSocketTest {
    public static void main(String[] args) throws Exception {
        ClienteSocket cliente = new ClienteSocket();
        cliente.conectar();
        RespuestaServidor respuesta = cliente.enviarComando("USERNAME", "LourdesToledo");
        System.out.println("Aceptado: " + respuesta.getAceptado());
        System.out.println("Mensaje: " + respuesta.getMensaje());
        respuesta = cliente.enviarComando("EMAIL", "lourdestoledo");
        System.out.println("Aceptado: " + respuesta.getAceptado());
        System.out.println("Mensaje: " + respuesta.getMensaje());
        cliente.cerrar();
    }
}
