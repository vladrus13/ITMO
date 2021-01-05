#pragma clang diagnostic push
#pragma ide diagnostic ignored "cert-err58-cpp"
//
// Created by vladkuznetsov on 20.06.2020.
//

#ifndef MATHLOGICHW_BASE_H
#define MATHLOGICHW_BASE_H

#include <string>
#include <unordered_set>

using namespace std;

namespace base {
    const string OPEN_BRACKET = "(";
    const string CLOSE_BRACKET = ")";
    const string NOT = "!";
    const string AND = "&";
    const string OR = "|";
    const string IMPLICATION = "->";
    const string EXISTS = "@";
    const string FOR_ALL = "?";
    const string POINT = ".";
    const string APOSTROPHE = "\'";
    const string PLUS = "+";
    const string MULTIPLY = "*";
    const string EQUALS = "=";

    inline bool isAnyVar(char _char) {
        return _char >= 'a' && _char <= 'z';
    }

}

class Base {
public:
    bool is_axiom = false;

    unsigned long long hash = 0;
    bool hasher = false;

    virtual string to_string() = 0;

    virtual void set_axiom(bool is_axiom) = 0;

    virtual void get_variables(unordered_set<string> & variables) = 0;

    virtual bool is_not_free(unordered_set<string> & variables) = 0;

    virtual bool equals_one_replace(Base *, Base * from, Base *& to) = 0;

    void set_hash(unsigned long long _hash) {
        hash = _hash;
        hasher = true;
    }
};

class Operation {
public:
    string operation;

    explicit Operation(string copied)
            : operation(move(copied)) {}

};

class Variable : public Base {
public:
    string variable;

    explicit Variable(string copied)
            : variable(move(copied)) {}

    string to_string() override;

    void set_axiom(bool is_axiom) override;

    void get_variables(unordered_set<string> & variables) override;

    bool is_not_free(unordered_set<string> & variables) override;

    bool equals_one_replace(Base *, Base * from, Base *& to) override;
};

class BinaryOperator : public Base, public Operation {
public:
    Base *left, *right;

    string to_string() override;

    BinaryOperator(string _operation, Base *_left, Base *_right);

    void set_axiom(bool is_axiom) override;

    void get_variables(unordered_set<string> & variables) override;

    bool is_not_free(unordered_set<string> & variables) override;

    bool equals_one_replace(Base *, Base * from, Base *& to) override;

};

class UnaryOperator : public Base, public Operation {
public:
    Base *expression;

    string to_string() override;

    UnaryOperator(string _operation, Base *_expression);

    void set_axiom(bool is_axiom) override;

    void get_variables(unordered_set<string> & variables) override;

    bool is_not_free(unordered_set<string> & variables) override;

    bool equals_one_replace(Base *, Base * from, Base *& to) override;

};

class Quantifier : public Operation, public Variable {
public:
    Base *expression;
    Variable *predicate;

    string to_string() override;

    Quantifier(string _operation, Base *_expression, string predicate);

    void set_axiom(bool is_axiom) override;

    void get_variables(unordered_set<string> & variables) override;

    bool is_not_free(unordered_set<string> & variables) override;

    bool equals_one_replace(Base *, Base * from, Base *& to) override;

};

#endif //MATHLOGICHW_BASE_H
