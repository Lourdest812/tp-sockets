import socket

HOST = '127.0.0.1'
PORT = 1234

with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
    s.connect((HOST, PORT))
    s.sendall(b'USERNAME:LourdesToledo\n')
    print('USERNAME:', s.recv(1024).decode())
    s.sendall(b'EMAIL:lourdestoledo\n')
    print('EMAIL:', s.recv(1024).decode())