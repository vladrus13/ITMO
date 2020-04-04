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

int32 n, m;
vector <vector <int32> > g1, g2;

vector <int32> ans1, ans2;
vector <bool> used;

void read() {
    int x;
    cin >> n >> m;
    g1.resize(n);
    g2.resize(m);
    used.resize(n);
    ans1.resize(n, -1);
    ans2.resize(m, -1);
    for (int i = 0; i < n; i++) {
        cin >> x;
        x--;
        while (x != -1) {
            g1[i].push_back(x);
            g2[x].push_back(i);
            cin >> x;
            x--;
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
    cout << counter << endl;
    for (int i = 0; i < n; i++) {
        if (ans1[i] != -1) {
            cout << i + 1 << ' ' << ans1[i] + 1 << endl;
        }
    }
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
    solve();
    return 0;
}