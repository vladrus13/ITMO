#include <iostream>

using namespace std;

int main() {
    int n;
    cin >> n;
    for (int i = 0; i < n; i++) {
        cout << "90";
        if (i + 1 != n) {
            cout << ' ';
        }
    }
    return 0;
}
