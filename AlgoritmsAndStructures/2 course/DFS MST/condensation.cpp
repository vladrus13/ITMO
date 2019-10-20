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

vector<bool> used;
vector<vector<int>> g;
vector<vector<int>> edges;
vector<pair<int, int>> marked;
vector<int> color;
int timer = 0;
int current_color = 0;

void dfs1(int v) {
    used[v] = true;
    for (int u : g[v]) {
        if (!used[u]) {
            dfs1(u);
        }
    }
    marked.emplace_back(timer++, v);
}

void dfs2(int v) {
    color[v] = current_color;
    for (int u : edges[v]) {
        if (!color[u]) {
            dfs2(u);
        }
    }
}

int main() {
    ios_base::sync_with_stdio(NULL);
    cin.tie(NULL);
    cout.tie(NULL);
    int n, m;
    cin >> n >> m;
    g.resize(n);
    edges.resize(n);
    used.resize(n);
    color.resize(n);
    int a, b;
    for (int i = 0; i < m; i++) {
        cin >> a >> b;
        --a, --b;
        g[a].push_back(b);
        edges[b].push_back(a);
    }
    for (int i = 0; i < n; i++) {
        if (!used[i]) {
            dfs1(i);
        }
    }
    sort(marked.begin(), marked.end());
    for (int i = marked.size() - 1; i >= 0; i--) {
        if (!color[marked[i].second]) {
            ++current_color;
            dfs2(marked[i].second);
        }
    }
    set <pair <int, int> > answer;
    for (int v = 0; v < n; v++) {
        for (int u : g[v]) {
            if (color[v] != color[u]) answer.insert({min(color[v], color[u]), max(color[u], color[v])});
        }
    }
    cout << answer.size() << endl;
    return 0;
}
