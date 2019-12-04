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

vector <vector <int32> > g;
int n;

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
    cin >> n;
    g.resize(n, vector <int32> (n, 0));
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            cin >> g[i][j];
            if (g[i][j]== 100'000) {
                g[i][j] = INT32_MAX - 10000;
            }
        }
    }
    vector <ll> dp(n, INT64_MAX - INT32_MAX);
    vector <int32> parent(n, -1);
    dp[0] = 0;
    for (int32 i = 0; i < n; i++) {
        for (int32 j = 0; j < n; j++){
            for (int32 k = 0; k < n; k++) {
                if (g[j][k] != INT32_MAX - 10000) {
                    if (dp[k] > dp[j] + g[j][k]) {
                        dp[k] = dp[j] + g[j][k];
                        parent[k] = j;
                    }
                }
            }
        }
    }
    vector <int32> answer;
    for (int32 i = 0; i < n; i++) {
        for (int32 j = 0; j < n; j++) {
            if (dp[j] > dp[i] + g[i][j] && g[i][j] != INT32_MAX - 10000) {
                cout << "YES\n";
                for (int k = 0; k < 2 * n; k++) {
                    j = parent[j];
                }
                i = j;
                while (i != parent[j]) {
                    answer.push_back(j);
                    j = parent[j];
                }
                answer.push_back(j);
                reverse(answer.begin(), answer.end());
                cout << answer.size() << endl;
                for (int h : answer) {
                    cout << h + 1 << ' ';
                }
                cout << endl;
                return 0;
            }
        }
    }
    cout << "NO\n";
    return 0;
}
