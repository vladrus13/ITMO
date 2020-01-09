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

class edge {
public:
    edge(int32 v, int32 u, int32 color, int32 number)
        :v(v),
        u(u),
        color(color),
        number(number)
    {}

    int32 v, u;
    int32 color;
    int32 number;

    friend bool operator<(edge const & a, edge const & b) {
        return a.number < b.number;
    }
};

int32 n, m;
vector <edge> g;

void read() {
    int v, u, c;
    cin >> n >> m;
    for (int32 i = 0; i < m; i++) {
        cin >> v >> u >> c;
        v--, u--;
        g.emplace_back(v, u, c, i + 1);
    }
}

vector <bool> ready;
vector <int32> used_color;
vector <set <edge> > g_replace;
set <int32> x1, x2;

void kill_edge(int32 v) {
    g_replace[g[v].v].erase(g[v]);
    g_replace[g[v].u].erase(g[v]);
    used_color[g[v].color]--;
    ready[v] = false;
}

void recover_edge(int32 v) {
    g_replace[g[v].v].insert(g[v]);
    g_replace[g[v].u].insert(g[v]);
    used_color[g[v].color]++;
    ready[v] = true;
}

vector <int32> used;

bool dfs(int32 v, int32 parent) {
    used[v] = 1;
    for (edge edgeI : g_replace[v]) {
        int32 u;
        if (edgeI.u == v) {
            u = edgeI.v;
        } else {
            u = edgeI.u;
        }
        if (used[u] == 1 && edgeI.number != parent) return true;
        if (used[u] == 0) {
            bool flag = dfs(u, edgeI.number);
            if (flag) return true;
        }
    }
    used[v] = 2;
    return false;
}

// post: 1 if cyclic, else 0
bool is_cyclic() {
    used.clear();
    used.resize(n, 0);
    for (int32 i = 0; i < n; i++) {
        if (used[i] == 0) {
            bool flag = dfs(i, -1);
            if (flag) return true;
        }
    }
    return false;
}

// pre: i, j in [0; m)
// post: 1, if after erasing i edge, and adding j edge - rainbow, 0 else
bool is_rainbow(int32 i, int32 j) { // i - deleted, j - inserted
    if (g[i].color == g[j].color && used_color[g[i].color] == 1) {
        return true;
    } else {
        return used_color[g[j].color] == 0;
    }
}

bool is_rainbow(int32 i) { // i - inserted
    return used_color[g[i].color] == 0;
}

void make_x1_x2() {
    x1.clear();
    x2.clear();
    for (int32 i = 0; i < ready.size(); i++) {
        if (!ready[i]) {
            recover_edge(i);
            if (!is_cyclic()) {
                x1.insert(i);
            }
            kill_edge(i);
            if (is_rainbow(i)) {
                x2.insert(i);
            }
        }
    }
}

vector <int32> path;

void bfs() {
    queue <int32> q; // numbers of vertexes (???)
    vector <int32> parent(m, -1);
    vector <bool> used_in_bfs(m, false);
    path.clear();
    for (int32 it : x1) {
        q.push(it);
        used_in_bfs[it] = true;
        if (x2.count(it)) {
            // if it found in x1 and x2. Just take it
            path.resize(1, it);
            return;
        }
    }
    bool flag = !q.empty();
    while (flag) {
        int32 v = q.front();
        q.pop();
        for (int32 u = 0; u < m && flag; u++) {
            if (!used_in_bfs[u]) { // is it not visited?
                bool can = false;
                // try to come
                if (ready[v] && !ready[u]) {
                    // v in J, u in J/S.
                    // edge from (J) to (J/S). Is it graph matroid?
                    kill_edge(v);
                    recover_edge(u);
                    if (!is_cyclic()) {
                        can = true;
                    }
                    recover_edge(v);
                    kill_edge(u);
                }
                if (ready[u] && !ready[v]) {
                    // v in J/S, u in J.
                    // edge from (J/S) to (S). Is it rainbow martoid?
                    if (is_rainbow(u, v)) {
                        can = true;
                    }
                }
                if (can) {
                    // edge accepted
                    if (x2.count(u)) {
                        // is it end!
                        flag = false;
                        int it = u;
                        parent[u] = v;
                        while (parent[it] != -1) {
                            path.push_back(it);
                            it = parent[it];
                        }
                        path.push_back(it);
                    } else {
                        // just put it to queue
                        q.push(u);
                        used_in_bfs[u] = true;
                        parent[u] = v;
                    }
                }
            }
        }
        if (q.empty()) flag = false;
    }
}

void singing() {
    for (int32 it : path) {
        if (ready[it]) {
            kill_edge(it);
        } else {
            recover_edge(it);
        }
    }
}

void edmondse() {
    ready.clear();
    ready.resize(m);
    used_color.resize(101, 0);
    g_replace.resize(n);
    bool flag = true;
    while (flag) {
        make_x1_x2();
        bfs();
        if (path.empty()) {
            flag = false;
        } else {
            singing();
        }
    }
}

void write() {
    int32 count = 0;
    for (int32 i = 0; i < ready.size(); i++) {
        if (ready[i]) count++;
    }
    cout << count << endl;
    for (int32 i = 0; i < ready.size(); i++) {
        if (ready[i]) cout << g[i].number << ' ';
    }
}

void solve() {
    read();
    edmondse();
    write();
}

int main() {
    // begin of my useless prog
#ifdef MY_DEBUG
    freopen("input.txt", "r", stdin);
    freopen("output.txt", "w", stdout);
#else
    freopen("rainbow.in", "r", stdin);
    freopen("rainbow.out", "w", stdout);
#endif
    ios_base::sync_with_stdio(NULL);
    cin.tie(NULL);
    cout.tie(NULL);
    solve();
    re
}
