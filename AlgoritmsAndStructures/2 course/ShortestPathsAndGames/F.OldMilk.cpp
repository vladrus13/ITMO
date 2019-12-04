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
    ll to, weight;
public:
    edge(ll to, ll weight) : to(to), weight(weight) {}

    ll getTo() const {
        return to;
    }

    ll getWeight() const {
        return weight;
    }
};

vector <vector <edge> > g;
int32 n, m;

void relax(ll &a, ll b) {
    a = min(a, b);
}

// a b c -> c b a
// a c b -> b c a
// b a c -> c a b

void deikstra(vector <ll> &dp, int32 s) {
    dp.resize(n, INT64_MAX);
    vector <bool> used;
    set <pair <ll, int> > queue;
    dp[s] = 0LL;
    queue.insert({0, s});
    while (!queue.empty()) {
        pair <int, int> current = *(queue.begin());
        queue.erase(queue.begin());
        ll distance = current.first;
        int32 vertex = current.second;
        if (distance <= dp[vertex]) {
            for (edge i : g[vertex]) {
                if (dp[i.getTo()] > dp[vertex] + i.getWeight()) {
                    dp[i.getTo()] = dp[vertex] + i.getWeight();
                    queue.insert({dp[i.getTo()], i.getTo()});
                }
            }
        }
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
    cin >> n >> m;
    ll v, u, x;
    g.resize(n);
    for (int32 i = 0; i < m; i++) {
        cin >> v >> u >> x;
        v--, u--;
        g[v].push_back(edge(u, x));
        g[u].push_back(edge(v, x));
    }
    int32 a, b, c;
    cin >> a >> b >> c;
    a--, b--, c--;
    vector <ll> dp_from_a, dp_from_b;
    deikstra(dp_from_a, a);
    deikstra(dp_from_b, b);
    ll a_to_b = dp_from_a[b], a_to_c = dp_from_a[c];
    ll b_to_c = dp_from_b[c];
    if (a_to_b == INT64_MAX || a_to_c == INT64_MAX || b_to_c == INT64_MAX) {
        cout << "-1\n";
        return 0;
    }
    cout << min(a_to_b + a_to_c, min(a_to_b + b_to_c, a_to_c + b_to_c)) << endl;
    return 0;
}
