package ar.com.grupoz.ui;

import ar.com.grupoz.clients.ClienteSocket;
import ar.com.grupoz.model.RespuestaServidor;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Scanner;

/**
 * Clase encargada de la interacción con el usuario a través de la consola.
 * Presenta un menú con distintas opciones y delega la ejecución de comandos al servidor mediante {@link ClienteSocket}.
 * Es un bean CDI de ámbito application-scoped, por lo que se mantiene activo en todo el ciclo de vida de la aplicación.
 */
@ApplicationScoped
public class Presentacion {
	static Scanner scanner = new Scanner(System.in);
	final String COMANDO_USERNAME = "USERNAME";
	final String COMANDO_EMAIL = "EMAIL";

	@Inject
	ClienteSocket clienteSocket;

	public Presentacion() {}

	/**
	 * Muestra un menú interactivo por consola que permite al usuario:
	 *  Generar un nombre de usuario
	 *  Generar un correo electrónico
	 *  Salir de la aplicación
	 * Se conecta al servidor al inicio y garantiza el cierre de recurso al finalizar (scanner y socket).
	 */
	public void menu() {
		try{
			clienteSocket.conectar();
			String opcionElegida = "0";
			while (!opcionElegida.equals("3")) {
				System.out.println("\nSeleccione una opción: ");
				System.out.println("1 - Generar nombre de usuario");
				System.out.println("2 - Generar correo electrónico");
				System.out.println("3 - Salir");
				opcionElegida = scanner.nextLine();
				switch (opcionElegida) {
					case "1":
						generarNombreDeUsuario();
						break;
					case "2":
						generarCorreoElectronico();
						break;
					case "3":
						System.out.println("¡Chau!");
						break;
					default:
						System.out.println("Opción no válida");
						break;
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			scanner.close();
			clienteSocket.cerrar();
		}
	}

	/**
	 * Solicita al usuario su nombre y apellido, y lo envía al servidor para generar un nombre de usuario.
	 * Muestra el resultado en consola según la respuesta recibida (válida o erronea).
	 */
	private void generarNombreDeUsuario() {
		System.out.print("Ingrese su nombre y apellido: ");
		String nombreApellido = scanner.nextLine();
		RespuestaServidor respuesta = clienteSocket.enviarComando(COMANDO_USERNAME, nombreApellido);
		if (respuesta.getAceptado()) {
			System.out.println("Nombre de usuario generado: " + respuesta.getMensaje());
		} else {
			System.err.println("Error al generar nombre de usuario: " + respuesta.getMensaje());
		}
	}

	/**
	 *Solicita al usuario un nombre de usuario y lo envía al servidor para generar una dirección de correo electrónico.
	 * Muestra el resultado en consola según la respuesta recibida (válida o erronea).
	 */
	private void generarCorreoElectronico() {
		System.out.print("Ingrese nombre de usuario: ");
		String nombreDeUsuario = scanner.nextLine();
		RespuestaServidor respuesta = clienteSocket.enviarComando(COMANDO_EMAIL, nombreDeUsuario);
		if (respuesta.getAceptado()) {
			System.out.println("Correo electrónico generado: " + respuesta.getMensaje());
		} else {
			System.err.println("Error al generar correo electrónico: " + respuesta.getMensaje());
		}
	}
}
