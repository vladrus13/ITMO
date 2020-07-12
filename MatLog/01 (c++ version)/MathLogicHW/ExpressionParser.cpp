//
// Created by vladkuznetsov on 20.06.2020.
//

#include "ExpressionParser.h"

bool ExpressionParser::is_any() {
    return base::isAny(parsed[iterator]);
}

void ExpressionParser::skip_spaces() {
    while (iterator < parsed.size() && parsed[iterator] == ' ') iterator++;
}

Base *ExpressionParser::parse_token() {
    Base *returned;
    skip_spaces();
    if (parsed[iterator] == base::OPEN_BRACKET[0]) {
        iterator++;
        skip_spaces();
        returned = parse_implication();
        iterator++;
    } else {
        if (parsed[iterator] == base::NOT[0]) {
            iterator++;
            skip_spaces();
            returned = new UnaryOperator(base::NOT, parse_token());
        } else {
            int stopped = 0;
            string variable;
            skip_spaces();
            while (iterator < parsed.size() && is_any()) {
                iterator++;
                stopped++;
            }
            returned = new Variable(parsed.substr(iterator - stopped, stopped));
        }
    }
    skip_spaces();
    return returned;
}

Base *ExpressionParser::parse_and() {
    skip_spaces();
    Base *returned = parse_token();
    skip_spaces();
    while (parsed[iterator] == base::AND[0]) {
        iterator++;
        skip_spaces();
        returned = new BinaryOperator(base::AND, returned, parse_token());
        skip_spaces();
    }
    return returned;
}

Base *ExpressionParser::parse_or() {
    skip_spaces();
    Base *returned = parse_and();
    skip_spaces();
    while (parsed[iterator] == base::OR[0]) {
        iterator++;
        skip_spaces();
        returned = new BinaryOperator(base::OR, returned, parse_and());
        skip_spaces();
    }
    return returned;
}

Base *ExpressionParser::parse_implication() {
    skip_spaces();
    Base *returned = parse_or();
    skip_spaces();
    while (parsed[iterator] == base::IMPLICATION[0] && parsed[iterator + 1] == base::IMPLICATION[1]) {
        iterator += 2;
        skip_spaces();
        returned = new BinaryOperator(base::IMPLICATION, returned, parse_implication());
        skip_spaces();
    }
    return returned;
}

Base *ExpressionParser::parse(string s) {
    iterator = 0;
    parsed = move(s);
    Base * returned = parse_implication();
    return returned;
}

void ExpressionParser::add_pair(string s, pair<int, Base *> h) {
    proofed[s] = h;
}

void ExpressionParser::inc_counter() {
    counter++;
}

map <string, pair <int, Base *> > & ExpressionParser::getProofed() {
    return proofed;
}

int& ExpressionParser::getCounter() {
    return counter;
}
