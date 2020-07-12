//
// Created by vladkuznetsov on 20.06.2020.
//

#include "SolveE.h"
#include <fstream>

typedef unsigned long long ull;

map<string, SolveStatus> proofed;
string error;
ExpressionParser parser;
map<string, map<int, BinaryOperator *> > impl;

ull hashing(const string& s) {
    ull answer = 0, p = 33, pow = 1;
    for (char i : s) {
        answer += i * pow;
        pow *= p;
    }
    return answer;
}

bool equals(Base * a, Base * b) {
    if (!a->hasher) a->set_hash(hashing(a->to_string()));
    if (!b->hasher) b->set_hash(hashing(b->to_string()));
    return a->hash == b->hash && a->to_string() == b->to_string();
}

bool equals(Base * small, Base * base, Base * &A, Base * &B, Base * &C) {
    auto try_var = dynamic_cast<Variable *>(small);
    auto try_bin = dynamic_cast<BinaryOperator *>(small);
    auto try_un = dynamic_cast<UnaryOperator *>(small);
    auto try_bin_b = dynamic_cast<BinaryOperator *>(base);
    auto try_un_b = dynamic_cast<UnaryOperator *>(base);

    if (try_var != nullptr) {
        if (small->to_string() == "A") {
            if (A) {
                return equals(A, base);
            } else {
                A = base;
                return true;
            }
        }
        if (small->to_string() == "B") {
            if (B) {
                return equals(B, base);
            } else {
                B = base;
                return true;
            }
        }
        if (small->to_string() == "C") {
            if (C) {
                return equals(C, base);
            } else {
                C = base;
                return true;
            }
        }
    }
    if (try_bin != nullptr) {
        return try_bin_b != nullptr &&
                try_bin->operation == try_bin_b->operation &&
                equals(try_bin->left, try_bin_b->left, A, B, C) &&
                equals(try_bin->right, try_bin_b->right, A, B, C);
    }
    if (try_un != nullptr) {
        return try_un_b != nullptr &&
               try_un->operation == try_un_b->operation &&
               equals(try_un->expression, try_un_b->expression, A, B, C);
    }
    return false;
}

// (@x.F)->F[x:=f]
int isAxiom11(Base *base) {
    auto *binary = dynamic_cast<BinaryOperator *>(base); // (@x.F)->F[x:=f]
    if (binary == nullptr) {
        return -1;
    }
    if (binary->operation != "->") {
        return -1;
    }
    auto *left = dynamic_cast<Quantifier *>(binary->left); // @x.F
    Base *right = binary->right; // F[x:=f]
    if (left == nullptr || left->operation != "@") {
        return -1;
    }
    auto *F = left->expression; // F
    Base * replacer = nullptr;
    if (!F->equals_one_replace(right, new Variable(left->variable), replacer)) {
        return -1;
    }
    unordered_set<string> variables;
    if (replacer != nullptr) replacer->get_variables(variables);
    if (variables.empty()) {
        return 1;
    }
    if (!right->is_not_free(variables)) {
        return 1;
    } else {
        error = " variable " + left->variable + " is not free for term " + replacer->to_string() + " in ?@-axiom.";
        return 0;
    }
}

// F[x:=f]->(?x.F)
int isAxiom12(Base *base) {
    auto *binary = dynamic_cast<BinaryOperator *>(base); // F[x:=f]->(?x.F)
    if (binary == nullptr) {
        return -1;
    }
    if (binary->operation != "->") {
        return -1;
    }
    Base *left = binary->left; // F[x:=f]
    auto *right = dynamic_cast<Quantifier *>(binary->right); // ?x.F
    if (right == nullptr || right->operation != "?") {
        return -1;
    }
    auto *F = right->expression; // F
    Base * replacer = nullptr;
    if (!F->equals_one_replace(left, new Variable(right->variable), replacer)) {
        return -1;
    }
    unordered_set<string> variables;
    if (replacer != nullptr) replacer->get_variables(variables);
    if (variables.empty()) {
        return 1;
    }
    if (!left->is_not_free(variables)) {
        return 1;
    } else {
        error = " variable " + right->variable + " is not free for term " + replacer->to_string() + " in ?@-axiom.";
        return 0;
    }
}

