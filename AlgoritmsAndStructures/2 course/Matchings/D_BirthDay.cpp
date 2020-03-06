/// what about the useless text at the beginning
//                               of the program?
///                 I like to do useless things.
//       The program is made by vladrus13 (2020)

// Do you want to optimize?
#define _CRT_SECURE_NO_WARNINGS

#include <iostream>
#include <algorithm>
#include <vector>
#include <string>
#include <iomanip>
#include <list>
#include <math.h>
#include <set>

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
vector <set <int32> > g1, g2;

vector <int32> ans1, ans2;
vector <bool> used;

void read() {
    cin >> n >> m;
    g1.resize(n);
    g2.resize(m);
    ans1.resize(n, -1);
    ans2.resize(m, -1);
    used.resize(n, false);
    for (int32 i = 0; i < n; i++) {
        for (int32 j = 0; j < m; j++) {
            g1[i].insert(j);
            g2[j].insert(i);
        }
    }
    int32 x;
    for (int32 i = 0; i < n; i++) {
        cin >> x;
        x--;
        while (x != -1) {
            g1[i].erase(x);
            g2[x].erase(i);
            cin >> x;
            x--;
        }
    }
}

// kun? no) "i like Ford"
bool dfs(int32 x) {
    if (used[x]) return false;
    used[x] = true;
    for (int32 y : g1[x]) {
        if (ans2[y] == -1) {
            ans1[x] = y;
            ans2[y] = x;
            return true;
        } else {
            if (dfs(ans2[y])) {
                ans1[x] = y;
                ans2[y] = x;
                return true;
            }
        }
    }
    return false;
}

void Ford() {
    bool isPath = true;
    while (isPath) {
        isPath = false;
        used.assign(n, false);
        for (int i = 0; i < n; i++) {
            if (ans1[i] == -1) {
                if (dfs(i)) {
                    isPath = true;
                }
            }
        }
    }
}

vector <bool> visitn, visitm;

void mark_dfs(int v, bool side) {
    if (side) {
        visitn[v] = true;
        for (int32 u : g1[v]) {
            if (ans1[v] != u) {
                if (!visitm[u]) {
                    mark_dfs(u, false);
                }
            }
        }
    } else {
        visitm[v] = true;
        for (int32 u : g2[v]) {
            if (ans2[v] == u) {
                if (!visitn[u]) {
                    mark_dfs(u, true);
                }
            }
        }
    }
}

void solve() {
    int q;
    cin >> q;
    while (q--) {
        read();
        Ford();
        visitn.resize(n);
        visitm.resize(m);
        for (int32 i = 0; i < n; i++) {
            if (ans1[i] == -1) {
                mark_dfs(i, true);
            }
        }
        vector <int32> answer1, answer2;
        for (int32 i = 0; i < n; i++) {
            if (visitn[i]) answer1.push_back(i);
        }
        for (int32 i = 0; i < m; i++) {
            if (!visitm[i]) answer2.push_back(i);
        }
        cout << answer1.size() + answer2.size() << endl << answer1.size() << ' ' << answer2.size() << endl;
        for (int32 i : answer1) cout << i + 1 << ' ';
        cout << endl;
        for (int32 i : answer2) cout << i + 1 << ' ';
        cout << endl;
        visitm.clear();
        visitn.clear();
        g1.clear();
        g2.clear();
        ans1.clear();
        ans2.clear();
        used.clear();
    }
}

int main() {
    // begin of my useless prog
#ifdef MY_DEBUG
    freopen("input.txt", "r", stdin);
    freopen("output.txt", "w", stdout);
#else
    freopen("birthday.in", "r", stdin);
    freopen("birthday.out", "w", stdout);
#endif
    ios_base::sync_with_stdio(NULL);
    cin.tie(NULL);
    cout.tie(NULL);
    solve();
    return 0;
}
