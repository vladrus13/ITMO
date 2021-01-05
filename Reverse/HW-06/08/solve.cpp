#include <iostream>
#include <vector>
#include <algorithm>
#include <cmath>

using namespace std;

int main() {
    vector <int> a{106, 112, 101, 107, 127, 119, 121, 102, 119, 120, 109, 120, 121, 120, 109, 115, 114, 99, 109, 119, 99, 114, 115, 120, 99, 120, 108, 105, 99, 119, 115, 112, 121, 120, 109, 115, 114, 129};
    for (int i : a) {
        cout << (char) (i + 252);
    }
    return 0;
}
