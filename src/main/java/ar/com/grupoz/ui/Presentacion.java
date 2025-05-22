package ar.com.grupoz.ui;

import ar.com.grupoz.clients.ClienteSocket;
import ar.com.grupoz.model.RespuestaServidor;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Scanner;

@ApplicationScoped
public class Presentacion {
	static Scanner scanner = new Scanner(System.in);
	final String COMANDO_USERNAME = "USERNAME";
	final String COMANDO_EMAIL = "EMAIL";

	@Inject
	ClienteSocket clienteSocket;

	public Presentacion() {}

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
