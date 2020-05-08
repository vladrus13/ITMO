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

bool is_prime(ll x) {
    if (x == 2 || x == 3) return true;
    if (x == 1 || x % 2 == 0) return false;
    ll p = 0, q = x - 1;
    while (q % 2 == 0) {
        p++;
        q /= 2;
    }
    for (int i = 0; i < 10; i++) {
        ll a = (rand() % (x - 2)) + 2;
        ll s = power_up(a, q, x);
        if (s == 1 || s == x - 1) {
            continue;
        }
        for (int j = 1; j < p; j++) {
            s = multiply(s, s, x);
            if (s == 1) {
                return false;
            }
            if (s == x - 1) {
                break;
            }
        }
        if (s != x - 1) {
            return false;
        }
    }
    return true;
}

void read() {
    srand(NULL);
    int n;
    ll x;
    cin >> n;
    while (n--) {
        cin >> x;
        cout << (is_prime(x) ? "YES\n" : "NO\n");
    }
}

void solve() {

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
