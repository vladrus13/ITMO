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

vector <vector <int> > g;
vector <bool> used;

int ultra = 0;

vector <int> tin, tout;

int n, m, v, u;

set <int> answer;

void dfs(int v, int p) {
    used[v] = true;
    tin[v] = tout[v] = ultra++;
    int counter = 0;
    for (auto u : g[v]) {
        if (u != p) {
            if (used[u]) {
                tout[v] = min(tout[v], tin[u]);
            } else {
                dfs(u, v);
                tout[v] = min(tout[v], tout[u]);
                if (tout[u] >= tin[v] && p != -1) {
                    answer.insert(v);
                }
                counter++;
            }
        }
    }
    if (p == -1 && counter > 1) answer.insert(v);
}

int main() {
    ios_base::sync_with_stdio(NULL);
    cin.tie(NULL);
    cout.tie(NULL);\
    cin >> n >> m;
    g.resize(n);
    used.resize(n, false);
    tin.resize(n);
    tout.resize(n);
    for (int i = 0; i < m; i++) {
        cin >> u >> v;
        v--, u--;
        g[u].push_back(v);
        g[v].push_back(u);
    }
    for (int i = 0; i < n; i++) if (!used[i]) dfs(i, -1);
    cout << answer.size() << endl;
    for (int i : answer) cout << i + 1 << ' ';
    return 0;
}
