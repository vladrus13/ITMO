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

const ll INF = INT64_MAX / 1000;

class Tube {
public:
    ll get_free() {
        return c - flow;
    }
    Tube(int32 to, ll c) : flow(0), to(to), c(c), index(0) {}

    Tube* reverse;
    ll flow, c;
    int32 to, index;
};

class Order {
public:
    int v, u;
    list <Tube>::iterator itform, itto;
};

int32 n, m, computers;
int32 start, finish;
vector <list <Tube> > g;

void add_small_edge(int32 v, int32 u, ll c) {
    g[v].push_back(Tube(u, c));
    g[u].push_back(Tube(v, 0));
    g[v].back().reverse = &g[u].back();
    g[u].back().reverse = &g[v].back();
}

void add_edge(int32 v, int32 u, ll c = 1) {
    if (max(v, u) >= g.size()) {
        g.resize(max(v, u) + 1);
    }
    add_small_edge(v, u, c);
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

ll dfs(int32 v, vector <list<Tube>::iterator> &block, vector <ll> &level, ll flow = INF) {
    if (v == finish) return flow;
    if (flow == 0) return 0;
    for (auto i = block[v]; i != g[v].end(); i++) {
        if (level[i->to] != level[v] + 1) continue;
        ll flow_get = dfs(i->to, block, level, min(i->get_free(), flow));
        if (flow_get) {
            i->flow += flow_get;
            i->reverse->flow -= flow_get;
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
            vector <list<Tube>::iterator> block(g.size());
            for (int i = 0; i < g.size(); i++) block[i] = g[i].begin();
            ld flow;
            flow = dfs(start, block, level, INF);
            while (flow) {
                result += flow;
                flow = dfs(start, block, level, INF);
            }
            gg = bfs(max_cap);
        }
        answer += result;
        max_cap >>= 1;
    }
    return answer;
}

int32 index(int32 day, int32 max_v, int32 v) {
    return day * max_v + v;
}

vector <pair <int32, int32> > graph;

void read() {
    cin >> n >> m >> computers;
    cin >> start >> finish;
    start--, finish--;
    graph.resize(m);
    for (int32 i = 0; i < m; i++) {
        cin >> graph[i].first >> graph[i].second;
        graph[i].first--, graph[i].second--;
    }
}

int32 solver() {
    int32 days = 0;
    int32 trans_comp = 0;
    int32 finisher = finish;
    for (days = 0; trans_comp < computers; days++) {
        for (int32 i = 0; i < n; i++) {
            add_edge(index(days, n, i), index(days + 1, n, i), INF);
        }
        for (int32 i = 0; i < m; i++) {
            add_edge(index(days, n, graph[i].first), index(days + 1, n, graph[i].second));
            add_edge(index(days, n, graph[i].second), index(days + 1, n, graph[i].first));
        }
        finish = index(days + 1, n, finisher);
        trans_comp += dinic();
    }
    return days;
}

int32 answer;

void solve() {
    answer = solver();
}

void write() {
    cout << answer << endl;
    vector <int32> temp(computers, start);
    for (int32 i = 0; i < answer; i++) {
        list <pair <int32, int32> > trans;
        for (int32 j = 0; j < computers; j++) {
            for (auto &e : g[temp[j]]) {
                if (e.flow >= 1) {
                    e.flow -= 1;
                    if (temp[j] + n != e.to) {
                        trans.emplace_back(j, e.to % n);
                    }
                    temp[j] = e.to;
                    break;
                }
            }
        }
        cout << trans.size() << "  ";
        for (auto it : trans) {
            cout << it.first + 1 << ' ' << it.second + 1 << "  ";
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
