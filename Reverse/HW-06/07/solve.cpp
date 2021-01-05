#include <iostream>
#include <vector>
#include <algorithm>
#include <cmath>

using namespace std;

int main() {
    string s;
    cin >> s;
    int count = 0;
    for (char i : s) {
        if (i == 'a') count++;
        else {
            cout << (char) (count + 1);
            count = 0;
        }
    }
    return 0;
}
