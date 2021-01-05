#include <iostream>
#include <vector>
#include <algorithm>
#include <cmath>

using namespace std;

string to_string_m(unsigned int x) {
    string s;
    for (int i = 0; i < 4; i++) {
        s.push_back((char) x % 256);
        x /= 256;
    }
    return s;
}


int main() {
    vector <unsigned int> a{0x3033E1B0, 0x6B3CB8BD, 0x3861E4B2, 0x6C67B9E1, 0x6A3DB5E1, 0x6D36B1B6, 0x3832E4E1, 0x30383636};
    unsigned int xorist = 0x8048085;
    for (unsigned int i : a) {
        cout << to_string_m(i ^ xorist);
    }
    cout << endl;
    xorist = 0x8048085;
    for (int i = 7; i >= 0; i--) {
        cout << to_string_m(a[i] ^ xorist);
    } // 5a78888c7de0d9cdd59b312edd606680
    return 0;
}
