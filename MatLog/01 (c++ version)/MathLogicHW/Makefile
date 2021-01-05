CC          = g++
CFLAGS      = -c -Wall
LDFLAGS     =
SOURCES     = Base.cpp ExpressionParser.cpp main.cpp SolveE.cpp SolveStatus.cpp
HEADFILES   = Base.h ExpressionParser.h SolveE.h
OBJFILES    = $(SOURCES:.cpp=.o)
EXECUTABLE  = ExpressionParser

run: $(SOURCES) $(EXECUTABLE) $(HEADFILES)
	./$(EXECUTABLE)

all: $(SOURCES) $(EXECUTABLE) $(HEADFILES)

$(EXECUTABLE): $(OBJFILES) $(HEADFILES)
	$(CC) $(LDFLAGS) $(OBJFILES) -o $@

.cpp.o:
	$(CC) $(CFLAGS) $< -o $@

clear:
	rm *.o

pack: all
	mkdir temp
	cp Makefile $(SOURCES) $(EXECUTABLE) $(HEADFILES) ./temp/
	cd temp && \
	zip ../solve.zip -r .
	rm -r temp

