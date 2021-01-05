//
// Created by vladkuznetsov on 20.06.2020.
//

#include <map>
#include <utility>
#include "Base.h"

string Variable::to_string() {
    return variable;
}

void Variable::set_axiom(bool _is_axiom) {
    is_axiom = _is_axiom;
}

void Variable::get_variables(unordered_set<string> &variables) {
    if (variable != "0") variables.insert(variable);
}

bool Variable::is_not_free(unordered_set<string> &variables) {
    return variables.find(variable) == variables.end();
}

bool Variable::equals_one_replace(Base *base, Base *from, Base *&to) {
    if (this->variable != from->to_string()) {
        return this->to_string() == base->to_string();
    } else {
        if (to == nullptr) {
            to = base;
            return true;
        } else {
            return to->to_string() == base->to_string();
        }
    }
}

BinaryOperator::BinaryOperator(string _operation, Base *_left, Base *_right)
        : Operation(_operation), left(_left), right(_right) { operation = _operation; }


string BinaryOperator::to_string() {
    return base::OPEN_BRACKET + left->to_string() + operation + right->to_string() + base::CLOSE_BRACKET;
}

void BinaryOperator::set_axiom(bool _is_axiom) {
    is_axiom = _is_axiom;
    left->set_axiom(_is_axiom);
    right->set_axiom(_is_axiom);
}

void BinaryOperator::get_variables(unordered_set<string> &variables) {
    left->get_variables(variables);
    right->get_variables(variables);
}

bool BinaryOperator::is_not_free(unordered_set<string> &variables) {
    return left->is_not_free(variables) && right->is_not_free(variables);
}

bool BinaryOperator::equals_one_replace(Base *base, Base *from, Base *&to) {
    auto casted = dynamic_cast<BinaryOperator *>(base);
    if (casted == nullptr) return false;
    return this->operation == casted->operation &&
            this->left->equals_one_replace(casted->left, from, to) &&
           this->right->equals_one_replace(casted->right, from, to);
}

UnaryOperator::UnaryOperator(string _operation, Base *_expression)
        : Operation(_operation), expression(_expression) { operation = _operation; }


string UnaryOperator::to_string() {
    if (operation == "!") {
        return base::OPEN_BRACKET + operation + expression->to_string() + base::CLOSE_BRACKET;
    }
    if (operation == "'") {
        return expression->to_string() + operation;
    }
    // throw new UnsupportedOperateException
    return "";
}

void UnaryOperator::set_axiom(bool _is_axiom) {
    is_axiom = _is_axiom;
    expression->set_axiom(_is_axiom);
}

void UnaryOperator::get_variables(unordered_set<string> &variables) {
    expression->get_variables(variables);
}

bool UnaryOperator::is_not_free(unordered_set<string> &variables) {
    return expression->is_not_free(variables);
}

bool UnaryOperator::equals_one_replace(Base *base, Base *from, Base *&to) {
    auto casted = dynamic_cast<UnaryOperator *>(base);
    if (casted == nullptr) return false;
    return this->operation == casted->operation &&
            this->expression->equals_one_replace(casted->expression, from, to);
}

string Quantifier::to_string() {
    return base::OPEN_BRACKET + this->operation +
           predicate->to_string() + base::POINT + expression->to_string() + base::CLOSE_BRACKET;
}

void Quantifier::set_axiom(bool _is_axiom) {
    is_axiom = _is_axiom;
    predicate->set_axiom(_is_axiom);
    expression->set_axiom(_is_axiom);
}

Quantifier::Quantifier(string _operation, Base *_expression, string _predicate)
        : Operation(_operation), expression(_expression), Variable(_predicate) {
    operation = _operation;
    predicate = new Variable(_predicate);
}

void Quantifier::get_variables(unordered_set<string> &variables) {
    bool is_it = variables.find(variable) == variables.end();
    expression->get_variables(variables);
    if (is_it) {
        variables.erase(variable);
    }
}

bool Quantifier::is_not_free(unordered_set<string> &variables) {
    return variables.find(predicate->variable) != variables.end() || expression->is_not_free(variables);
}

bool Quantifier::equals_one_replace(Base *base, Base *from, Base *&to) {
    auto casted = dynamic_cast<Quantifier * >(base);
    if (casted == nullptr) {
        return false;
    }
    return this->operation == casted->operation &&
            (this->to_string() == base->to_string() || (from->to_string() != this->variable &&
                    this->expression->equals_one_replace(casted->expression, from, to)));
}