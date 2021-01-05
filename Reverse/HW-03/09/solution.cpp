#include <iostream>
#include <set>
#include <vector>
#include <cstring>
#include <map>
#include <algorithm>

using namespace std;

long long sub_4006BD(string input, int size, string &new_string, int code) {
    int temp; // eax
    int temp2; // eax
    int new_string_iterator; // [rsp+20h] [rbp-18h]
    long long symbol; // [rsp+24h] [rbp-14h]
    int input_iterator; // [rsp+28h] [rbp-10h]
    int i; // [rsp+2Ch] [rbp-Ch]
    int temp3; // [rsp+34h] [rbp-4h]
    string byte_400918 = "BCDEFGHIJKLMNOPQRSTUVWXYZ234567";
    if (size < 0 || size > 0x10000000)
        return 0xFFFFFFFFLL;
    new_string_iterator = 0;
    if (size > 0) {
        symbol = input[0];
        input_iterator = 1;
        for (i = 8; new_string_iterator < code && (i > 0 || input_iterator < size); i -= 5) {
            if (i <= 4) {
                if (input_iterator >= size) {
                    symbol <<= 5 - i;
                    i = 5;
                } else {
                    temp = input_iterator++;
                    symbol = input[temp] | (symbol << 8);
                    i += 8;
                }
            }
            temp3 = (symbol >> (i - 5)) & 0x1F;
            temp2 = new_string_iterator++;
            new_string[temp2] = byte_400918[temp3];
        }
    }
    if (new_string_iterator < code)
        new_string[new_string_iterator] = 0;
    return (unsigned int) new_string_iterator;
}

set <string> masks;
set<pair <string, string> > best;
string code = "IYYTIR27GE2V6QSBKNCV6T2GL5AU4WIK";
int count_best = 0;

int count(string a, string b) {
    int i = 0;
    while (a[i] == b[i]) {
        i++;
    }
    return i;
}

void rec(const string& s) {
    if (s.size() > 1) {
        string generate(328, 0);
        for (const string& mask : masks) {
            string ss = mask + s;
            sub_4006BD(ss, ss.size(), generate, 320);
            // cout << ss << "   " << generate << endl;
            if (count(generate, code) > count_best) {
                best.clear();
                count_best = count(generate, code);
            }
            if (count(generate, code) == count_best) {
                best.insert({ss, generate});
            }
        }
    } else {
        /*
        for (char ch = 'A'; ch <= 'Z'; ch++) {
            rec(s + ch);
        }
        for (char ch = '0'; ch <= '9'; ch++) {
            rec(s + ch);
        }
        for (char ch = 'a'; ch <= 'z'; ch++) {
            rec(s + ch);
        }*/
        for (char ch = 0; ch < 127; ch++) {
            rec(s + ch);
        }
    }
}

string to_string_mine(int x) {
    string returned = "";
    while (x) {
        returned += '0' + x % 2;
        x /= 2;
    }
    reverse(returned.begin(), returned.end());
    return string(5 - returned.size(), '0') + returned;
}

int to_int(string s) {
    int returned = 0, power = 1;
    for (int i = s.size() - 1; i >= 0; i--) {
        returned += (s[i] - '0') * power;
        power *= 2;
    }
    return returned;
}

int main() {
    if (0) {
        string input;
        string generate(328, 0);
        cin >> input;
        int size = input.size();
        sub_4006BD(input, size, generate, 320);
        if (!strcmp(generate.c_str(), "IYYTIR27GE2V6QSBKNCV6T2GL5AU4WIK"))
            cout << ("Correct!");
        else
            cout << generate << endl;
        cout << ("NOT correct");
    } else {
        string to_character;
        map <char, int> positions;
        string byte_400918 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567";
        for (int i = 0; i < byte_400918.size(); i++) {
            positions[byte_400918[i]] = i;
        }
        for (char ch : code) {
            to_character += to_string_mine((int) positions[ch]);
        }
        string answer;
        for (int i = 0; i < to_character.size() - 8; i += 4) {
             answer += (unsigned char) to_int(to_character.substr(i, i + 8));
        }
        cout << answer << endl;
        string generate(328, 0);
        int size = answer.size();
        sub_4006BD(answer, size, generate, 320);
        cout << generate << endl;
    }
    return 0;
}
