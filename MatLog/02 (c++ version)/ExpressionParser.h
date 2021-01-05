//
// Created by vladkuznetsov on 20.06.2020.
//

#ifndef MATHLOGICHW_EXPRESSIONPARSER_H
#define MATHLOGICHW_EXPRESSIONPARSER_H

#include <string>
#include <map>
#include "Base.h"

using namespace std;

class ExpressionParser {
    string parsed;
    int iterator;
    map <string, pair <int, Base *> > proofed;
    int counter = 0;

    bool is_any_variable_char();

    void skip_spaces();

    char get_symbol();

    Base * parse_predicate();

    Base * parse_variable();

    Base * parse_unary();

    Base * parse_gal();

    Base * parse_multipy();

    Base * parse_add();

    Base * parse_eq();

    Base * parse_and();

    Base * parse_or();

    Base * parse_implication();

public:

    Base * parse(string s);

    void add_pair(const string& s, pair <int, Base *> h);

    void inc_counter();

    map <string, pair <int, Base *> > & getProofed();

    [[nodiscard]] int& getCounter();
};

#endif //MATHLOGICHW_EXPRESSIONPARSER_H
