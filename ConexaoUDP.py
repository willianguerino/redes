#!/usr/bin/env puthon3
#cliente e servidor UDP em localhost

import argparse,socket
from datetime import datetime

MAX_BYTES = 65535

def server(port):
    sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    sock.bind(("127.0.0.1",port))
    print(f"Listening at {sock.getsockname()}")

    while True:
        data, address = sock.recvfrom(MAX_BYTES)
        text = data.decode("ascii")
        print(f"The client at {address} says {text}")
        text = f"Your data was {len(data)} bytes"
        data = text.encode("ascii")
        sock.sendto(data,address)

def client (port):
    sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    text = f"The time is {datetime.now()}"
    data = text.encode("ascii")
    sock.sendto(data,("127.0.0.1", port))
    print(f"The OS assingned me the address {sock.getsockname()}")
    data, address = sock.recvfrom(MAX_BYTES)
    text = data.decode("ascii")
    print(f"The server {address} replied {text} ")


if __name__== "__main__":
    choices ={'client':client, 'server': server}
    parser=argparse.ArgumentParser(description ="Send and receive UDP locally")
    parser.add_argument("role", choices = choices, help="which role to play")
    parser.add_argument("-p", metavar="PORT", type=int, default=1060, help="UDP port (default 1060)")
    args = parser.parse_args()
    function = choices[args.role]
    function(args.p)
