#!/usr/bin/python
# -*- coding: cp949 -*-
import socket
import struct
import fcntl
import os
import sys
import errno
from time import sleep


# https://stackoverflow.com/questions/603852/how-do-you-udp-multicast-in-python
def gen_multicast_sock(MCAST_GRP, MCAST_PORT, nicip='0.0.0.0'):
    sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM, socket.IPPROTO_UDP)
    sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    # use MCAST_GRP instead of '' to listen only
    # to MCAST_GRP, not all groups on MCAST_PORT
    sock.bind((MCAST_GRP, MCAST_PORT))
    MCAST_GRPIP = socket.inet_aton(MCAST_GRP)
    if nicip == '0.0.0.0':
        mreq = struct.pack("4sl", MCAST_GRPIP, socket.INADDR_ANY)
    else:
        mreq = struct.pack("4s4s", MCAST_GRPIP, socket.inet_aton(nicip))
    sock.setsockopt(socket.IPPROTO_IP, socket.IP_ADD_MEMBERSHIP, mreq)
    fcntl.fcntl(sock, fcntl.F_SETFL, os.O_NONBLOCK)
    return sock


def recv_one(sock):
    try:
        s = sock.recv(10240)
        print(len(s), s)
    except socket.error as e:
        err = e.args[0]
        if err == errno.EAGAIN or err == errno.EWOULDBLOCK:
            sleep(0.001)
            return
        else:
            print(e)
            sys.exit(1)


if __name__ == "__main__":
    if len(sys.argv) != 3 and len(sys.argv) != 4:
        print("Usage) %s IP PORT (NICIP)" % sys.argv[0])
        sys.exit(-1)
    ip = sys.argv[1]
    port = int(sys.argv[2])
    nicip = sys.argv[3] if len(sys.argv) >= 4 else '0.0.0.0'

    sock1 = gen_multicast_sock(ip, port, nicip)

    while True:
        recv_one(sock1)
