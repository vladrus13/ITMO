/// what about the useless text at the beginning
//                               of the program?
///                 I like to do useless things.
//       The program is made by vladrus13 (2019)

// Do you want to optimize?
#define _CRT_SECURE_NO_WARNINGS
#include <iostream>
#include <algorithm>
#include <random>
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
#include <random>

typedef long double ld;
typedef int64_t ll;
typedef uint64_t ull;
typedef int32_t int32;

using namespace std;

//////////////////////////////////////////////////////////////////
// SYSTEM STARTS

//////////////////////////////////////////////////////////////////
// UTILS

/////////////////////////////////////////////////////////////////
// MAIN

int n;

vector <vector <bool> > g;

vector <int> q;

void cut(int i) {
    int l = 0, r = q.size() - 1, m = 0;
    while (l < r + 1) {
        m = (l + r) / 2;
        if (g[q[m]][i]) l = m + 1;
        else r = m - 1;
    }
    if (g[q[m]][i]) {
        q.insert(q.begin() + m + 1, i);
    } else {
        q.insert(q.begin() + m, i);
    }
}

void ender() {
    for (int i : q) {
        cout << i << ' ';
    }
}

int main() {
    // begin of my useless prog
#ifdef MY_DEBUG
    freopen("input.txt", "r", stdin);
    freopen("output.txt", "w", stdout);
#else
    freopen("guyaury.in", "r", stdin);
    freopen("guyaury.out", "w", stdout);
#endif
    ios_base::sync_with_stdio(NULL);
    cin.tie(NULL);
    cout.tie(NULL);
    cin >> n;
    char ch;
    g.resize(n + 1, vector <bool> (n + 1, 0));
    for (int i = 1; i <= n; i++) {
        for (int j = 1; j < i; j++) {
            cin >> ch;
            if (ch == '1') {
                g[i][j] = 1;
            } else {
                g[j][i] = 1;
            }
        }
    }
    q.push_back(1);
    for (int i = 2; i <= n; i++){
        cut(i);
    }
    int cutting;
    for (cutting = n - 1; cutting > 0; cutting--) if (g[q[cutting]][q[0]]) break;
    int current = cutting - 1;
    vector <int> templater;
    while (current != cutting) {
        current = cutting;
        for (int i = current + 1; i < n; i++) {
            for (int j = 1; j < current + 1; j++) {
                if (g[q[i]][q[j]]) {
                    templater.clear();
                    for (int k = current + 1; k <= i; k++) templater.push_back(q[k]);
                    for (int k = i; k > i + j - current - 1; k--) q[k] = q[k - i + current];
                    for (int k = j; k < i + j - current; k++) q[k] = templater[k - j];
                    templater.clear();
                    cutting = i;
                    break;
                }
            }
            if (current != cutting) break;
        }
    }
    ender();
    return 0;
}