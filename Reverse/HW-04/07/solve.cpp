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

int main() {
    vector <int> hello = {1, 1};
    int position = 2;
    while (true) {
        hello.push_back(hello[position - 1] + hello[position - 2] + 1);
        if (hello.back() == 866988873) {
            cout << position + 1;
            exit(0);
        }
        position++;
    }
    return 0;
}
