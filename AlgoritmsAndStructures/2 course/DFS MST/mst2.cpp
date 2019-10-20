/// what about the useless text at the beginning
//                               of the program?
///                 I like to do useless things.
//       The program is made by vladrus13 (2018)

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
#include <stack>

typedef long double ld;
typedef int64_t ll;
// typedef uint64_t ull;
typedef int32_t int32;

using namespace std;
//#pragma comment (linker, "/STACK:5000000000")
#define INF (int)2e9;
#define MOD (ll)(1e9+7)

//////////////////////////////////////////////////////////////////
// SYSTEM STARTS



//////////////////////////////////////////////////////////////////
// UTILS

/////////////////////////////////////////////////////////////////
// MAIN

int n, m;
vector <int> color;
vector <pair <int, int> > answer;
vector <pair <int, pair <int, int> > > g;

int dfs(int v) {
    return (v != color[v] ? (color[v] = dfs(color[v])) : v);
}

void vpn(int a, int b) {
    a = dfs(a);
    b = dfs(b);
    if (a != b) {
        color[a] = b;
    }
}

int main() {
    ios_base::sync_with_stdio(NULL);
    cin.tie(NULL);
    cout.tie(NULL);
    int v, u, w;
    ll mega_cost = 0;
    cin >> n >> m;
    color.resize(n);
    for (int i = 0; i < m; i++) {
        cin >> v >> u >> w;
        v--, u--;
        g.push_back({w, {v, u}});
    }
    sort(g.begin(), g.end());
    for (int i = 0; i < n; i++) color[i] = i;
    for (int i = 0; i < m; i++) {
        int f = g[i].second.first, s = g[i].second.second;
        int cost = g[i].first;
        if (dfs(f) != dfs(s)) {
            mega_cost += g[i].first;
            vpn(f, s);
        }
    }
    cout << mega_cost << endl;
    return 0;
}
