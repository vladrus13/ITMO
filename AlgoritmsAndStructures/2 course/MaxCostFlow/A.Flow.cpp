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
#include <iomanip>

typedef int32_t int32;
typedef long double ld;
typedef long long ll;

using namespace std;

class Tube {
public:
    int32 get_free() {
        return c - flow;
    }
    Tube(int32 to, int32 c, int32 index) : flow(0), to(to), c(c), index(index) {}

    int32 flow;
    int32 to, c, index;
};

int32 n, m;
int32 start, finish;
vector <vector <Tube> > g;
list <pair <int32, int32> > order;

void add_edge(int32 v, int32 u, int32 c) {
    order.emplace_back(v, g[v].size());
    g[v].push_back(Tube(u, c, g[u].size()));
    g[u].push_back(Tube(v, 0, g[v].size() - 1));
    g[u].push_back(Tube(v, c, g[v].size()));
    g[v].push_back(Tube(u, 0, g[u].size() - 1));
}

Tube& get_r(int32 v, int32 u) {
    return g[g[v][u].to][g[v][u].index];
}

pair <bool, vector <int32> > bfs(int32 flow) {
    vector <int32> level(n, 1e9);
    level[start] = 0;
    queue <int32> q;
    q.push(start);
    while (!q.empty()) {
        int32 v = q.front();
        q.pop();
        for (auto& e : g[v]) {
            if (level[e.to] == 1e9 && e.get_free() >= flow) {
                level[e.to] = level[v] + 1;
                q.push(e.to);
            }
        }
    }
    return {level[finish] != 1e9, move(level)};
}

int32 dfs(int32 v, vector <int32> &block, vector <int32> &level, int32 flow) {
    if (v == finish) return flow;
    if (flow == 0) return 0;
    for (int32 i = block[v]; i < g[v].size(); i++) {
        Tube& e = g[v][i];
        if (level[e.to] != level[v] + 1) continue;
        int32 flow_get = dfs(e.to, block, level, min(e.get_free(), flow));
        if (flow_get) {
            e.flow += flow_get;
            get_r(v, i).flow -= flow_get;
            return flow_get;
        }
        block[v]++;
    }
    return 0;
}

double dinic() {
    int32 max_cap = 1e9;
    double answer = 0;
    while(max_cap) {
        double result = 0;
        auto [gg, level] = bfs(max_cap);
        while (gg) {
            vector <int> block(n, 0);
            double flow;
            flow = dfs(start, block, level, 1e9);
            while (flow) {
                result += flow;
                flow = dfs(start, block, level, 1e9);
            }
            auto pr = bfs(max_cap);
            gg = pr.first;
            level = pr.second;
        }
        answer += result;
        max_cap >>= 1;
    }
    return answer;
}

void read() {
    cin >> n >> m;
    g.resize(n);
    start = 0;
    finish = n - 1;
    int32 v, u, c;
    for (int32 i = 0; i < m; i++) {
        cin >> v >> u >> c;
        add_edge(--v, --u, c);
    }
}

double answer;

void solve() {
    answer = dinic();
}

void write() {
    cout << setprecision(8) << fixed << answer << endl;
    for (auto num : order) {
        int32 flow = g[num.first][num.second].flow;
        if (flow == 0) {
            flow = g[num.first][num.second + 1].flow;
        }
        cout << flow << endl;
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
