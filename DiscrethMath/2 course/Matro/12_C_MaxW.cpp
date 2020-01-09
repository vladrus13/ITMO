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
#include <unordered_map>
#include <fstream>
#include <list>

typedef long double ld;
typedef int64_t ll;
typedef uint64_t ull;
typedef int32_t int32;

using namespace std;

void re() {
#ifdef _DEBUG
    system("pause");
#endif
    exit(0);
}

#define re re();

//////////////////////////////////////////////////////////////////
// SYSTEM STARTS

//////////////////////////////////////////////////////////////////
// UTILS

/////////////////////////////////////////////////////////////////
// MAIN

int32 n;
vector <bool> used;
vector <int32> l, r;
vector <vector <int32> > g;
vector <pair <int32, int32> > values;

void fill() {
    g.resize(n);
    r.resize(n, -1);
    l.resize(n, -1);
}

bool dfs(int32 v) {
    if (used[v]) return false;
    used[v] = true;
    for (int32 u : g[v]) {
        if (l[u] == -1 || dfs(l[u])) {
            l[u] = v;
            return true;
        }
    }
    return false;
}

int main() {
    // begin of my useless prog
#ifdef MY_DEBUG
    freopen("input.txt", "r", stdin);
    freopen("output.txt", "w", stdout);
#else
    freopen("matching.in", "r", stdin);
    freopen("matching.out", "w", stdout);
#endif
    ios_base::sync_with_stdio(NULL);
    cin.tie(NULL);
    cout.tie(NULL);
    cin >> n;
    fill();
    // are you need Kunh?)
    int32 value, value2;
    for (int32 i = 0; i < n; i++) {
        cin >> value;
        values.emplace_back(value, i);
    }
    for (int32 i = 0; i < n; i++) {
        cin >> value;
        for (int32 j = 0; j < value; j++) {
            cin >> value2;
            g[i].push_back(value2 - 1);
            // g[value - 1].push_back(j);
        }
    }
    sort(values.rbegin(), values.rend());
    for (pair <int32, int32> v : values) {
        used.clear();
        used.resize(n, false);
        dfs(v.second);
    }
    for (int32 i = 0; i < n; i++) {
        if (l[i] != -1) r[l[i]] = i;
    }
    for (int32 i = 0; i < n; i++) {
        cout << r[i] + 1 << " ";
    }
    re
}