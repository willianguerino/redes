#!/usr/bin/env python3
#programacao de redes com Python
#Cliente e servidor TCP simples que enviam e recebem 16 octetos

import argparse, socket

def recvall(sock, length):
    data = b''
    while len(data)<length:
        more= sock.recv(length -len(data))
        if not more:
            raise EOFError(f"Was expecting {length} bytes but onlu received {len(data)} bytes before the socket closed")
        data += more

    return data

def server(interface, port):
    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    sock.bind((interface, port))
    sock.listen(1)
    print("listening at", sock.getsockname())
    while True:
        sc,sockname = sock.accept()
        print("We accepted a connection from ", sockname)
        print("  Socket name:", sc.getsockname())
        print("  Soket peer :", sc.getpeername())
        message = recvall(sc, 16)
        print(" Incoming sixteen-octet message:", repr(message))
        sc.sendall(b'farewell, client')
        sc.close()
        print("   Reply sent, socket closed")

def client(host, port):
    sock = socket.socket(socket.AF_INET,socket.SOCK_STREAM)
    sock.connect((host,port))
    print("Client has been assigned socket name", sock.getsockname())
    sock.sendall(b'Hi there, server')
    reply = recvall(sock, 16)
    print("The server said ",repr(reply))
    sock.close()

if __name__=="__main__":
    choices ={"client": client, "server":server}
    parser = argparse.ArgumentParser(description="Send and receive over TCP")
    parser.add_argument("role",choices=choices, help="which role to play")
    parser.add_argument("host", help="interface the server listens at; host the client sends to")
    parser.add_argument("-p", metavar="port", type=int, default=1060, help="TCP port (default 1060")
    args = parser.parse_args()
    function =  choices[args.role]
    function(args.host, args.p)
   