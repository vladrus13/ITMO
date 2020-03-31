/// what about the useless text at the beginning
//                               of the program?
///                 I like to do useless things.
//       The program is made by vladrus13 (2020)

// Do you want to optimize?
#define _CRT_SECURE_NO_WARNINGS

#include <iostream>
#include <algorithm>
#include <vector>
#include <map>
#include <queue>
#include <list>

typedef int32_t int32;
typedef long double ld;
typedef long long ll;

using namespace std;

const ll INF = INT64_MAX / 1000;

class Tube {
public:
    ll get_free() {
        return c - flow;
    }
    Tube(int32 to, ll c, int32 index) : flow(0), to(to), c(c), index(index) {}

    ll flow, c;
    int32 to, index;
};

int32 n, m;
int32 start, finish;
vector <vector <Tube> > g;
list <pair <int32, int32> > order;

void add_small_edge(int32 v, int32 u, ll c) {
    g[v].push_back(Tube(u, c, g[u].size()));
    g[u].push_back(Tube(v, 0, g[v].size() - 1));
}

Tube& get_reverse(int32 v, int32 u) {
    return g[g[v][u].to][g[v][u].index];
}

void add_edge(int32 v, int32 u, ll c) {
    order.emplace_back(v, g[v].size());
    add_small_edge(v, u, c);
    add_small_edge(u, v, c);
}

vector <ll> level;

bool bfs(ll flow) {
    level.assign(g.size(), INF);
    level[start] = 0;
    queue <int32> q;
    q.push(start);
    while (!q.empty()) {
        int32 v = q.front();
        q.pop();
        for (auto& e : g[v]) {
            if (level[e.to] == INF && e.get_free() >= flow) {
                level[e.to] = level[v] + 1;
                q.push(e.to);
            }
        }
    }
    return level[finish] != INF;
}

ll dfs(int32 v, vector <int32> &block, vector <ll> &level, ll flow = INF) {
    if (v == finish) return flow;
    if (flow == 0) return 0;
    for (auto i = block[v]; i < g[v].size(); i++) {
        auto& it = g[v][i];
        if (level[it.to] != level[v] + 1) continue;
        ll flow_get = dfs(it.to, block, level, min(it.get_free(), flow));
        if (flow_get) {
            it.flow += flow_get;
            get_reverse(v, i).flow -= flow_get;
            return flow_get;
        }
        block[v]++;
    }
    return 0;
}

ld dinic() {
    ll max_cap = INF;
    ld answer = 0;
    while (max_cap) {
        double result = 0;
        bool gg = bfs(max_cap);
        while (gg) {
            vector <int32> block(g.size(), 0);
            ld flow;
            flow = dfs(start, block, level);
            while (flow) {
                result += flow;
                flow = dfs(start, block, level);
            }
            gg = bfs(max_cap);
        }
        answer += result;
        max_cap >>= 1;
    }
    return answer;
}

vector <bool> used;

void used_dfs(int32 v) {
    if (used[v]) return;
    used[v] = true;
    for (auto e : g[v]) {
        if (e.c != e.flow) {
            used_dfs(e.to);
        }
    }
}

ll answer;
list <int32> answer_list;

void min_cut() {
    dinic();
    used.resize(n, false);
    used_dfs(start);
    answer = 0;
    int32 index = -1;
    for (auto e : order) {
        index++;
        if (used[e.first] == used[g[e.first][e.second].to]) continue;
        ll flow = g[e.first][e.second].flow;
        if (flow == 0) {
            flow = g[e.first][e.second + 1].flow;
        }
        answer += abs(flow);
        answer_list.emplace_back(index);
    }
}

void read() {
    cin >> n >> m;
    g.resize(n);
    start = 0;
    finish = n - 1;
    ll v, u, c;
    for (int32 i = 0; i < m; i++) {
        cin >> v >> u >> c;
        add_edge(--v, --u, c);
    }
}

void solve() {
    min_cut();
}

void write() {
    cout << answer_list.size() << ' ' << answer << endl;
    for (int it : answer_list) cout << it + 1 << ' ';
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
