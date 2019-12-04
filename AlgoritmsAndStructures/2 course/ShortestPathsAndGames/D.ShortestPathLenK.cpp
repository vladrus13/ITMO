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
int n, m;

void relax(int32 &a, int32 b) {
    a = min(a, b);
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
    int32 k, s, v, u, x;
    cin >> n >> m >> k >> s;
    s--;
    vector <vector <int32> > dp;
    g.resize(n);
    dp.resize(k + 1, vector <int32> (n, INT32_MAX - 1e8));
    for (int32 i = 0; i < m; i++) {
        cin >> v >> u >> x;
        v--, u--;
        g[v].push_back(edge(u, x));
    }
    dp[0][s] = 0;
    for (int32 i = 0; i < k; i++) {
        for (int32 j = 0; j < n; j++) {
            for (edge it : g[j]) {
                if (dp[i][j] != INT32_MAX - 1e8) relax(dp[i + 1][it.getTo()], dp[i][j] + it.getWeight());
            }
        }
    }
    for (int32 i : dp[k]) {
        cout << (i == INT32_MAX - 1e8 ? -1 : i) << endl;
    }
    return 0;
}
