//
// Created by vladkuznetsov on 20.06.2020.
//

#ifndef MATHLOGICHW_BASE_H
#define MATHLOGICHW_BASE_H

#include <string>

using namespace std;

namespace base {
    const string OPEN_BRACKET = "(";
    const string CLOSE_BRACKET = ")";
    const string NOT = "!";
    const string AND = "&";
    const string OR = "|";
    const string IMPLICATION = "->";

    inline bool isLowLetter(char _char) {
        return _char >= 'a' && _char <= 'z';
    }

    inline bool isAny(char _char) {
        return _char != '!' && _char != '.' && _char != '(' && _char != ')' && _char != '&' && _char != '|' && _char != '-' && _char != ' ';
    }
}

class Base {
public:
    virtual bool equals(Base *, map <string, pair <int, Base *> > & proofed, int counter) = 0;

    virtual string to_string() = 0;
};

class Operation {
public:
    string operation;

    explicit Operation(string copied)
        : operation(move(copied))
    {}
};

class Variable : public Base {
public:
    string variable;

    explicit Variable(string copied)
        : variable(move(copied)) {}

    bool equals(Base *, map <string, pair <int, Base *> > & proofed, int counter) override;

    string to_string() override;
};

class BinaryOperator : public Base, public Operation {
public:
    Base *left, *right;

    bool equals(Base *, map <string, pair <int, Base *> > & proofed, int counter) override;

    string to_string() override;

    BinaryOperator(string _operation, Base *_left, Base * _right);
};

class UnaryOperator : public Base, public Operation {
public:
    Base * expression;

    bool equals(Base *, map <string, pair <int, Base *> > & proofed, int counter) override;

    string to_string() override;

    UnaryOperator(string _operation, Base * _expression);
};

#endif //MATHLOGICHW_BASE_H
