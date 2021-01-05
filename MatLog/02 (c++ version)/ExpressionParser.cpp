//
// Created by vladkuznetsov on 20.06.2020.
//

#include "ExpressionParser.h"

bool ExpressionParser::is_any_variable_char() {
    return base::isAnyVar(parsed[iterator]);
}

void ExpressionParser::skip_spaces() {
    while (iterator < (int) parsed.size() && parsed[iterator] == ' ') iterator++;
}

Base *ExpressionParser::parse_predicate() {
    int stopped = 0;
    string variable;
    skip_spaces();
    while (iterator < (int) parsed.size() && get_symbol() >= 'A' && get_symbol() <= 'Z') {
        iterator++;
        stopped++;
    }
    Base *returned = new Variable(parsed.substr((unsigned long) iterator - stopped, (unsigned long) stopped));
    skip_spaces();
    return returned;
}

Base *ExpressionParser::parse_variable() {
    int stopped = 0;
    string variable;
    skip_spaces();
    while (iterator < (int) parsed.size() && is_any_variable_char()) {
        iterator++;
        stopped++;
    }
    if (stopped == 0) return nullptr;
    return new Variable(parsed.substr((unsigned long) iterator - stopped, (unsigned long) stopped));
}

Base *ExpressionParser::parse_unary() {
    skip_spaces();
    Base *returned;
    if (get_symbol() >= 'a' && get_symbol() <= 'z') {
        return parse_variable();
    }
    if (get_symbol() >= 'A' && get_symbol() <= 'Z') {
        return parse_predicate();
    }
    if (get_symbol() == '0') {
        iterator++;
        return new Variable("0");
    }
    if (get_symbol() == base::NOT[0]) {
        iterator++;
        skip_spaces();
        return new UnaryOperator(base::NOT, parse_eq());
    }
    if (get_symbol() == base::EXISTS[0] || get_symbol() == base::FOR_ALL[0]) {
        string op = string(1, get_symbol());
        iterator++;
        skip_spaces();
        Base *variable = parse_variable();
        skip_spaces();
        iterator++;
        skip_spaces();
        return new Quantifier(op, parse_implication(), variable->to_string());
    }
    if (get_symbol() == base::OPEN_BRACKET[0]) {
        iterator++;
        skip_spaces();
        returned = parse_implication();
        skip_spaces();
        iterator++;
        skip_spaces();
        return returned;
    }
    return nullptr;
}

Base *ExpressionParser::parse_gal() {
    skip_spaces();
    Base *returned = parse_unary();
    skip_spaces();
    while (get_symbol() == base::APOSTROPHE[0]) {
        iterator++;
        skip_spaces();
        returned = new UnaryOperator(base::APOSTROPHE, returned);
        skip_spaces();
    }
    return returned;
}

Base *ExpressionParser::parse_multipy() {
    skip_spaces();
    Base *returned = parse_gal();
    skip_spaces();
    while (get_symbol() == base::MULTIPLY[0]) {
        iterator++;
        skip_spaces();
        returned = new BinaryOperator(base::MULTIPLY, returned, parse_gal());
        skip_spaces();
    }
    return returned;
}

Base *ExpressionParser::parse_add() {
    skip_spaces();
    Base *returned = parse_multipy();
    skip_spaces();
    while (get_symbol() == base::PLUS[0]) {
        iterator++;
        skip_spaces();
        returned = new BinaryOperator(base::PLUS, returned, parse_multipy());
        skip_spaces();
    }
    return returned;
}

Base *ExpressionParser::parse_eq() {
    skip_spaces();
    Base *returned = parse_add();
    skip_spaces();
    while (get_symbol() == base::EQUALS[0]) {
        iterator++;
        skip_spaces();
        returned = new BinaryOperator(base::EQUALS, returned, parse_add());
        skip_spaces();
    }
    return returned;
}

Base *ExpressionParser::parse_and() {
    skip_spaces();
    Base *returned = parse_eq();
    skip_spaces();
    while (get_symbol() == base::AND[0]) {
        iterator++;
        skip_spaces();
        returned = new BinaryOperator(base::AND, returned, parse_eq());
        skip_spaces();
    }
    return returned;
}

Base *ExpressionParser::parse_or() {
    skip_spaces();
    Base *returned = parse_and();
    skip_spaces();
    while (get_symbol() == base::OR[0]) {
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
    while (get_symbol() == base::IMPLICATION[0] && parsed[iterator + 1] == base::IMPLICATION[1]) {
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
    Base *returned = parse_implication();
    return returned;
}

void ExpressionParser::add_pair(const string &s, pair<int, Base *> h) {
    proofed[s] = h;
}

void ExpressionParser::inc_counter() {
    counter++;
}

map<string, pair<int, Base *> > &ExpressionParser::getProofed() {
    return proofed;
}

int &ExpressionParser::getCounter() {
    return counter;
}

char ExpressionParser::get_symbol() {
    return parsed[iterator];
}
