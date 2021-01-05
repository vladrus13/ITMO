#include <iostream>
#include <set>
#include <vector>
#include <cstring>
#include <map>
#include <algorithm>
#include <csignal>

using namespace std;

int main() {
    for (int i = 0; i < 1e5; i++) {
        int v4 = (5 * i * i % 256 - 34 * i + 24) & 0x800000FF;
        if ((((unsigned int) (v4 - 1)) | 0xFFFFFF00) == -1) {
            cout << i << endl;
        }
    }
    return 0;
}
