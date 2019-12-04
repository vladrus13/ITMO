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
#include <random>

typedef long double ld;
typedef int64_t ll;
typedef uint64_t ull;
typedef int32_t int32;

using namespace std;

//////////////////////////////////////////////////////////////////
// SYSTEM STARTS

//////////////////////////////////////////////////////////////////
// UTILS

/////////////////////////////////////////////////////////////////
// MAIN

struct edge {
private:
    int32 to, weight;
public:
    edge(int32 to, int32 weight) : to(to), weight(weight) {}

    int32 getTo() const {
        return to;
    }

    int32 getWeight() const {
        return weight;
    }
};

vector <vector <edge> > g;
int32 n, m;
int32 new_m;
ll length;

void destabilase_relax(int32 &a, int32 b) {
    a = max(a, b);
}

void relax(int32 &a, int32 b) {
    a = min(a, b);
}

void read() {
    cin >> n >> m;
    int32 v, u, x;
    g.resize(n);
    for (int i = 0; i< m; i++) {
        cin >> u >> v >> x;
        v--, u--;
        g[u].push_back(edge(v, x));
        g[v].push_back(edge(u, x));
    }
    cin >> length;
}

void solve_YES() {
    cout << "Possible\n";
    exit(0);
}

void solve_NO() {
    cout << "Impossible\n";
    exit(0);
}

void ifer() {
    if (g.back().empty()) {
        if (length == 0) {
            solve_YES();
        } else {
            solve_NO();
        }
    }
}

void calculate() {
    new_m = g.back()[0].getWeight();
    for (edge it : g.back()) {
        destabilase_relax(new_m, it.getWeight());
    }
    new_m *= 4;
    int32 max_edge = 0;
    for (const auto& it : g) {
        for (auto jt : it) {
            destabilase_relax(max_edge, jt.getWeight());
        }
    }
    int32 divided = new_m;
    while (divided <= max_edge) {
        divided += max_edge;
    }
    new_m = divided;
}

vector <vector <ll> > used;

void deik() {
    used.resize(n, vector <ll> (new_m, 1e9));
    queue <pair <int32, int32> > queue;
    queue.push({0, 0});
    used[0][0] = 0;
    while (!queue.empty()) {
        pair <int32, int32> current = queue.front();
        queue.pop();
        for (edge it : g[current.first]) {
            int32 f = it.getTo(), s = (current.second + it.getWeight()) % new_m;
            int deter = 0;
            if (current.second + it.getWeight() >= new_m) {
                deter = 1;
            }
            if (used[f][s] > used[current.first][current.second] + deter) {
                used[f][s] = used[current.first][current.second] + deter;
                queue.push({f, s});
            }
        }
    }
    if (used[n - 1][length % new_m] != 1e9) {
        if (used[n - 1][length % new_m] * new_m + (length % new_m) <= length) solve_YES();
        else solve_NO();
    } else {
        solve_NO();
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
    ifer();
    calculate();
    deik();
    return 0;
}
