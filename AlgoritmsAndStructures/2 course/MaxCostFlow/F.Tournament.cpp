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

int32 n;
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

vector <vector <char> > graph;
int32 t;

void read() {
    cin >> t;
    n = t;
    g.resize(n + 2);
    graph.resize(n, vector <char> (n));
    vector <int> used_at_this_round(n, 0), used(n, 0);
    start = t;
    finish = t + 1;
    for (int32 i = 0; i < t; i++) {
        for (int32 j = 0; j < t; j++) {
            cin >> graph[i][j];
            switch (graph[i][j]) {
                case '.' :
                    if (i < j) {
                        add_edge(i, j, 3);
                        used[i] += 3;
                    }
                    break;
                case 'W':
                    used_at_this_round[i] -= 3;
                    break;
                case 'w':
                    used_at_this_round[i] -= 2;
                    break;
                case 'l':
                    used_at_this_round[i] -= 1;
                default:
                    break;
            }
        }
    }
    int temp;
    for (int i = 0; i < t; i++) {
        cin >> temp;
        used_at_this_round[i] += temp;
        add_edge(i, t + 1, used_at_this_round[i]);
        add_edge(t, i, used[i]);
    }
}

void solve() {
    dinic();
    for (int i = 0; i < g.size() - 2; i++) {
        for (auto e : g[i]) {
            if (graph[i][e.to] == '.' && e.to != finish && e.to != start && i < e.to) {
                switch (e.flow) {
                    case 0:
                        graph[i][e.to] = 'W';
                        graph[e.to][i] = 'L';
                        break;
                    case 1:
                        graph[i][e.to] = 'w';
                        graph[e.to][i] = 'l';
                        break;
                    case 2:
                        graph[i][e.to] = 'l';
                        graph[e.to][i] = 'w';
                        break;
                    case 3:
                        graph[i][e.to] = 'L';
                        graph[e.to][i] = 'W';
                        break;
                    default:
                        break;
                }
            }
        }
    }
}

void write() {
    for (int i = 0; i < t; i++) {
        for (int j = 0; j < t; j++) {
            cout << graph[i][j];
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
