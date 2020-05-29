SRCS    = foo.cc
OBJS    = foo.o

CFLAGS = $(CFLAG) -D_REENTRANT -D_THREAD_SAFE -D$(_OSTYPE_)
CPPFLAGS= $(CPPFLAG) -D_REENTRANT -D_THREAD_SAFE -D$(_OSTYPE_)

all : libfoo.so

libfoo.so :
	g++ -fPIC -c $(SRCS)
	g++ -shared -Wl,-soname,$@ -o $@ $(OBJS)
	gradle build

clean:
	rm -f *.o core *.out .*list *.ln *.so
	gradle clean
