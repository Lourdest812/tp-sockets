package ar.com.grupoz.clients;
import ar.com.grupoz.model.RespuestaServidor;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

@ApplicationScoped
public class ClienteSocket {

	private final String SERVER_IP = "127.0.0.1";
	private final int SERVER_PORT = 1234;
	private Socket socket;
	private PrintWriter salida;
	private BufferedReader entrada;

	public void conectar() throws IOException {
		socket = new Socket(SERVER_IP, SERVER_PORT);
		salida = new PrintWriter(socket.getOutputStream(), true);
		entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

	public RespuestaServidor enviarComando(String comando, String valor) {
		RespuestaServidor respuestaServidor = new RespuestaServidor();
		try {
			salida.println(comando + ":" + valor);
			String respuesta = entrada.readLine();
			String[] partes = respuesta.split(":");
			if (partes.length == 2) {
				respuestaServidor.setAceptado(Boolean.parseBoolean(partes[0]));
				respuestaServidor.setMensaje(partes[1]);
			} else {
				respuestaServidor.setMensaje("Respuesta inválida del servidor");
			}
		} catch (Exception e) {
			respuestaServidor.setMensaje("Error al enviar comando: " + e.getMessage());
		}
		return respuestaServidor;
	}

	public void cerrar() {
		try {
			if (entrada != null) entrada.close();
			if (salida != null) salida.close();
			if (socket != null && !socket.isClosed()) socket.close();
		} catch (IOException e) {
			System.err.println("Error al desconectar socket: " + e.getMessage());
		}
	}
}