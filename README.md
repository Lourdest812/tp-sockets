# Trabajo Práctico Sockets

Este proyecto contiene un servidor socket implementado en Java que procesa solicitudes para:

- Generar un nombre de usuario a partir de un nombre completo.
- Generar un correo electrónico a partir de un nombre de usuario.

El servidor realiza validaciones básicas para asegurar la integridad y validez de los datos recibidos, y devuelve respuestas indicando si la operación fue exitosa o no.

Además, incluye dos clientes con interfaz gráfica para interactuar con el servidor:
- Cliente desarrollado en Java.
- Cliente desarrollado en Python.

## Prerequisitos

- Java Development Kit (JDK 21) instalado.
- Python 3.x instalado (para el cliente en Python).
- Conexión a internet (opcional, para descargar dependencias).
- Maven (para gestionar dependencias en el cliente Java).

## ¿Cómo usar este programa?

1. Primero, abrí una terminal y ejecutá el servidor (SimpleTestServer). Dejalo corriendo.
2. Después, en otra terminal, ejecutá el cliente (App.java) para ver el menú y probar la generación de usuario y correo.

---

**Notas:**
- Acordate de tener Java instalado y configurado.
- El servidor tiene que estar corriendo antes de abrir el cliente.
- El menú te permite probar la generación y validación de nombres de usuario y correos según la consigna.