// (P[x:=0]&(@x.P->P[x:=x']))->P
int isAxiomA9(Base *base) {
    auto *binaryOperator = dynamic_cast<BinaryOperator *>(base);
    if (binaryOperator == nullptr || binaryOperator->operation != "->") {
        return 0;
    }
    auto *P = binaryOperator->right; // P
    auto left = dynamic_cast<BinaryOperator *>(binaryOperator->left); //(P[x:=0]&(@x.P->P[x:=x']))
    if (left == nullptr || left->operation != "&") {
        return 0;
    }
    auto *P_0 = left->left; // F[x:=0]
    auto *recursion = dynamic_cast<Quantifier *>(left->right); // @x.P->P[x:=x']
    if (recursion == nullptr || recursion->operation != "@") {
        return 0;
    }
    Base * null = new Variable("0");
    if (!P->equals_one_replace(P_0, new Variable(recursion->variable), null)) {
        return 0;
    }

    auto *under_recursion = dynamic_cast<BinaryOperator *>(recursion->expression); // P->P[x:=x']
    if (under_recursion == nullptr || under_recursion->operation != "->") {
        return 0;
    }
    auto *left_under = under_recursion->left; // P
    auto *right_under = under_recursion->right; // P[x:=x']
    if (P->to_string() != left_under->to_string()) {
        return 0;
    }
    Base * increace_x = new UnaryOperator("'", new Variable(recursion->variable));
    if (!P->equals_one_replace(right_under, new Variable(recursion->variable), increace_x)) {
        return 0;
    }
    return 1;
}

// is exist - F->G
// F->(@x.G)
int is_all_intro(Base *base) {
    auto *binary = dynamic_cast<BinaryOperator * >(base);
    if (binary == nullptr || binary->operation != "->") {
        return -1;
    }
    auto *right = dynamic_cast<Quantifier *>(binary->right); // @x.G
    if (right == nullptr || right->operation != "@") {
        return -1;
    }
    auto F = binary->left;
    auto G = right->expression;
    string line = (new BinaryOperator(base::IMPLICATION, F, G))->to_string();
    if (proofed.count(line)) {
        unordered_set<string> variables;
        variables.insert(right->variable);
        if (!F->is_not_free(variables)) {
            error = " variable " + right->variable + " occurs free in ?@-rule.";
            return 0;
        } else {
            return proofed[line].first + 1;
        }
    } else {
        return -1;
    }
}

// is exist - F->G
// (?x.F)->G
int is_exists_intro(Base *base) {
    auto *binary = dynamic_cast<BinaryOperator * >(base);
    if (binary == nullptr || binary->operation != "->") {
        return -1;
    }
    auto *left = dynamic_cast<Quantifier *>(binary->left); // ?x.F
    if (left == nullptr || left->operation != "?") {
        return -1;
    }
    auto F = left->expression;
    auto G = binary->right;
    string line = (new BinaryOperator(base::IMPLICATION, F, G))->to_string();
    if (proofed.count(line)) {
        unordered_set<string> variables;
        variables.insert(left->variable);
        if (!G->is_not_free(variables)) {
            error = " variable " + left->variable + " occurs free in ?@-rule.";
            return 0;
        } else {
            return proofed[line].first + 1;
        }
    } else {
        return -1;
    }
}

string IN_FILE_NAME = "test_in/602";

void setInputName(string s) {
    IN_FILE_NAME = s;
}

void make_solve(string & line, Type type, int first, int number) {
    proofed[line].type = type;
    proofed[line].first = first;
    proofed[line].number = number;
}

