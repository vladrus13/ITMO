ALEX = Lexer.x
HAPPYEXPESSION = GrammarExpression.y 
HAPPYFILE = FileExpression.y
HAPPY = $(HAPPYEXPESSION) $(HAPPYFILE)
SOURCE = Main.hs ManyExpression.hs
GENERATEDALEX = Lexer.hs
GENERATEDHAPPYEXPRESSION = GrammarExpression.hs
GENERATEDHAPPYFILE = FileExpression.hs
GENERATEDHAPPY = $(GENERATEDHAPPYEXPRESSION) $(GENERATEDHAPPYFILE)
GENERATED = $(GENERATEDALEX) $(GENERATEDHAPPY)
OUTPUT = minimal

.PHONY: pack all run clean

all: $(OUTPUT)

run: $(OUTPUT)
	./$(OUTPUT)

$(OUTPUT):
	ghc Main.hs -o $(OUTPUT)

gen: $(ALEX) $(HAPPY) $(SOURCE)
	alex -g $(ALEX) -o $(GENERATEDALEX)
	happy -g -a -c $(HAPPYFILE) -o $(GENERATEDHAPPYFILE)
	happy -g -a -c $(HAPPYEXPESSION) -o $(GENERATEDHAPPYEXPRESSION)

pack: gen
	mkdir temp
	cp Makefile $(SOURCE) $(ALEX) $(HAPPY) $(GENERATED) ./temp/
	cd temp && \
	zip ../solve.zip -r .
	rm -r temp

clean:
	rm *.o
	rm *.hi