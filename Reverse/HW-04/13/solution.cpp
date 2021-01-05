#include <iostream>
#include <set>
#include <vector>
#include <cstring>
#include <map>
#include <algorithm>
#include <csignal>
#include <memory>
#include <sstream>

using namespace std;

int fromHex(const string & s) {
    stringstream strs;
    strs << hex << s;
    int number;
    strs >> number;
    return number;
}

void solve_04() {
    freopen("input.txt", "r", stdin);
    string number;
    vector <int> numbers;
    while (cin >> number) {
        numbers.push_back(fromHex(number));
        if (numbers.back() == 1337) {
            cout << "YES";
        }
    }
    int position = 1337;
    string normalizer;
    while (position != 1) {
        for (int i = 0; i * 9 + 5 < numbers.size(); i++) {
            if (numbers[i * 9 + 5] == position) {
                if (numbers[i * 9 + 4] != 0) {
                    normalizer += (char) numbers[i * 9 + 4];
                }
                position = numbers[i * 9];
                break;
            }
        }
    }
    reverse(normalizer.begin(), normalizer.end());
    cout << normalizer;
}

int main() {
    // 93 66 30 29 117 94 66 25 117 98 25 102 102 117 67 89 117 108 120 26 104 100 27 105 107 126 111 0
    freopen("input.txt", "r", stdin);
    int x;
    while (cin >> x) {
        for (int i = 32; i < 127; i++) {
            if ((i ^ 42) == x) {
                cout << (char) i;
            }
        }
    }
    return 0;
}
