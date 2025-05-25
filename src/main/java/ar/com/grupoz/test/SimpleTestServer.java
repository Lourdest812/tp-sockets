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
                            if (username == null) {
                                salida.println("false:No se pudo generar un nombre de usuario válido");
                            } else {
                                salida.println("true:" + username);
                            }
                        // Si el comando es EMAIL, intenta generar un correo válido
                        } else if (comando.equals("EMAIL")) {
                            String email = generarEmail(valor);
                            if (email == null) {
                                salida.println("false:No se pudo generar un correo válido");
                            } else {
                                salida.println("true:" + email);
                            }
                        // Si el comando no es reconocido, responde con error
                        } else {
                            salida.println("false:Comando desconocido");
                        }
                    }
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
        String limpio = nombreApellido.replaceAll("[^a-zA-Z]", "").toLowerCase();
        if (limpio.length() < 5 || limpio.length() > 20) return null;
        if (!limpio.matches(".*[aeiou].*") || !limpio.matches(".*[bcdfghjklmnpqrstvwxyz].*")) return null;
        return limpio;
    }

    // Genera un correo electrónico válido usando el username y un dominio permitido
    private static String generarEmail(String username) {
        String[] dominios = {"@gmail.com", "@hotmail.com"};
        String user = username.replaceAll("[^a-zA-Z]", "").toLowerCase();
        if (user.length() < 5 || user.length() > 20) return null;
        if (!user.matches(".*[aeiou].*") || !user.matches(".*[bcdfghjklmnpqrstvwxyz].*")) return null;
        // Selecciona un dominio válido aleatorio
        String dominio = dominios[(int)(Math.random() * dominios.length)];
        String email = user + dominio;
        if (validarEmail(email)) return email;
        return null;
    }

    // Valida que el correo tenga el formato correcto y un dominio permitido
    private static boolean validarEmail(String email) {
        return email.matches("^[a-zA-Z]{5,20}@(gmail|hotmail)\\.com$");
    }
}