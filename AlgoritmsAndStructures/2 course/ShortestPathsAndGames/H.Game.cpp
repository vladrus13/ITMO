/// what about the useless text at the beginning
//                               of the program?
///                 I like to do useless things.
//       The program is made by vladrus13 (2019)

// Do you want to optimize?
#define _CRT_SECURE_NO_WARNINGS
#include <iostream>
#include <algorithm>
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

int n, m, s;

vector <vector <int> > g;

// return 1, if first player lose, 0 else
bool dfs(int v) {
    bool returned = false;
    for (int u : g[v]) {
        returned |= dfs(u);
    }
    return !returned;
}

int main() {
    // begin of my useless prog
#ifdef MY_DEBUG
    freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
    freopen("game.in", "r", stdin);
	freopen("game.out", "w", stdout);
#endif
    ios_base::sync_with_stdio(NULL);
    cin.tie(NULL);
    cout.tie(NULL);
    cin >> n >> m >> s;
    s--;
    g.resize(n);
    int v, u;
    for (int i = 0; i < m; i++) {
        cin >> v >> u;
        v--, u--;
        g[v].push_back(u);
    }
    cout << (dfs(s) ? "Second player wins\n" : "First player wins\n");
    return 0;
}
