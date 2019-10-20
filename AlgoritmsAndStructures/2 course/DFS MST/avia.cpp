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

int n;
vector<vector<int>> m;
vector<bool> used;
vector<int> color;
vector<pair<int, int>> remarked;
int timer = 0;
int current_color = 0;

void dfs1(int v, int k) {
    used[v] = true;
    for (int i = 0; i < n; ++i) {
        if (m[v][i] <= k && !used[i]) {
            dfs1(i, k);
        }
    }
    remarked.emplace_back(timer++, v);
}

void dfs2(int v, int k) {
    color[v] = current_color;
    for (int i = 0; i < n; ++i) {
        if (m[i][v] <= k && !color[i]) {
            dfs2(i, k);
        }
    }
}

bool check(int cnt) {
    timer = 0;
    current_color = 0;
    used.assign(n, false);
    remarked.resize(0);
    for (int i = 0; i < n; ++i) {
        if (!used[i]) {
            dfs1(i, cnt);
        }
    }
    sort(remarked.begin(), remarked.end());
    color.assign(n, 0);
    for (int i = remarked.size() - 1; i >= 0; i--) {
        if (!color[remarked[i].second]) {
            current_color++;
            if (current_color > 1) {
                return false;
            }
            dfs2(remarked[i].second, cnt);
        }
    }

    return true;
}

int main() {
    ios_base::sync_with_stdio(NULL);
    cin.tie(NULL);
    cout.tie(NULL);
    freopen("avia.in", "r", stdin);
    freopen("avia.out", "w", stdout);
    cin >> n;
    m.resize(n, vector<int>(n, 0));
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            cin >> m[i][j];
        }
    }
    int l = -1, r = 1e9, m;
    while (r - l > 1) {
        m = (l + r) / 2;
        if (check(m)) {
            r = m;
        } else {
            l = m;
        }
    }
    cout << r;
    return 0;
}
