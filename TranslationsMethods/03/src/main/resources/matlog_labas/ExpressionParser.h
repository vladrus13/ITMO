#include <map>

using namespace std;

class ExpressionParser {
    string parsed;
    int iterator;
    int proofed;
    int counter = 0;

    bool is_any();

    void skip_spaces();

    string parse_token();

    string parse_and();

    string parse_or();

    string parse_implication();

    string parse(string s);

    void add_pair(string s, int h);

    void inc_counter();

    vector <string> getProofed();

    int getCounter();
};
