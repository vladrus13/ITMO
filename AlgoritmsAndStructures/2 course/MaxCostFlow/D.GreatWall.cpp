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

    ll flow, c;
    int32 to, index;
};

int32 n, m, len, hei;
int32 start, finish;
vector <vector <Tube> > g;
list <pair <int32, int32> > order;

void add_small_edge(int32 v, int32 u, ll c) {
    g[v].push_back(Tube(u, c, g[u].size()));
    g[u].push_back(Tube(v, 0, g[v].size() - 1));
}

bool orien = true;

void add_edge(int32 v, int32 u, ll c) {
    order.emplace_back(v, g[v].size());
    add_small_edge(v, u, c);
    if (!orien) {
        add_small_edge(u, v, c);
    }
}

Tube& get_r(int32 v, int32 u) {
    return g[g[v][u].to][g[v][u].index];
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

ll dfs(int32 v, vector <int32> &block, vector <ll> &level, ll flow) {
    if (v == finish) return flow;
    if (flow == 0) return 0;
    for (int32 i = block[v]; i < g[v].size(); i++) {
        Tube& e = g[v][i];
        if (level[e.to] != level[v] + 1) continue;
        ll flow_get = dfs(e.to, block, level, min(e.get_free(), flow));
        if (flow_get) {
            e.flow += flow_get;
            get_r(v, i).flow -= flow_get;
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
            vector <int32> block(n, 0);
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

vector <vector <char> > kind;

int32 index(int32 h, int32 l, int32 nn) {
    return nn * h + l;
}

int32 index2(int32 h, int32 l, int32 nn, int32 mm) {
    return index(h, l, mm) + nn * mm;
}

ll lINF = INF;

void read() {
    cin >> hei >> len;
    n = hei * len * 2;
    char c;
    kind.resize(hei * 2, vector <char> (len));
    g.resize(n);
    for (int i = 0; i < hei; i++) {
        for (int j = 0; j < len; j++) {
            cin >> c;
            switch (c) {
                case '.' :
                    add_edge(index2(i, j, hei, len), index(i, j, len), 1);
                    break;
                case '-':
                    add_edge(index2(i, j, hei, len), index(i, j, len), lINF);
                    break;
                case 'A':
                    start = index(i, j, len);
                    break;
                case 'B':
                    finish = index2(i, j, hei, len);
                    break;
                default:
                    break;
            }
            kind[i][j] = c;
        }
    }
    for (int32 i = 0; i < hei; i++) {
        for (int32 j = 0; j < len; j++) {
            if (kind[i][j] == '#') continue;
            int32 v = index(i, j, len);
            int32 u = index2(i, j, hei, len);
            if (j + 1 < len && kind[i][j + 1] != '#') {
                add_edge(v, index2(i, j + 1, hei, len), lINF);
                add_edge(index(i, j + 1, len), u, lINF);
            }
            if (i + 1 < hei && kind[i + 1][j] != '#') {
                add_edge(v, index2(i + 1, j, hei, len), lINF);
                add_edge(index(i + 1, j, len), u, lINF);
            }
        }
    }
}

list <pair <int32, int32> > result;
vector <bool> used;

void find_bfs(int32 v) {
    if (used[v]) return;
    used[v] = true;
    for (auto e : g[v]) {
        if (e.c != e.flow) {
            find_bfs(e.to);
        }
    }
}

ld answer;

void min_cut() {
    ld flow = dinic();
    used.resize(n, false);
    find_bfs(start);
    answer = 0;
    for (int32 i = 0; i < n; i++) {
        if (!used[i]) continue;
        for (auto e : g[i]) {
            if (!used[e.to] && e.flow == 1) {
                result.emplace_back(i, e.to);
                break;
            }
        }
    }
    answer = flow;
}

void solve() {
    min_cut();
}

void write() {
    if (answer >= lINF || answer < 0) {
        cout << "-1" << endl;
        return;
    }
    for (auto i : result) {
        kind[i.second / len][i.second % len] = '+';
    }
    cout << answer << endl;
    for (int32 i = 0; i < hei; i++) {
        for (int32 j = 0; j < len; j++) {
            cout << kind[i][j];
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
