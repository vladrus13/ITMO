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

const int MAX = 1e6 + 1;

vector <int> some_del(MAX, 0);

void precalc() {
    for (int i = 0; i < some_del.size(); i++) some_del[i] = i;
    for (int i = 2; i < MAX; i++) {
        if (some_del[i] == i) {
            for (ll j = 1LL * i * i; j < MAX; j += i) {
                some_del[j] = i;
            }
        }
    }
}

void read() {
    precalc();
    int n, x;
    cin >> n;
    vector <int> answer;
    while (n--) {
        cin >> x;
        answer.clear();
        while (x != 1) {
            answer.push_back(some_del[x]);
            x /= some_del[x];
        }
        sort(answer.begin(), answer.end());
        for_each(answer.begin(), answer.end(), [](int x) {cout << x << ' '; });
        cout << "\n";
    }
}

void solve() {

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
