#
# Makefile
# Date : 2014.09.05
# 

CC              = cc
CFLAGS          = -g -c -Wall
.SUFFIXES       : .c .o .cp

.c.o            :
	$(CC) $(CFLAGS) $*.c

TARGET = execserv execclnt

all :: $(TARGET)

execserv : execserv.o
	$(CC) -o $@ $@.o -lnsl 

execclnt : execclnt.o
	$(CC) -o $@ $@.o -lnsl
	rm -f $@.o

clean	:
	rm -f *.o *.out err core
