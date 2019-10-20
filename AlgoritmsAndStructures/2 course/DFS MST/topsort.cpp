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

vector <int> color;
vector <int> answer;

int n, m, v, u;

void dfs(int v) {
    color[v] = 1;
    for (int u : g[v]) {
        if (color[u] == 1) {
            cout << -1 << endl;
            exit(0);
        }
        if (color[u] == 0) dfs(u);
    }
    color[v] = 2;
    answer.push_back(v + 1);
}

void top_sort() {
    for (int i = 0; i < n; i++) if (!color[i]) dfs(i);
    reverse(answer.begin(), answer.end());
}

int main() {
    ios_base::sync_with_stdio(NULL);
    cin.tie(NULL);
    cout.tie(NULL);
    cin >> n >> m;
    g.resize(n);
    color.resize(n, 0);
    for (int i = 0; i < m; i++) {
        cin >> u >> v;
        v--, u--;
        g[u].push_back(v);
    }
    top_sort();
    for (int i : answer) {
        cout << i << ' ';
    }
    return 0;
}
