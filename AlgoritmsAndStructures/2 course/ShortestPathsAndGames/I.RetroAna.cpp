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
vector <vector <int32> > g, g_r;
vector <int32> status, counter;
vector <bool> used;

void dfs(int v) {
    used[v] = true;
    for (auto i : g_r[v]) {
        if (!used[i]) {
            if (status[v] == -1) {
                status[i] = 1;
            } else {
                if (--counter[i] == 0) {
                    status[i] = -1;
                } else {
                    continue;
                }
            }
            dfs(i);
        }
    }
}

void write() {
    for (int i = 0; i < n; i++) {
        if (status[i] == -1) cout << "SECOND";
        if (status[i] == 0) cout << "DRAW";
        if (status[i] == 1) cout << "FIRST";
        cout << endl;
    }
    cout << endl;
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
    while (cin >> n >> m) {
        g.clear();
        g.resize(n);
        status.clear();
        status.resize(n, 0);
        g_r.clear();
        g_r.resize(n);
        counter.clear();
        counter.resize(n, 0);
        used.clear();
        used.resize(n, false);
        int v, u;
        for (int i = 0; i < m; i++) {
            cin >> v >> u;
            v--, u--;
            g[v].push_back(u);
            g_r[u].push_back(v);
        }
        vector <int> lists;
        for (int i = 0; i < n; i++) {
            counter[i] = g[i].size();
            if (counter[i] == 0) {
                status[i] = -1;
                lists.push_back(i);
            }
        }
        for (int i : lists) {
            dfs(i);
        }
        write();
    }
    return 0;
}
