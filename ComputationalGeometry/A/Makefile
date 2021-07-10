CC          = g++
CFLAGS      =
LDFLAGS     = -I/usr/include -L/usr/lib/x86_64-linux-gnu -lgmp
SOURCES     = main.cpp
OBJFILES    = $(SOURCES:.cpp=.o)
EXECUTABLE  = a.out

all:
	g++ $(SOURCES) -I/usr/include -L/usr/lib/x86_64-linux-gnu -lgmp
run:
	./a.out

pack: all
	mkdir temp
	cp Makefile $(SOURCES) $(EXECUTABLE) ./temp/
	cd temp && \
	zip ../solve.zip -r .
	rm -r temp
