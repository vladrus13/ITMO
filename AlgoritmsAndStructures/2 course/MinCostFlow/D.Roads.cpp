/// what about the useless text at the beginning
//                               of the program?
///                 I like to do useless things.
//       The program is made by vladrus13 (2020)

// Do you want to optimize?
#include <iostream>
#include <algorithm>
#include <utility>
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

#define _CRT_SECURE_NO_WARNINGS

using namespace std;

typedef int32_t int32;
typedef long double ld;
typedef long long ll;

const ll MOD = 1e18 + 7;

class edge {
public:
    int32 from, to;
    ll flow, cost, capacity;
    size_t reversal;

    edge(int32 from, int32 to, ll flow, ll capacity, ll cost)
            : from(from), to(to), flow(flow), capacity(capacity), cost(cost)
    {}
};

int32 n, m;
int32 s, t;
ll max_flow = 0, min_cost = 0;
vector <vector <edge> > g;
vector <ll> d, id;
vector <edge*> path;
vector <ll> a;

void add_edge(int v, int u, ll c, ll cc) {
    g[v].emplace_back(v, u, 0, c, cc);
    g[u].emplace_back(u, v, 0, 0, -cc);
    g[v].back().reversal = g[u].size() - 1;
    g[u].back().reversal = g[v].size() - 1;
}

void read() {
    cin >> n >> m;
    s = 0, t = 2 * n + 1;
    g.resize(n * 2 + 2);
    path.resize(n + 1);
    a.resize(n + 1);
    for (int i = 1; i <= n; i++) {
        cin >> a[i];
        add_edge(n + i, i, MOD, a[i]);
        add_edge(0, n + i, 1, 0);
        add_edge(i, 2 * n + 1, 1, 0);
        add_edge(i, n + i, MOD, 0);
    }
    int v, u;
    ll ccc;
    for (int i = 0; i < m; i++) {
        cin >> v >> u >> ccc;
        add_edge(n + v, u, MOD, ccc);
    }
    n = 2 * n + 2;
}

void bfs() {
    deque<int> q;
    id.assign(n + 1, 0);
    d.assign(n + 1, MOD);
    path.assign(n + 1, nullptr);
    d[s] = 0;
    q.push_back(s);
    while (!q.empty()) {
        int i = q.front();
        q.pop_front();
        id[i] = 2;
        for (auto &edge : g[i]) {
            if (edge.flow < edge.capacity && d[edge.to] > d[edge.from] + edge.cost) {
                d[edge.to] = d[edge.from] + edge.cost;
                if (id[edge.to] == 0) {
                    q.push_back(edge.to);
                } else {
                    if (id[edge.to] == 2) {
                        q.push_front(edge.to);
                    }
                }
                path[edge.to] = &edge;
                id[edge.to] = 1;
            }
        }
    }
}

void solve() {
    while (true) {
        bfs();
        if (d[t] == MOD) {
            break;
        }
        ll de = MOD;
        for (int v = t; v != s; v = path[v]->from) {
            de = min(de, path[v]->capacity - path[v]->flow);
        }
        for (int v = t; v != s; v = path[v]->from) {
            path[v]->flow += de;
            g[path[v]->to][path[v]->reversal].flow -= de;
            min_cost += path[v]->cost * de;
        }
        max_flow += de;
    }
}

void write() {
    cout << min_cost << endl;
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
    read();
    solve();
    write();
    return 0;
}
