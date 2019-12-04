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

vector <vector <int32> > g;
vector <vector <int32> > dp;
int32 n;

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
    cin >> n;
    g.resize(n, vector <int32> (n, 0));
    dp.resize(n, vector <int32> (n, INT32_MAX));
    for (int32 i = 0; i < n; i++) {
        for (int32 j = 0; j < n; j++) {
            cin >> g[i][j];
            dp[i][j] = g[i][j];
        }
    }
    for (int32 i = 0; i < n; i++) dp[i][i] = 0;
    for (int32 k = 0; k < n; k++) {
        for (int32 i = 0; i < n; i++) {
            for (int32 j = 0; j < n; j++) {
                relax(dp[i][j], dp[i][k] + dp[k][j]);
            }
        }
    }
    for (vector <int32> it : dp) {
        for (int32 i : it) {
            cout << i << ' ';
        }
        cout << endl;
    }
    return 0;
}
