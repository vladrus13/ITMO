/// what about the useless text at the beginning
//                               of the program?
///                 I like to do useless things.
//       The program is made by vladrus13 (2020)

// Do you want to optimize?
#define _CRT_SECURE_NO_WARNINGS

#include <iostream>
#include <algorithm>
#include <vector>
#include <string>
#include <iomanip>
#include <list>
#include <math.h>

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

int32 n;
vector <vector <int32> > g1, g2;

vector <int32> ans1, ans2;
vector <bool> used;

int32 dist(int32 x1, int32 y1, int32 x2, int32 y2) {
    return abs(x1 - x2) + abs(y1 - y2);
}

vector <int32> timer, fpx, fpy, spx, spy;

bool is_ok(int f, int s) {
    return timer[f] + dist(fpx[f], fpy[f], spx[f], spy[f]) + dist(spx[f], spy[f], fpx[s], fpy[s]) < timer[s];
}

void read() {
    cin >> n;
    timer.resize(n);
    fpx.resize(n);
    spx.resize(n);
    fpy.resize(n);
    spy.resize(n);
    int x, y;
    char ch;
    for (int32 i = 0; i < n; i++) {
        cin >> x >> ch >> y >> fpx[i] >> fpy[i] >> spx[i] >> spy[i];
        timer[i] = x * 60 + y;
    }
    g1.resize(n);
    g2.resize(n);
    ans1.resize(n, -1);
    ans2.resize(n, -1);
    used.resize(n, false);
    for (int32 i = 0; i < n; i++) {
        for (int32 j = 0 ; j < n; j++) {
            if (is_ok(i, j)) {
                g1[i].push_back(j);
                g2[i].push_back(j);
            }
        }
    }
}

// kun? no) "i like Ford"
bool dfs(int32 x) {
    if (used[x]) return false;
    used[x] = true;
    for (int32 y : g1[x]) {
        if (ans2[y] == -1) {
            ans1[x] = y;
            ans2[y] = x;
            return true;
        } else {
            if (dfs(ans2[y])) {
                ans1[x] = y;
                ans2[y] = x;
                return true;
            }
        }
    }
    return false;
}

void Ford() {
    bool isPath = true;
    while (isPath) {
        isPath = false;
        used.assign(n, false);
        for (int i = 0; i < n; i++) {
            if (ans1[i] == -1) {
                if (dfs(i)) {
                    isPath = true;
                }
            }
        }
    }
}

void solve() {
    read();
    Ford();
    int32 counter = 0;
    for (int i = 0; i < n; i++) {
        counter += (ans1[i] == -1 ? 0 : 1);
    }
    cout << n - counter << endl;
}

int main() {
    // begin of my useless prog
#ifdef MY_DEBUG
    freopen("input.txt", "r", stdin);
    freopen("output.txt", "w", stdout);
#else
    freopen("taxi.in", "r", stdin);
    freopen("taxi.out", "w", stdout);
#endif
    ios_base::sync_with_stdio(NULL);
    cin.tie(NULL);
    cout.tie(NULL);
    solve();
    return 0;
}
