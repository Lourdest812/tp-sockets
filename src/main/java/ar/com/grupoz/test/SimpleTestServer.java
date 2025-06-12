package ar.com.grupoz.test;

import java.io.*;
import java.net.*;

/**
 * Servidor simple en Java que escucha en el puerto 1234 usando TCP.
 * Recibe comandos de clientes (en formato TEXTO) para generar nombres de usuario y correos electrónicos válidos.
 * Puede ser probado con clientes en cualquier lenguaje (Java, Python, C, etc.).
 */
public class SimpleTestServer {
    public static void main(String[] args) throws IOException {
        // Crea el servidor y lo deja escuchando en el puerto 1234
        ServerSocket serverSocket = new ServerSocket(1234);
        System.out.println("Servidor de prueba escuchando en el puerto " + serverSocket.getLocalPort() + "...");

        try {
            // Bucle principal: acepta conexiones de clientes una por una
            while (true) {
                // Espera a que un cliente se conecte
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader entrada = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter salida = new PrintWriter(clientSocket.getOutputStream(), true)) {

					String linea;
					// Lee los mensajes que envía el cliente
					while ((linea = entrada.readLine()) != null) {
						try {
							System.out.println("Recibido: " + linea);

							// Espera mensajes en formato COMANDO:valor (por ejemplo, USERNAME:LourdesToledo)
							String[] partes = linea.split(":", 2);
							if (partes.length != 2) {
								salida.println("false:Comando inválido");
								continue;
							}
							String comando = partes[0];
							String valor = partes[1];

							// Si el comando es USERNAME, intenta generar un nombre de usuario válido
							if (comando.equals("USERNAME")) {
								String username = generarUsername(valor);
								salida.println("true:" + username);
								// Si el comando es EMAIL, intenta generar un correo válido
							} else if (comando.equals("EMAIL")) {
								String email = generarEmail(valor);
								salida.println("true:" + email);
								// Si el comando no es reconocido, responde con error
							} else {
								salida.println("false:Comando desconocido");
							}
						} catch (IllegalArgumentException e) {
							// Maneja errores de validación de entrada
							System.err.println("Error de validación: " + e.getMessage());
							salida.println("false:" + e.getMessage());
						}
					}
				} catch (SocketException e) {
					// Maneja desconexiones inesperadas
					System.err.println("Cliente desconectado: " + e.getMessage());
                } catch (IOException e) {
                    System.err.println("Error en la conexión: " + e.getMessage());
                }
            }
        } finally {
            // Cierra el servidor si el programa termina
            serverSocket.close();
        }
    }

    // Genera un nombre de usuario válido a partir de un nombre y apellido
    // Debe tener entre 5 y 20 letras, al menos una vocal y una consonante, y solo letras
    private static String generarUsername(String nombreApellido) {
		validarUsername(nombreApellido);
		return nombreApellido.replaceAll("[^a-zA-Z]", "").toLowerCase();
	}

    // Genera un correo electrónico válido usando el username y un dominio permitido
    private static String generarEmail(String username) {
        String[] dominios = {"@gmail.com", "@hotmail.com"};
		String user = username.toLowerCase();
		validarUsername(user);
        // Selecciona un dominio válido aleatorio
        String dominio = dominios[(int)(Math.random() * dominios.length)];
        String email = user + dominio;
        if (!validarEmail(email))
			throw new IllegalArgumentException("El formato para email no es válido.");
        return email;
    }

	// Realiza las validaciones de nombre de usuario
	private static void validarUsername(String username){
		String usernameLowerCase = username.toLowerCase();
		if (!usernameLowerCase.matches("(?=.*[aeiou])(?=.*[bcdfghjklmnpqrstvwxyz]).+"))
			throw new IllegalArgumentException("El nombre de usuario debe contener al menos una vocal y una consonante");
		if (usernameLowerCase.matches(".*\\d.*"))
			throw new IllegalArgumentException("El nombre de usuario no puede contener números");
		String limpio = usernameLowerCase.replaceAll("[^a-zA-Z]", "").toLowerCase();
		if (limpio.length() < 5 || limpio.length() > 20)
			throw new IllegalArgumentException("El nombre de usuario debe tener entre 5 y 20 letras.");
	}

    // Valida que el correo tenga el formato correcto y un dominio permitido
    private static boolean validarEmail(String email) {
        return email.matches("^[a-zA-Z]{5,20}@(gmail|hotmail)\\.com$");
    }
}