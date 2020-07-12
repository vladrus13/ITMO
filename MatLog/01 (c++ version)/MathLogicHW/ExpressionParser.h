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

    bool is_any();

    void skip_spaces();

    Base * parse_token();

    Base * parse_and();

    Base * parse_or();

    Base * parse_implication();

public:

    Base * parse(string s);

    void add_pair(string s, pair <int, Base *> h);

    void inc_counter();

    map <string, pair <int, Base *> > & getProofed();

    [[nodiscard]] int& getCounter();
};

#endif //MATHLOGICHW_EXPRESSIONPARSER_H
