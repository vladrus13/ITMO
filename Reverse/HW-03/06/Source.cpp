#include <iostream>
#include <set>
#include <vector>
#include <cstring>
#include <map>
#include <algorithm>

using namespace std;

int max_nulls = 0;

vector <pair <string, pair <bool, char> > > best;

string get_without_nulls(const string &s) {
    string answer;
    bool is_not_null_exits = false;
    for (char i : s) {
        is_not_null_exits |= i != '0';
        if (is_not_null_exits) {
            answer += i;
        }
    }
    return answer;
}

void rec(vector <pair<string, pair <bool, char> > > &decoder, const string &s, int index, string& answer) {
    int it = index;
    bool is_detected = false;
    for (int i = 1; i <= 12 && it + i < s.size(); i++) {
        for (int j = 0; j < decoder.size(); j++) {
            if (decoder[j].first == s.substr(it, i)) {
                pair<bool, char> getting = decoder[j].second;
                if (getting.first) {
                    answer += getting.second;
                    rec(decoder, s, it + i, answer);
                    answer.pop_back();
                    is_detected = true;
                    break;
                }
            }
        }
    }
    if (is_detected) return;
    for (int i = 1; i <= 12 && it + i < s.size(); i++) {
        for (int j = 0; j < decoder.size(); j++) {
            if (decoder[j].first == get_without_nulls(s.substr(it, i)) && !decoder[j].second.first) {
                decoder[j].first = s.substr(it, i);
                decoder[j].second.first = true;
                answer += decoder[j].second.second;
                rec(decoder, s, it + i, answer);
                answer.pop_back();
                decoder[j].second.first = false;
                decoder[j].first = get_without_nulls(s.substr(it, i));
            }
        }
    }
    cout << answer << " ---- " << s.size() - it << endl;
    if (it == s.size()) {
        cout << answer << endl;
        best = decoder;
    }
}

void count_max_nulls(const string& s) {
    int nulls = 0;
    for (char ch : s) {
        if (ch == '0') nulls++;
        else {
            max_nulls = max(max_nulls, nulls);
            nulls = 0;
        }
    }
    max_nulls = max(max_nulls, nulls);
}

string to_string_my(int x) {
    string returned;
    while (x) {
        returned += '0' + x % 2;
        x /= 2;
    }
    reverse(returned.begin(), returned.end());
    return returned;
}

int main() {
    freopen("input.txt", "r", stdin);
    string input;
    cin >> input;
    count_max_nulls(input);
    char ch, usless_eq, delim;
    int number;
    vector <pair <string, pair <bool, char> > > decoded;
    while (cin >> ch) {
        cin >> usless_eq >> number >> delim;
        decoded.push_back({to_string_my(number), {false, ch}});
    }
    vector <pair <string, pair <bool, char> > > cheat;
    cheat.push_back({"000000", {true, 'I'}});
    cheat.push_back({"100011", {true, 'L'}});
    cheat.push_back({"001", {true, '_'}});
    cheat.push_back({"0101", {true, 'i'}});
    cheat.push_back({"100101", {true, 'k'}});
    cheat.push_back({"0001", {true, 'e'}});
    cheat.push_back({"000001", {true, 'T'}});
    cheat.push_back({"110", {true, 'o'}});
    cheat.push_back({"100110", {true, 'S'}});
    cheat.push_back({"01001", {true, 't'}});
    cheat.push_back({"111011", {true, 'u'}});
    cheat.push_back({"00001", {true, 'd'}});
    cheat.push_back({"11100", {true, 'y'}});

    for (auto & i : decoded) {
        for (auto & j : cheat) {
            if (j.second.second == i.second.second) {
                i = j;
            }
        }
    }
    string s;
    rec(decoded, input, 0, s);
    for (const auto& x : best) {
        cout << x.first << " " << x.second.second << endl;
    }
    return 0;
}
