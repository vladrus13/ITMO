/// what about the useless text at the beginning
//                               of the program?
///                 I like to do useless things.
//       The program is made by vladrus13 (2020)

// Do you want to optimize?
#include <iostream>
#include <algorithm>
#include <utility>
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
#include <unordered_map>
#include <fstream>
#include <list>

#define _CRT_SECURE_NO_WARNINGS

using namespace std;

typedef int32_t int32;
typedef long double ld;
typedef long long ll;

const ll MOD = 1e9 + 7;

ll m, k;
vector <ll> counter, sum, answer;

void read() {
    cin >> k >> m;
    counter.resize(k);
    for (int i = 0; i < k; i++) cin >> counter[i];
    sum.resize(m + 1);
    answer.resize(m + 1);
}

void solve() {
    answer[0] = 1;
    sum[0] = 1;
    for (int i = 1; i <= m; i++) {
        for (int j = 0; j < k; j++) {
            if (i >= counter[j]) {
                answer[i] += sum[i - counter[j]];
                answer[i] %= MOD;
            }
        }
        for (int j = 0; j <= i; j++) {
            sum[i] += (answer[j] * answer[i - j]) % MOD;
            sum[i] %= MOD;
        }
    }

    for (int i = 1; i <= m; i++) {
        cout << answer[i] << ' ';
    }
}

void write() {

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