void solve() {
#ifdef _DEBUG
    ifstream cin((IN_FILE_NAME + ".in").c_str());
    ofstream cout((IN_FILE_NAME + ".out").c_str());
#endif
    iostream::sync_with_stdio(false);
    cin.tie(nullptr);
    cout.tie(nullptr);
    string start;
    string line;
    getline(cin, start);
    if (start.empty()) {
        return;
    }
    string hypo, stat;
    vector<Base *> hypos;
    for (size_t i = 0; i < start.size(); i++) {
        if (start[i] == '|' && start[i + 1] == '-') {
            hypo = start.substr(0, i);
            stat = start.substr(i + 2);
            break;
        }
    }
    hypo.push_back(',');
    int last = 0;
    for (size_t i = 0; i < hypo.size(); i++) {
        if (hypo[i] == ',') {
            hypos.emplace_back(parser.parse(hypo.substr((unsigned long) last, (unsigned long) i - last)));
            last = (int) i + 1;
        }
    }
    Base *statement = parser.parse(stat);

    vector<Base *> axioms(10);
    vector<Base *> formal_axioms(8);
    axioms[0] = parser.parse("A -> (B -> A)");
    axioms[1] = parser.parse("(A -> B) -> (A -> B -> C) -> (A -> C)");
    axioms[2] = parser.parse("A & B -> A");
    axioms[3] = parser.parse("A & B -> B");
    axioms[4] = parser.parse("A -> B -> A & B");
    axioms[5] = parser.parse("A -> A | B");
    axioms[6] = parser.parse("B -> A | B");
    axioms[7] = parser.parse("(A -> C) -> (B -> C) -> (A | B -> C)");
    axioms[8] = parser.parse("(A -> B) -> (A -> !B) -> !A");
    axioms[9] = parser.parse("!!A -> A");
    formal_axioms[0] = parser.parse("a = b -> a = c -> b = c");
    formal_axioms[1] = parser.parse("a = b -> a' = b'");
    formal_axioms[2] = parser.parse("a' = b' -> a = b");
    formal_axioms[3] = parser.parse("!a' = 0");
    formal_axioms[4] = parser.parse("a + 0 = a");
    formal_axioms[5] = parser.parse("a + b' = (a + b)'");
    formal_axioms[6] = parser.parse("a * 0 = 0");
    formal_axioms[7] = parser.parse("a * b' = a * b + a");

    for (int i = 0; i < 10; i++) {
        axioms[i]->set_axiom(true);
    }

    vector<Base *> sentences;
    int counter = 0;
    cout << "|-" << statement->to_string() << endl;
    while (getline(cin, line)) {
        auto *parsed = parser.parse(line);
        line = parsed->to_string();
        bool is_ready = false;
        string problem;
        // Axioms [i-Ai] for i /in 1, 8
        for (size_t i = 0; i < 8; i++) {
            // Axiom i
            if (!is_ready) {
                parser.inc_counter();
                Base * A = nullptr;
                Base * B = nullptr;
                Base * C = nullptr;
                if (equals(axioms[i], parsed, A, B, C)) {
                    make_solve(line, Type::AXIOM, counter, i);
                    counter++;
                    is_ready = true;
                    cout << "[" << counter << ". ";
                    cout << "Ax. sch. " << i + 1 << "] ";
                    cout << line << '\n';
                    break;
                }
            }
            // Axiom Ai
            if (!is_ready) {
                parser.inc_counter();
                if (equals(parsed, formal_axioms[i])) {
                    make_solve(line, Type::FORMAL_AXIOM, counter, i);
                    counter++;
                    is_ready = true;
                    cout << "[" << counter << ". ";
                    cout << "Ax. A" << i + 1 << "] ";
                    cout << line << '\n';
                    break;
                }
            }
        }
        // Axiom 9
        if (!is_ready) {
            parser.inc_counter();
            Base * A = nullptr;
            Base * B = nullptr;
            Base * C = nullptr;
            if (equals(axioms[8], parsed, A, B, C)) {
                make_solve(line, Type::AXIOM, counter, 8);
                counter++;
                is_ready = true;
                cout << "[" << counter << ". ";
                cout << "Ax. sch. " << 8 + 1 << "] ";
                cout << line << '\n';
            }
        }
        // Axiom A9
        if (!is_ready) {
            int isAxiom = isAxiomA9(parsed);
            if (isAxiom == 1) {
                make_solve(line, Type::FORMAL_AXIOM, counter, 9);
                counter++;
                is_ready = true;
                cout << "[" << counter << ". Ax. sch. A9] ";
                cout << line << "\n";
            }
        }
        // Axiom 10
        if (!is_ready) {
            parser.inc_counter();
            Base * A = nullptr;
            Base * B = nullptr;
            Base * C = nullptr;
            if (equals(axioms[9], parsed, A, B, C)) {
                make_solve(line, Type::AXIOM, counter, 9);
                counter++;
                is_ready = true;
                cout << "[" << counter << ". ";
                cout << "Ax. sch. " << 9 + 1 << "] ";
                cout << line << '\n';
            }
        }
        // Axioms 11
        if (!is_ready) {
            int isAxiom = isAxiom11(parsed);
            if (isAxiom == 0) {
                problem = error;
            }
            if (isAxiom == 1) {
                make_solve(line, Type::AXIOM, counter, 11);
                counter++;
                is_ready = true;
                cout << "[" << counter << ". " << "Ax. sch. " << 11 << "] ";
                cout << line << "\n";
            }
        }
        // Axiom 12
        if (!is_ready) {
            int isAxiom = isAxiom12(parsed);
            if (isAxiom == 0 && problem.empty()) {
                problem = error;
            }
            if (isAxiom == 1) {
                make_solve(line, Type::AXIOM, counter, 12);
                counter++;
                is_ready = true;
                cout << "[" << counter << ". Ax. sch. " << 12 << "] ";
                cout << line << "\n";
            }
        }
        // Modus Ponens
        if (!is_ready && impl.count(line)) {
            counter++;
            int first_index = -1, second_index = -1;
            for (auto iter = impl[line].begin(); iter != impl[line].end(); iter++) {
                auto it = iter->second;
                if (proofed.count(it->left->to_string()) &&
                    proofed.count(it->to_string())) {
                    int first = (int) sentences.size() - 1, second = (int) sentences.size() - 1;
                    while (first > 0 && !equals(sentences[first], it)) first--;
                    while (second > 0 && !equals(sentences[second], it->left)) second--;
                    if (second > second_index || (second == second_index && first > first_index)) {
                        first_index = first;
                        second_index = second;
                        is_ready = true;
                    }
                }
            }
            if (is_ready) {
                make_solve(line, Type::MODUS_PONENS, counter - 1, 0);
                cout << "[" << counter << ". ";
                cout << "M.P. " << second_index + 1 << ", "
                     << first_index + 1 << "] ";
                cout << line << '\n';
            } else {
                counter--;
            }
        }
        // Intro
        if (!is_ready) {
            int ready = is_exists_intro(parsed);
            if (ready == 0) {
                problem = error;
            }
            if (ready > 0) {
                make_solve(line, Type::INTRO, counter, ready);
                counter++;
                is_ready = true;
                cout << "[" << counter << ". ?@-intro " << ready << "] ";
                cout << line << "\n";
            }
            if (!is_ready) {
                int ready_new = is_all_intro(parsed);
                if (ready_new == 0 && ready == -1) {
                    problem = error;
                }
                if (ready_new > 0) {
                    make_solve(line, Type::AXIOM, counter, ready_new);
                    counter++;
                    is_ready = true;
                    cout << "[" << counter << ". ?@-intro " << ready_new << "] ";
                    cout << line << "\n";
                }
            }
        }
        if (is_ready) {
            sentences.emplace_back(parsed);
            auto *rek = dynamic_cast<BinaryOperator *>(parsed);
            if (rek != nullptr && rek->operation == base::IMPLICATION) {
                impl[rek->right->to_string()][counter] = rek;
            }
        } else {
            if (!problem.empty()) {
                cout << "Expression " << counter + 1 << ":" << problem << "\n";
            } else {
                cout << "Expression " << counter + 1 << " is not proved.\n";
            }
            return;
        }
    }
    parser.inc_counter();
    if (!equals(sentences.back(), statement)) {
        cout << "The proof proves different expression.\n";
        return;
    }
}