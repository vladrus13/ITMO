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

int32 n, m;
vector <vector <int32> > g;
vector <int32> grandi;

void dfs(int v) {
    set <int> mexer;
    for (int32 u : g[v]) {
        if (grandi[u] == 1e9) {
            dfs(u);
        }
        mexer.insert(grandi[u]);
    }
    for (int32 i = 0; i < n; i++) {
        if (mexer.count(i) == 0) {
            grandi[v] = i;
            return;
        }
    }
    grandi[v] = 0;
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
    int32 v, u;
    cin >> n >> m;
    g.resize(n);
    grandi.resize(n, 1e9);
    for (int32 i = 0; i < m; i++) {
        cin >> v >> u;
        v--, u--;
        g[v].push_back(u);
    }
    for (int32 i = 0; i < n; i++) {
        if (grandi[i] == 1e9) {
            dfs(i);
        }
    }
    for (int32 i : grandi) {
        cout << i << endl;
    }
    return 0;
}
