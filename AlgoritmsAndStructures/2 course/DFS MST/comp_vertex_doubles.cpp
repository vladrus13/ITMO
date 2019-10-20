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

int current_color = 0, n, m;
vector <vector <pair <int, int> > > g;
vector <int> color, tin, tout, used;
int timer = 0;
void dfs(int vertex, int parent) {
    tout[vertex] = tin[vertex] = ++timer;
    used[vertex] = true;
    for (auto next : g[vertex]) {
        if (next.first == parent) {
            continue;
        }
        if (used[next.first]) {
            tout[vertex] = min(tout[vertex], tin[next.first]);
        } else {
            dfs(next.first, vertex);
            tout[vertex] = min(tout[vertex], tout[next.first]);
        }
    }
}



void paint(int v, int coloristic) {
    used[v] = true;
    for (pair <int, int> u : g[v]) {
        if (used[u.first]) {
            if (color[u.second] == 0) {
                color[u.second] = coloristic;
            }
        } else {
            if (tout[u.first] >= tin[v]) {
                color[u.second] = ++current_color;
                paint(u.first, current_color);
            } else {
                color[u.second] = coloristic;
                paint(u.first, coloristic);
            }
        }
    }
}

int main() {
    ios_base::sync_with_stdio(NULL);
    cin.tie(NULL);
    cout.tie(NULL);
    cin >> n >> m;
    used.resize(n, 0);
    color.resize(m, 0);
    tin.resize(n);
    tout.resize(n);
    g.resize(n);
    int v, u;
    for (int i = 0; i < m; ++i) {
        cin >> v >> u;
        v--, u--;
        g[v].emplace_back(u, i);
        g[u].emplace_back(v, i);
    }
    for (int i = 0; i < n; ++i) {
        if (!used[i]) {
            dfs(i, -1);
        }
    }
    used.clear();
    used.resize(n, 0);
    for (int i = 0; i < n; ++i) {
        if (!used[i]) {
            paint(i, 0);
        }
    }
    cout << current_color << endl;
    for (int i : color) {
        cout << i << " ";
    }
    return 0;
}
