#include <iostream>
#include <set>
#include <vector>
#include <cstring>
#include <map>
#include <algorithm>
#include <csignal>
#include <memory>

using namespace std;

int main() {
    string s;
    vector <int> numbers = {6, 0, 104, 6, 2, 104, 4, 3, 2, 126, 126, 126, 78, 0, 0};
    for (int number : numbers) {
        cout << (char) (number ^ 0x37);
    }
    return 0;
}
