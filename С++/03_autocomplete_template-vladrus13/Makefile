all: test

autocomplete_test: term.hpp term.cpp binary_search_deluxe.hpp binary_search_deluxe.cpp autocomplete.hpp autocomplete.cpp autocomplete_test.cpp
	$(CXX) -g -Wall -Wextra -Werror -std=c++17 $^ -o autocomplete_test

test: autocomplete_test
	./autocomplete_test

clean:
	rm autocomplete_test

.PHONY: all test autocomplete_test clean
