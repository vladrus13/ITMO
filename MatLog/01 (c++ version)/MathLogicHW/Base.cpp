//
// Created by vladkuznetsov on 20.06.2020.
//

#include <map>
#include "Base.h"

string Variable::to_string() {
    return variable;
}

bool Variable::equals(Base * base, map <string, pair <int, Base *> > & proofed, int counter) {
    auto *casted = dynamic_cast<Variable *>(base);
    if (casted == nullptr) {
        // it is not Variable
        return false;
    }
    if (base::isLowLetter(casted->variable[0])) {
        if (proofed[casted->variable].first != counter) {
            proofed[casted->variable].first = counter;
            proofed[casted->variable].second = this;
            return true;
        } else {
            return equals(proofed[casted->variable].second, proofed, counter);
        }
    }
    return this->variable == casted->variable;
}

BinaryOperator::BinaryOperator(string _operation, Base *_left, Base * _right)
    : Operation(move(_operation)), left(_left), right(_right) {}

bool BinaryOperator::equals(Base * base, map <string, pair <int, Base *> > & proofed, int counter) {
    auto *casted = dynamic_cast<BinaryOperator *>(base);
    if (casted == nullptr) {
        auto * a = dynamic_cast<Variable *>(base);
        if (a == nullptr || !base::isLowLetter(a->variable[0])) {
            return false;
        }
        if (proofed[a->variable].first != counter) {
            proofed[a->variable].first = counter;
            proofed[a->variable].second = this;
            return true;
        } else {
            return equals(proofed[a->variable].second, proofed, counter);
        }
    }
    return this->operation == casted->operation && left->equals(casted->left, proofed, counter) && right->equals(casted->right, proofed, counter);
}

string BinaryOperator::to_string() {
    return base::OPEN_BRACKET + left->to_string() + operation + right->to_string() + base::CLOSE_BRACKET;
}

UnaryOperator::UnaryOperator(string _operation, Base *_expression)
    :Operation(move(_operation)), expression(_expression) {}

bool UnaryOperator::equals(Base * base, map <string, pair <int, Base *> > & proofed, int counter) {
    auto *casted = dynamic_cast<UnaryOperator *>(base);
    if (casted == nullptr) {
        auto * a = dynamic_cast<Variable *>(base);
        if (a == nullptr || !base::isLowLetter(a->variable[0])) {
            return false;
        }
        if (proofed[a->variable].first != counter) {
            proofed[a->variable].first = counter;
            proofed[a->variable].second = this;
            return true;
        } else {
            return equals(proofed[a->variable].second, proofed, counter);
        }
    }
    return this->operation == casted->operation && expression->equals(casted->expression, proofed, counter);
}

string UnaryOperator::to_string() {
    return operation + expression->to_string();
}
