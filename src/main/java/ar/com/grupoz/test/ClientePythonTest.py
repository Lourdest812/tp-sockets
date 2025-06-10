import socket

HOST = '127.0.0.1'
PORT = 1234

def crear_usuario(s):
    usuario = input('Ingrese el nombre de usuario: ')
    s.sendall(f'USERNAME:{usuario}\n'.encode())
    print('Respuesta:', s.recv(1024).decode())

def crear_mail(s):
    usuario = input('Ingrese el nombre de usuario: ')
    s.sendall(f'EMAIL:{usuario}\n'.encode())
    print('Respuesta:', s.recv(1024).decode())

def menu(s):
    while True:
        print('\nIngrese que quiere hacer')
        print('1- Crear Usuario')
        print('2- Crear Mail')
        print('3- Salir')
        try:
            numero = int(input('Opción: '))
        except ValueError:
            print("Debe ingresar un número.")
            continue
        match numero:
            case 1:
                crear_usuario(s)
            case 2:
                crear_mail(s)
            case 3:
                print("Saliendo...")
                break
            case _:
                print("Opción no válida.")

def main():
    while True:
        try:
            with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
                print('\nConectando con el puerto', PORT)
                s.connect((HOST, PORT))
                menu(s)
                break  # Sale si el usuario elige salir del menú
        except (ConnectionRefusedError, OSError) as e:
            print(f"No se pudo conectar al puerto {PORT}: {e}")
            retry = input("¿Desea intentar conectarse de nuevo? (s/n): ").strip().lower()
            if retry != 's':
                print("Saliendo del programa.")
                break

if __name__ == "__main__":
    main()