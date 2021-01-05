#include <iostream>
#include <string>

using namespace std;

int main() {
    int x;
    int cond_size = 1000;
    int m = 10000 - cond_size;
    int count_key_0 = 0;
    string s;
    while (cond_size--) {
        cin >> x;
        cout << x << endl;
        cin >> s;
        if (s == "YES") { count_key_0++; }
    }
    int key = count_key_0 > 500 ? 0 : 1;
    while (m--) {
        cin >> x;
        cout << (x ^ key) << endl;
        cin >> s;
    }
    return 0;
}
