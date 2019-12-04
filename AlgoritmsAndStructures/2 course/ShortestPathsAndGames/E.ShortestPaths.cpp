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
vector <bool> marked;

void relax(ll &a, ll b) {
    a = min(a, b);
}

void dfs(int32 v) {
    marked[v] = true;
    for (edge j : g[v]) {
        if (!marked[j.getTo()]) {
            dfs(j.getTo());
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
    g.resize(n);
    ll s, v, u, x;
    cin >> n >> m >> s;
    s--;
    vector <ll> dp;
    g.resize(n);
    dp.resize(n, INT64_MAX);
    for (int32 i = 0; i < m; i++) {
        cin >> v >> u >> x;
        v--, u--;
        g[v].push_back(edge(u, x));
    }
    dp[s] = 0;
    for (int32 i = 0; i < n - 1; i++) {
        for (int32 j = 0; j < n; j++) {
            if (dp[j] != INT64_MAX) {
                for (edge k : g[j]) {
                    if (dp[k.getTo()] > dp[j] + k.getWeight()) {
                        dp[k.getTo()] = dp[j] + k.getWeight();
                    }
                }
            }
        }
    }
    vector <ll> new_dp = dp;
    marked.resize(n, false);
    for (int32 i = 0; i < n; i++) {
        for (int32 j = 0; j < n; j++) {
            if (new_dp[j] != INT64_MAX) {
                for (edge k : g[j]) {
                    if (new_dp[k.getTo()] > new_dp[j] + k.getWeight()) {
                        new_dp[k.getTo()] = new_dp[j] + k.getWeight();
                    }
                }
            }
        }
    }
    for (int32 i = 0; i < n; i++) {
        if (dp[i] != new_dp[i]) {dfs(i);}
    }
    for (int32 i = 0; i < n; i++) {
        if (dp[i] == INT64_MAX) {
            cout << "*\n";
        } else {
            if (marked[i]) {
                cout << "-\n";
            } else {
                cout << dp[i] << endl;
            }
        }
    }
    return 0;
}
