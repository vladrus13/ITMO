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

const ll MOD = 998244353;

ll n, k;

vector <vector <ll> > memoC;

void read() {
    cin >> k >> n;
    memoC.resize(k + 1, vector <ll> (k + 1));
}

void builtC() {
    for (int i = 0; i <= k; i++) {
        memoC[i][0] = 1;
        memoC[i][i] = 1;
        for (int j = 1; j < i; j++) {
            memoC[i][j] = (memoC[i - 1][j - 1] + memoC[i - 1][j]) % MOD;
            if (memoC[i][j] < 0) {
                memoC[i][j] += MOD;
            }
        }
    }
}

ll C(ll n, ll k) {
    return memoC[n][k];
}

ll get(ll pos, vector <ll> &a) {
    if (pos >= a.size()) return 0;
    return a[pos];
}

vector <ll> reversal(vector <ll> a) {
    vector <ll> returned(n + 1);
    returned[0] = (1 / get(0, a));
    for (int i = 0; i <= n; i++) {
        for (int j = 0; j < i; j++) {
            returned[i] -= (returned[j] * get(i - j, a)) % MOD;
            returned[i] %= MOD;
            if (returned[i] < 0) {
                returned[i] += MOD;
            }
        }
        returned[i] /= get(0, a);
    }
    return returned;
}

void solve() {
    builtC();
    vector <ll> left, right, not_right;
    for (int i = 0; k - 2 >= 2 * i; i++) {
        left.push_back(C(k - i - 2, i) * (i % 2 == 0 ? 1 : -1));
    }
    for (int i = 0; k - i - 1 >= i; i++) {
        right.push_back(C(k - i - 1, i) * (i % 2 == 0 ? 1 : -1));
    }
    not_right = reversal(right);
    for (int i = 0; i < n; i++) {
        int answer = 0;
        for (int j = 0; j <= i; j++) {
            answer += (get(j, left) * get(i - j, not_right)) % MOD;
            answer %= MOD;
            if (answer < 0) {
                answer += MOD;
            }
        }
        cout << answer << endl;
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