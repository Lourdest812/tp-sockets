package ar.com.grupoz.clients;
import ar.com.grupoz.model.RespuestaServidor;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Cliente para comunicarse con un servidor vía sockets TCP.
 * Esta clase se encarga de:
 *  - abrir una conexión
 *  - enviar comandos formateados
 *  - recibir respuestas
 *  - cerrar los recursos al finalizar.
 * Forma parte del contexto de la aplicación como un bean CDI application-scoped.
 */
@ApplicationScoped
public class ClienteSocket {

	private final String SERVER_IP = "127.0.0.1";
	private final int SERVER_PORT = 1234;
	private Socket socket;
	private PrintWriter salida;
	private BufferedReader entrada;

	/**
	 * Establece la conexión con el servidor utilizando la IP y el puerto definidos.
	 * Inicializa las variables entrada y salida que permiten el flujo de la comunicación con el servidor.
	 *
	 * @throws IOException si ocurre un error al abrir el socket o los flujos como por ejemplo
	 * rechazo en la conexión por parte del servidor.
	 */
	public void conectar() throws IOException {
		socket = new Socket(SERVER_IP, SERVER_PORT);
		salida = new PrintWriter(socket.getOutputStream(), true);//autoFlush=true (se manda al instante)
		//PrintWriter toma texto plano y lo convierte a bytes
		entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		//InputStreamReader toma bytes y lo convierte a texto plano
	}

	/**
	 * Envía un comando al servidor y espera una respuesta en el formato: "true:respuesta" o "false:mensaje de error".
	 * Si la respuesta recibida no tiene el formato esperado, se devuelve un mensaje de error.
	 * Por ejemplo: {@code true:juanperez@hotmail.com}
	 *
	 * @param comando Comando a enviar ("USERNAME" o "EMAIL").
	 * @param valor   Valor asociado al comando.
	 * @return Un objeto {@link RespuestaServidor} con la respuesta del servidor.
	 */
	public RespuestaServidor enviarComando(String comando, String valor) {
		RespuestaServidor respuestaServidor = new RespuestaServidor();
		try {
			// Envía el comando al servidor en el formato esperado
			salida.println(comando + ":" + valor);
			// Lee la respuesta del servidor
			String respuesta = entrada.readLine();
			// Toma la respuesta y la divide en dos partes: aceptado (true/false) y el mensaje
			String[] partes = respuesta.split(":");
			// Verifica que la respuesta tenga el formato correcto
			if (partes.length == 2) {
				// Asigna los valores a la respuesta del servidor
				respuestaServidor.setAceptado(Boolean.parseBoolean(partes[0]));
				respuestaServidor.setMensaje(partes[1]);
			} else {
				/// Si la respuesta no tiene el formato esperado, se asigna un mensaje de error
				respuestaServidor.setMensaje("Respuesta inválida del servidor");
			}
		} catch (Exception e) {
			respuestaServidor.setMensaje("Error al enviar comando: " + e.getMessage());
		}
		return respuestaServidor;
	}

	/**
	 * Cierra todos los recursos abiertos: socket, entrada y salida.
	 */
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