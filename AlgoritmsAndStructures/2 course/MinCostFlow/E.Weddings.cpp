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

const ll MOD = 1e12 + 7;

class edge {
public:
    int32 from, to;
    ll flow, cost, capacity;
    size_t reversal;
    ll num;

    edge(int32 from, int32 to, ll flow, ll capacity, ll cost, ll num)
            : from(from), to(to), flow(flow), capacity(capacity), cost(cost), num(num)
    {}
};

int32 n, m, k;
int32 s, t;
ll max_flow = 0, min_cost = 0;
vector <vector <edge> > g;
vector <ll> d, id;
vector <edge*> path;
vector <vector <ll> > answer;
ld answer_path = 0;

void add_edge(int v, int u, ll c, ll cc, ll num) {
    g[v].emplace_back(v, u, 0, c, cc, num);
    g[u].emplace_back(u, v, 0, 0, -cc, num);
    g[v].back().reversal = g[u].size() - 1;
    g[u].back().reversal = g[v].size() - 1;
}

void add_vert_edge(int v, int u, ll c, ll cc, ll num) {
    g[v].emplace_back(v, u, 0, c, cc, num);
    g[u].emplace_back(u, v, 0, c, -cc, num);
    g[v].back().reversal = g[u].size() - 1;
    g[u].back().reversal = g[v].size() - 1;
}

void read() {
    cin >> n >> m >> k;
    s = 1, t = n;
    g.resize(n + 1 + 2 * m);
    path.resize(n + 1);
    answer.resize(k);
    int v, u;
    ll ccc;
    for (int i = 0; i < m; i++) {
        cin >> v >> u >> ccc;
        int k = n + 1, l = n + 2;
        add_edge(v, k, 1, ccc, i + 1);
        add_edge(k, v, 1, 0, 0);
        add_vert_edge(k, l, 1, 0, 0);
        add_edge(u, l, 1, ccc, i + 1);
        add_edge(l, u, 1, 0, 0);
        n += 2;
    }
    n = g.size();
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
    int count = 0;
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
            answer_path += path[v]->cost;
        }
        max_flow += de;
        count++;
        if (count == k) {
            return;
        }
    }
    cout << -1 << endl;
    exit(0);
}

vector <bool> used;

bool dfs(int v, int counter) {
    used[v] = true;
    for (auto & edge : g[v]) {
        if (edge.flow == 1 && !used[edge.to]) {
            edge.flow = 0;
            if (edge.num != 0) answer[counter].push_back(edge.num);
            if (edge.to != t) {
                if (dfs(edge.to, counter)) return true;
                else edge.flow = 1;
            } else {
                return true;
            }
        }
    }
    return false;
}

void write() {
    for (int i = 0; i < k; i++) {
        used.assign(n + 1, false);
        dfs(1, i);
    }
    answer_path /= k;
    cout << setprecision(10) << fixed << answer_path << endl;
    for (const auto& ait : answer) {
        cout << ait.size() << ' ';
        for (auto bit : ait) {
            cout << bit << ' ';
        }
        cout << endl;
    }
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
