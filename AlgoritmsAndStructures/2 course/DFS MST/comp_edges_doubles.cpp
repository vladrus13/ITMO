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
vector<vector<pair <int, int> >> e;
vector<int> tout;
vector<int> tin;
vector<bool> marked;
vector<int> colors;

int l = 0;
int current_color = 0;

void dfs(int v, int p) {
    used[v] = true;
    tin[v] = l++;
    tout[v] = tin[v];
    for (auto u : e[v]) {
        if (!used[u.first]) {
            dfs(u.first, u.second);
        }
        if (u.second != p) {
            tout[v] = min(tout[v], tout[u.first]);
        }
    }
    if (tout[v] == tin[v] && p != -1) {
        marked[p] = true;
    }
}

void dfs2(int v, int c) {
    colors[v] = c;
    for (pair<int, int> u : e[v]) {
        if (!colors[u.first]) {
            if (marked[u.second]) {
                dfs2(u.first, ++current_color);
            } else {
                dfs2(u.first, c);
            }
        }
    }

}

int main() {
    ios_base::sync_with_stdio(NULL);
    cin.tie(NULL);
    cout.tie(NULL);

    int n, m;
    cin >> n >> m;
    e.resize(n);
    used.resize(n);
    tin.resize(n, 100000000);
    tout.resize(n, 100000000);
    marked.resize(m);
    int v, u;
    for (int i = 0; i < m; ++i) {
        cin >> v >> u;
        --v, --u;
        e[v].push_back({u, i});
        e[u].push_back({v, i});
    }
    for (int i = 0; i < n; ++i) {
        if (!used[i]) {
            dfs(i, -1);
        }
    }
    colors.resize(n);
    for (int i = 0; i < n; ++i) {
        if (!colors[i]) {
            dfs2(i, ++current_color);
        }
    }
    map<int, int> answer;
    int cnt = 0;
    cout << current_color << "\n";
    for (auto i : colors) {
        if (answer[i] == 0) {
            answer[i] = ++cnt;
        }
        cout << answer[i] << " ";
    }
    return 0;
}
