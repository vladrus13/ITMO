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
vector<string> list_of_names;
map<string, int> name_to_v;
vector<vector<int>> g, gt;
vector<int> answer, colors, tout;
int n, m;

void dfs(int v) {
    used[v] = true;
    for (int u : g[v]) {
        if (!used[u]) {
            dfs(u);
        }
    }
    tout.push_back(v);
}

void paint(int v, int color) {
    colors[v] = color;
    for (int u : gt[v]) {
        if (colors[u] == -1) {
            paint(u, color);
        }
    }
}

int main() {
    ios_base::sync_with_stdio(NULL);
    cin.tie(NULL);
    cout.tie(NULL);
    string v, u, temp;
    cin >> n >> m;
    g.resize(2 * n);
    gt.resize(2 * n);
    list_of_names.resize(n);
    used.resize(2 * n, false);
    colors.resize(2 * n, -1);
    for (int i = 0; i < n; i++) {
        cin >> list_of_names[i];
        name_to_v[list_of_names[i]] = i;
    }
    for (int i = 0; i < m; i++) {
        cin >> v >> temp >> u;
        int vv = 2 * name_to_v[v.substr(1)];
        int uu = 2 * name_to_v[u.substr(1)];
        if (v[0] == '-') vv++;
        if (u[0] == '-') uu++;
        g[vv].push_back(uu);
        gt[uu].push_back(vv);
        if (vv % 2 == 0) ++vv; else --vv;
        if (uu % 2 == 0) ++uu; else --uu;
        g[uu].push_back(vv);
        gt[vv].push_back(uu);
    }
    for (int i = 0; i < 2 * n; i++) {
        if (!used[i]) {
            dfs(i);
        }
    }
    reverse(tout.begin(), tout.end());
    int color = -1;
    for (int next : tout) {
        if (colors[next] == -1) {
            color++;
            paint(next, color);
        }
    }
    for (int i = 0; i < 2 * n; i += 2) {
        if (colors[i] == colors[i + 1]) {
            cout << -1 << endl;
            return 0;
        }
    }
    for (int i = 0; i < 2 * n; i += 2) {
        if (colors[i] > colors[i + 1]) {
            answer.push_back(i / 2);
        }
    }
    cout << answer.size() << endl;
    for (int cur : answer) {
        cout << list_of_names[cur] << endl;
    }
    return 0;
}
