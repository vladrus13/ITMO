//
// Created by vladkuznetsov on 20.06.2020.
//

#include <string>

using namespace std;

enum Type {
    AXIOM, MODUS_PONENS, FORMAL_AXIOM, INTRO
};

class SolveStatus {
public:
    Type type;
    string left, right;
    int first;
    int number;
    bool used;
};