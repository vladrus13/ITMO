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
ll s;

class edge {
public:
    int32 v, u, index;
    ll value;
    edge(int32 v, int32 u, ll value, int32 index)
    : v(v),
    u(u),
    value(value),
    index(index)
    {}

    friend bool operator<(edge const & a, edge const & b) {
        return a.value < b.value;
    }
};

vector <int32> dsu, sizer;

void fill_dsu() {
    sizer.resize(n, 0);
    dsu.resize(n);
    for (int i = 0; i < n; i++) dsu[i] = i;
}

int32 get_(int32 v) {
    return (dsu[v] != v ? dsu[v] = get_(dsu[v]) : dsu[v]);
}

void union_(int32 v, int32 u) {
    if (get_(v) != get_(u)) {
        v = dsu[v];
        u = dsu[u];
        if (sizer[v] > sizer[u]) swap(v, u);
        if (sizer[v] == sizer[u]) sizer[u]++;
        dsu[v] = u;
    }
}

int main() {
    // begin of my useless prog
#ifdef MY_DEBUG
    freopen("input.txt", "r", stdin);
    freopen("output.txt", "w", stdout);
#else
    freopen("destroy.in", "r", stdin);
    freopen("destroy.out", "w", stdout);
#endif
    ios_base::sync_with_stdio(NULL);
    cin.tie(NULL);
    cout.tie(NULL);
    int32 m, u, v;
    ll w;
    cin >> n >> m >> s;
    fill_dsu();
    vector <pair <ll, int32> > deleted;
    vector <edge> edges;
    for (int32 i = 0; i < m; i++) {
        cin >> u >> v >> w;
        edges.emplace_back(u - 1, v - 1, w, i);
    }
    sort(edges.begin(), edges.end());
    for (int32 i = m - 1; i >= 0; i--) {
        if (get_(edges[i].v) != get_(edges[i].u)) {
            union_(edges[i].v, edges[i].u);
        } else {
            deleted.emplace_back(edges[i].value, edges[i].index);
        }
    }
    sort(deleted.begin(), deleted.end());
    ll answer = 0;
    int32 count = 0;
    for (count = 0; count < deleted.size(); count++) {
        answer += deleted[count].first;
        if (answer > s) break;
    }
    cout << count << endl;
    for (int32 i = 0; i < count; i++) {
        cout << deleted[i].second + 1 << ' ';
    }
    re
}