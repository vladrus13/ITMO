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

ll multiply(ll a, ll b, ll M) {
    ll answer = 0;
    while (b > 0) {
        if (b % 2 != 0) {
            answer = (answer + a) % M;
        }
        a = (a + a) % M;
        b /= 2;
    }
    return answer;
}

ll power_up(ll x, ll n, ll M) {
    ll res = 1;
    while (n > 0) {
        if (n % 2 == 1) {
            res = multiply(res, x, M);
        }
        x = multiply(x, x, M);
        n /= 2;
    }
    return res;
}

ll sgcd(ll f, ll s, ll &fm, ll &sm) {
    if (!f) {
        fm = 0;
        sm = 1;
        return s;
    }
    ll aa, bb;
    ll result = sgcd(s % f, f, aa, bb);
    fm = bb - (s / f) * aa;
    sm = aa;
    return result;
}

ll module_reversal(ll a, ll b) {
    ll aa, bb;
    sgcd(a, b, aa, bb);
    return (aa % b + b) % b;
}

pair <ll, ll> fact(ll n) {
    for (int i = 2; i <= n; i++) {
        if (!(n % i)) {
            return {i, n / i};
        }
    }
}

void read() {

}

void solve() {
    ll a, b, c;
    cin >> a >> b >> c;
    pair <ll, ll> pair = fact(a);
    cout << power_up(c, module_reversal(b, (pair.first - 1) * (pair.second - 1)), a);
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
