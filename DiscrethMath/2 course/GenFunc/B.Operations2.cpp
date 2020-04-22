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

ll get(int pos, vector <ll> & a) {
    if (pos >= a.size()) {
        return 0;
    }
    return a[pos];
}

ll powerUP(ll x, ll n) {
    if (n == 1) return x;
    if (n % 2 == 1) {
        return (x * powerUP(x, n - 1)) % MOD;
    } else {
        return (powerUP((x * x) % MOD, n / 2)) % MOD;
    }
}

ll reversal(ll a) {
    return powerUP(a, MOD - 2);
}

ll n, m;

vector <ll> multiply(const vector <ll> &a, const vector <ll> &b) {
    vector <ll> result(min(m, (ll) (a.size() * b.size() + 2)));
    for (int i = 0; i < a.size(); i++) {
        for (int j = 0; j < b.size(); j++) {
            if (i + j < result.size()) {
                result[i + j] += a[i] * b[j] % MOD;
                result[i + j] %= MOD;
            }
        }
    }
    return result;
}

ll C(ll n) {
    ll a = 1, b = 1;
    n--;
    for (int i = 0; i <= n; i++) {
        a *= 1 - 2 * i + MOD;
        b *= (2 * i + 2) % MOD;
        a %= MOD;
        b %= MOD;
    }

    return (a * reversal(b) + MOD) % MOD;
}

vector <ll> p, Asqrt, Aexp, Aln;

void read() {
    cin >> n >> m;
    p.resize(n + 1);
    Asqrt.resize(m, 0);
    Aexp.resize(m, 0);
    Aln.resize(m, 0);
    for (int i = 0; i <= n; i++) {
        cin >> p[i];
    }
}

void solve() {
    Asqrt[0] = 1;
    Aexp[0] = 1;
    ll fact = 1, Kln = MOD - 1;
    vector <ll> powerUpper = {1};
    for (int i = 1; i < m; i++) {
        powerUpper = multiply(powerUpper, p);
        ll kk = C(i);
        fact *= i;
        fact %= MOD;
        Kln *= -1;
        Kln += MOD;
        for (int j = 0; j < m; j++) {
           Asqrt[j] += (kk * get(j, powerUpper)) % MOD;
           Aexp[j] += ((reversal(fact)) % MOD * get(j, powerUpper)) % MOD;
           Aln[j] += ((Kln * reversal(i)) % MOD * get(j, powerUpper)) % MOD;
           Asqrt[j] %= MOD;
           Aexp[j] %= MOD;
           Aln[j] %= MOD;
        }
    }
}

void write() {
    for_each(Asqrt.begin(), Asqrt.end(), [](ll element){ cout << element << ' ';});
    cout << endl;
    for_each(Aexp.begin(), Aexp.end(), [](ll element){ cout << element << ' ';});
    cout << endl;
    for_each(Aln.begin(), Aln.end(), [](ll element){ cout << element << ' ';});
    cout << endl;
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