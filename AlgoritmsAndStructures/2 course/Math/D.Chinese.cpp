/// what about the useless text at the beginning
//                               of the program?
///                 I like to do useless things.
//       The program is made by vladrus13 (2020)

// Do you want to optimize?
#include <iostream>
#include <algorithm>
#include <utility>
#include <vector>
#include <cmath>
#include <deque>
#include <string>
#include <vector>
#include <cmath>
#include <queue>
#include <map>
#include <string>
#include <set>
#include <queue>
#include <iomanip>
#include <bitset>
#include <cassert>
#include <unordered_map>
#include <fstream>
#include <list>

#define _CRT_SECURE_NO_WARNINGS

using namespace std;

typedef int32_t int32;
typedef long double ld;
typedef long long ll;

int tempura(int a, int m) {
    a %= m;
    for (int i = 1; i < m; i++) {
        if ((a * i) % m == 1) return i;
    }
    return 0;
}

int cto(vector <int> a, vector <int> b) {
    assert(a.size() == b.size());
    int n = a.size();
    vector <vector <int> > garner(n, vector <int> (n));
    for (int i = 0; i < n; i++) {
        for (int j = i + 1; j < n; j++) {
            garner[i][j] = tempura(b[i], b[j]);
        }
    }
    int answer = 0, power = 1;
    vector <int> solver(n, 0);
    for (int i = 0; i < n; i++) {
        solver[i] = a[i];
        for (int j = 0; j < i; j++) {
            solver[i] = (solver[i] - solver[j]) * garner[j][i];
            solver[i] = solver[i] % b[i];
            if (solver[i] < 0) {
                solver[i] += b[i];
            }
        }
        answer += power * solver[i];
        power *= b[i];
    }
    return answer;
}

void read() {

}

void solve() {
    ll a, b, c, d;
    cin >> a >> b >> c >> d;
    while (a % d != b) {
        a += c;
    }
    cout << a << endl;
}

void write() {

}

int main() {
    // begin of my useless prog
#ifdef MY_DEBUG
    freopen("input.txt", "r", stdin);
    freopen("output.txt", "w", stdout);
#else
#endif
    ios_base::sync_with_stdio(NULL);
    cin.tie(NULL);
    cout.tie(NULL);
    read();
    solve();
    write();
    return 0;
}
