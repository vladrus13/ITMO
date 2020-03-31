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
    Tube(int32 to, ll c, int32 index) : flow(0), to(to), c(c), index(index) {}

    Tube* reverse;
    ll flow, c;
    int32 to, index;
};

class Order {
public:
    int v, u;
    list <Tube>::iterator itform, itto;
};

int32 n, m;
int32 start, finish;
vector <list <Tube> > g;
vector <Order> order;

void add_small_edge(int32 v, int32 u, ll c) {
    g[v].push_back(Tube(u, c, order.size()));
    g[u].push_back(Tube(v, 0, order.size()));
    g[v].back().reverse = &g[u].back();
    g[u].back().reverse = &g[v].back();
}

void add_edge(int32 v, int32 u, ll c = 1) {
    add_small_edge(v, u, c);
    order.push_back(Order{v, u, --g[v].end(), --g[u].end()});
}

void cut_edge(int it) {
    g[order[it].v].erase(order[it].itform);
    g[order[it].u].erase(order[it].itto);
}

void clear() {
    for (auto &i : g) {
        for (auto &j : i) {
            j.flow = 0;
        }
    }
}

pair <bool, vector <ll> > bfs(ll flow) {
    vector <ll> level(n, INF);
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
    return {level[finish] != INF, move(level)};
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
        auto [gg, level] = bfs(max_cap);
        while (gg) {
            vector <list<Tube>::iterator> block(n);
            for (int i = 0; i < n; i++) block[i] = g[i].begin();
            ld flow;
            flow = dfs(start, block, level, INF);
            while (flow) {
                result += flow;
                flow = dfs(start, block, level, INF);
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

bool founded, f_1, f_2;
list<int32> foundedL, f_1L, f_2L;

void erase_path() {
    if (dinic() == 0) {
        founded = false;
        return;
    }
    vector <int> used(m, false);
    foundedL.push_back(start);
    for (int v = start; v != finish; ) {
        int temp = v;
        for (auto e : g[v]) {
            if (e.flow > 0 && !used[e.index]) {
                v = e.to;
                used[e.index] = true;
                foundedL.push_back(v);
                cut_edge(e.index);
                break;
            }
        }
        if (v == temp) {
            founded = false;
            return;
        }
    }
    clear();
    founded = true;
}

void find_two() {
    erase_path();
    if (!founded) {
        f_1 = false;
        return;
    }
    f_1 = true;
    f_1L = move(foundedL);
    erase_path();
    if (!founded) {
        f_2 = false;
        return;
    }
    f_2 = true;
    f_2L = move(foundedL);
}

void read() {
    cin >> n >> m;
    cin >> start >> finish;
    g.resize(n);
    start--, finish--;
    int32 v, u;
    for (int32 i = 0; i < m; i++) {
        cin >> v >> u;
        add_edge(--v, --u);
    }
}

void solve() {

}

void write() {
    find_two();
    if (f_1 && f_2) {
        cout << "YES\n";
        for (auto it : f_1L) cout << it + 1 << ' ';
        cout << endl;
        for (auto it : f_2L) cout << it + 1 << ' ';
        cout << endl;
    } else {
        cout << "NO\n";
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
