//
// Created by vladkuznetsov on 20.06.2020.
//

#include "SolveE.h"

map <string, SolveStatus> proofed;
vector <pair <int, string> > answer;

void get_bam(const string& edit) {
    auto & expression = proofed[edit];
    if (expression.used) return;
    expression.used = true;
    answer.emplace_back(expression.first, edit);
    if (expression.type == Type::MODUS_PONENS) {
        get_bam(expression.left);
        get_bam(expression.second);
    }
}

void solve() {
    freopen("a.in", "r", stdin);
    freopen("a.out", "w", stdout);
    iostream::sync_with_stdio(false);
    cin.tie(nullptr);
    cout.tie(nullptr);
    ExpressionParser parser;
    string start;
    string line;
    getline(cin, start);
    string hypo, stat;
    vector<Base *> hypos;
    for (int i = 0; i < start.size(); i++) {
        if (start[i] == '|' && start[i + 1] == '-') {
            hypo = start.substr(0, i);
            stat = start.substr(i + 2);
            break;
        }
    }
    hypo.push_back(',');
    int last = 0;
    for (int i = 0; i < hypo.size(); i++) {
        if (hypo[i] == ',') {
            hypos.emplace_back(parser.parse(hypo.substr(last, i - last)));
            last = i + 1;
        }
    }
    Base *statement = parser.parse(stat);

    vector<Base *> axioms(10);
    axioms[0] = parser.parse("a -> (b -> a)");
    axioms[1] = parser.parse("(a -> b) -> (a -> b -> c) -> (a -> c)");
    axioms[2] = parser.parse("a -> b -> a & b");
    axioms[3] = parser.parse("a & b -> a");
    axioms[4] = parser.parse("a & b -> b");
    axioms[5] = parser.parse("a -> a | b");
    axioms[6] = parser.parse("b -> a | b");
    axioms[7] = parser.parse("(a -> c) -> (b -> c) -> (a | b -> c)");
    axioms[8] = parser.parse("(a -> b) -> (a -> !b) -> !a");
    axioms[9] = parser.parse("!!a -> a");

    parser.add_pair("a", {0, nullptr});
    parser.add_pair("b", {0, nullptr});
    parser.add_pair("c", {0, nullptr});

    vector <Base *> sentences;
    map <string, vector <BinaryOperator *> > impl;
    int size = 0;

    while (getline(cin, line)) {
        auto * parsed = parser.parse(line);
        line = parsed->to_string();
        sentences.emplace_back(parsed);
        if (proofed.count(line) == 0) {
            bool is_ready = false;
            if (impl.count(line)) {
                for (auto & it : impl[line]) {
                    if (proofed.count(it->left->to_string()) && proofed.count(it->to_string())) {
                        proofed[line].type = Type::MODUS_PONENS;
                        proofed[line].first = size++;
                        proofed[line].left = it->to_string();
                        proofed[line].second = it->left->to_string();
                        is_ready = true;
                        break;
                    }
                }
                if (is_ready) {
                    impl.erase(line);
                }
            }
            if (!is_ready) {
                for (int i = 0; i < hypos.size(); i++) {
                    parser.inc_counter();
                    if (parsed->equals(hypos[i], parser.getProofed(), parser.getCounter())) {
                        proofed[line].type = Type::HYPOTHESIS;
                        proofed[line].first = size++;
                        proofed[line].number = i;
                        is_ready = true;
                        break;
                    }
                }
                if (!is_ready) {
                    for (int i = 0; i < axioms.size(); i++) {
                        parser.inc_counter();
                        if (parsed->equals(axioms[i], parser.getProofed(), parser.getCounter())) {
                            proofed[line].type = Type::AXIOM;
                            proofed[line].first = size++;
                            proofed[line].number = i;
                            is_ready = true;
                            break;
                        }
                    }
                }
            }
            if (is_ready) {
                auto * rek = dynamic_cast<BinaryOperator *>(parsed);
                if (rek != nullptr && rek->operation == base::IMPLICATION) {
                    impl[rek->right->to_string()].emplace_back(rek);
                }
            } else {
                cout << "Proof is incorrect";
                return;
            }
        }
    }
    parser.inc_counter();
    if (!sentences.back()->equals(statement, parser.getProofed(), parser.getCounter())) {
        cout << "Proof is incorrect";
        return;
    }
    for (int i = 0; i < hypos.size(); i++) {
        cout << hypos[i]->to_string();
        if (i == hypos.size() - 1) {
            cout << " |- ";
        } else {
            cout << ", ";
        }
    }
    cout << statement->to_string() << endl;
    get_bam(statement->to_string());
    sort(answer.begin(), answer.end());
    map <string, int> numerzirer;
    for (int i = 0; i < answer.size(); i++) {
        numerzirer[answer[i].second] = i + 1;
    }
    for (int i = 0; i < answer.size(); i++) {
        cout << "[" << i + 1 << ". ";
        if (proofed[answer[i].second].type == Type::AXIOM) {
            cout << "Ax. sch. " << proofed[answer[i].second].number + 1 << "] ";
        }
        if (proofed[answer[i].second].type == Type::HYPOTHESIS) {
            cout << "Hypothesis " << proofed[answer[i].second].number + 1 << "] ";
        }
        if (proofed[answer[i].second].type == Type::MODUS_PONENS) {
            cout << "M.P. " << numerzirer[proofed[answer[i].second].left] << ", " << numerzirer[proofed[answer[i].second].second] << "] ";
        }
        cout << answer[i].second << endl;
    }
}