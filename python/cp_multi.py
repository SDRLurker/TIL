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
def gen_multicast_sock(MCAST_GRP, MCAST_PORT):
    sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM, socket.IPPROTO_UDP)
    sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    sock.bind((MCAST_GRP, MCAST_PORT))  # use MCAST_GRP instead of '' to listen only
                                     # to MCAST_GRP, not all groups on MCAST_PORT
    mreq = struct.pack("4sl", socket.inet_aton(MCAST_GRP), socket.INADDR_ANY)

    sock.setsockopt(socket.IPPROTO_IP, socket.IP_ADD_MEMBERSHIP, mreq)
    fcntl.fcntl(sock, fcntl.F_SETFL, os.O_NONBLOCK)
    return sock

def multicast_socket():
    sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM, socket.IPPROTO_UDP)
    sock.setsockopt(socket.IPPROTO_IP, socket.IP_MULTICAST_TTL, 0)
    return sock

def recv_one(rsock,ssock,stuple):
    try:
        s = rsock.recv(10240)
        r = ssock.sendto(s,stuple)
        #print(len(s), s)
    except socket.error as e:
        err = e.args[0]
        if err == errno.EAGAIN or err == errno.EWOULDBLOCK:
            sleep(0.001)
            return
        else:
            print(e)
            sys.exit(1)

if __name__ == "__main__":
    import sys
    if len(sys.argv) != 5:
        print("Usage) %s 멀티캐스트수신IP 수신PORT 송신IP 송신PORT" % sys.argv[0])
        sys.exit(-1)
    rip = sys.argv[1]
    rport = int(sys.argv[2])
    sip = sys.argv[3]
    sport = int(sys.argv[4])

    rms = gen_multicast_sock(rip,rport)
    sms = multicast_socket()

    while True:
        recv_one(rms,sms,(sip,sport))
