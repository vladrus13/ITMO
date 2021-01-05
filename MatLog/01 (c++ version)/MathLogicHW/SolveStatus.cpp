//
// Created by vladkuznetsov on 20.06.2020.
//

#include <string>

using namespace std;

enum Type {
    HYPOTHESIS, AXIOM, MODUS_PONENS
};

class SolveStatus {
public:
    Type type;
    string left, second;
    int first;
    int number;
    bool used;
};