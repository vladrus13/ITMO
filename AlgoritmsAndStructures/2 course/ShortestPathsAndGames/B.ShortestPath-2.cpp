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
    int32 u, v, x;
    g.resize(n);
    for (int32 i = 0; i < m; i++) {
        cin >> u >> v >> x;
        u--, v--;
        g[u].push_back(edge(v, x));
        g[v].push_back(edge(u, x));
    }
    vector <ll> dp(n, INT64_MAX);
    vector <bool> used;
    set <pair <ll, int> > queue;
    dp[0] = 0LL;
    queue.insert({0, 0});
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
    for (int i : dp) {
        cout << i << ' ';
    }
    return 0;
}
