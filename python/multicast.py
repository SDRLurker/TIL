#!/usr/bin/python
# -*- coding: cp949 -*-
import socket
import struct
import fcntl
import os
import sys
import errno
from time import sleep

def gen_multicast_sock(MCAST_GRP, MCAST_PORT):
    sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM, socket.IPPROTO_UDP)
    sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    sock.bind(('', MCAST_PORT))  # use MCAST_GRP instead of '' to listen only
                                     # to MCAST_GRP, not all groups on MCAST_PORT
    mreq = struct.pack("4sl", socket.inet_aton(MCAST_GRP), socket.INADDR_ANY)

    sock.setsockopt(socket.IPPROTO_IP, socket.IP_ADD_MEMBERSHIP, mreq)
    fcntl.fcntl(sock, fcntl.F_SETFL, os.O_NONBLOCK)
    return sock

def recv_one(sock):
    try:
        s = sock.recv(10240)
        print(len(s), s)
    except socket.error as e:
        err = e.args[0]
        if err == errno.EAGAIN or err == errno.EWORLDBLOCK:
            sleep(0.001)
            return
        else:
            print(e)
            sys.exit(1)

if __name__ == "__main__":
    import sys
    if len(sys.argv) != 3:
        print("Usage) %s IP PORT" % sys.argv[0])
        sys.exit(-1)
    ip = sys.argv[1]
    port = int(sys.argv[2])
    sock1 = gen_multicast_sock(ip,port)

    while True:
        recv_one(sock1)
