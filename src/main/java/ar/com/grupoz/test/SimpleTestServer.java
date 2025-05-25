package ar.com.grupoz.test;

import java.io.*;
import java.net.*;

public class SimpleTestServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);
        System.out.println("Servidor de prueba escuchando en el puerto 1234...");
        try {
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader entrada = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter salida = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    String linea;
                    while ((linea = entrada.readLine()) != null) {
                        System.out.println("Recibido: " + linea);
                        String[] partes = linea.split(":", 2);
                        if (partes.length != 2) {
                            salida.println("false:Comando inválido");
                            continue;
                        }
                        String comando = partes[0];
                        String valor = partes[1];
                        if (comando.equals("USERNAME")) {
                            String username = generarUsername(valor);
                            if (username == null) {
                                salida.println("false:No se pudo generar un nombre de usuario válido");
                            } else {
                                salida.println("true:" + username);
                            }
                        } else if (comando.equals("EMAIL")) {
                            String email = generarEmail(valor);
                            if (email == null) {
                                salida.println("false:No se pudo generar un correo válido");
                            } else {
                                salida.println("true:" + email);
                            }
                        } else {
                            salida.println("false:Comando desconocido");
                        }
                    }
                } catch (IOException e) {
                    System.err.println("Error en la conexión: " + e.getMessage());
                }
            }
        } finally {
            serverSocket.close();
        }
    }

    // Métodos auxiliares para generación y validación
    private static String generarUsername(String nombreApellido) {
        String limpio = nombreApellido.replaceAll("[^a-zA-Z]", "").toLowerCase();
        if (limpio.length() < 5 || limpio.length() > 20) return null;
        if (!limpio.matches(".*[aeiou].*") || !limpio.matches(".*[bcdfghjklmnpqrstvwxyz].*")) return null;
        return limpio;
    }

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

    private static boolean validarEmail(String email) {
        return email.matches("^[a-zA-Z]{5,20}@(gmail|hotmail)\\.com$");
    }
}
