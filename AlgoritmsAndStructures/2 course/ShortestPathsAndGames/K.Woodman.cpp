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

int32 n;
vector <vector <pair <int32, int32> > > g;
vector <ll> grandi;

void dfs(int32 v, int32 p) {
    ll ender = 0;
    for (pair <int32, int32> u : g[v]) {
        if (u.first != p) {
            if (grandi[u.first] == (ull) -1) {
                dfs(u.first, v);
            }
            ender = ender xor grandi[u.first] + 1;
        }
    }
    grandi[v] = ender;
}

int32 go(int32 v, int32 p, int32 need) {
    for (pair <int32, int32> u : g[v]) {
        if (u.first != p) {
            int32 new_need = need ^ grandi[v] ^ (grandi[u.first] + 1);
            if (new_need == 0) return u.second;
            int32 ret = go(u.first, v, new_need - 1);
            if (ret != -1) return ret;
        }
    }
    return -1;
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
    int32 v, u, s;
    cin >> n >> s;
    s--;
    g.resize(n);
    grandi.resize(n, -1);
    for (int32 i = 0; i < n - 1; i++) {
        cin >> v >> u;
        v--, u--;
        g[v].push_back({u, i});
        g[u].push_back({v, i});
    }
    dfs(s, -1);
    if (grandi[s] == 0) {
        cout << 2 << endl;
    } else {
        cout << 1 << endl;
        // grandi was not null
        int32 result = go(s, -1, 0);
        cout << result + 1 << endl;
        return 0;
    }
    return 0;
}
