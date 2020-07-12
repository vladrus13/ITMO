ALEX = Lexer.x
HAPPY = Grammar.y
SOURCE = Main.hs Types.hs
GENERATEDALEX = Lexer.hs
GENERATEDHAPPY = Grammar.hs
GENERATED = $(GENERATEDALEX) $(GENERATEDHAPPY)
OUTPUT = expressionParser

.PHONY: pack all run clean

all: $(OUTPUT)

run: $(OUTPUT)
	./$(OUTPUT)

$(OUTPUT):
	ghc Main.hs -o $(OUTPUT)

$(GENERATED): $(ALEX) $(HAPPY) $(SOURCE)
	alex -g $(ALEX) -o $(GENERATEDALEX)
	happy -g -a -c $(HAPPY) -o $(GENERATEDHAPPY)

pack: $(GENERATED)
	mkdir temp
	cp Makefile $(SOURCE) $(ALEX) $(HAPPY) $(GENERATED) ./temp/
	cd temp && \
	zip ../solve.zip -r .
	rm -r temp

clean:
	rm *.o
	rm *.hi