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

const ll MOD = 104857601;

class Polynomial {
    vector <ll> a;

public:

    explicit Polynomial(vector <ll> a) {
        this->a = std::move(a);
    }

    Polynomial() {
        a = vector<ll>(0);
    }

    [[nodiscard]] ll get(int pos) const {
        if (pos >= a.size()) return 0;
        return a[pos];
    }

    friend Polynomial operator+(const Polynomial& a, const Polynomial& b) {
        vector <ll> result(max(a.size(), b.size()), 0);
        for (int i = 0; i < a.size(); i++) result[i] += a.get(i);
        for (int i = 0; i < b.size(); i++) result[i] += b.get(i);
        for (ll & x : result) x = (x + MOD) % MOD;
        return Polynomial(result);
    }

    friend Polynomial operator*(const Polynomial& a, const Polynomial& b) {
        vector <ll> result(a.size() * b.size() + 3, 0);
        for (int i = 0; i < a.size(); i++) {
            for (int j = 0; j < b.size(); j++) {
                result[i + j] += a.get(i) * b.get(j);
                result[i + j] += MOD;
                result[i + j] %= MOD;
            }
        }
        return Polynomial(result);
    }

    friend Polynomial operator*=(Polynomial& a, const Polynomial& b) {
        a = a * b;
        return a;
    }

    [[nodiscard]] size_t size() const {
        return a.size();
    }

    void resize(size_t size) {
        a.resize(size);
    }

    void set(int pos, ll x) {
        a[pos] = x;
    }

    void change(int pos, ll x) {
        a[pos] += x;
        a[pos] %= MOD;
        if (a[pos] < 0) {
            a[pos] += MOD;
        }
    }
};

ll n, k;
Polynomial p, q;

void read() {
    cin >> k >> n;
    n--;
    vector <ll> temp_vector_ll(k * 2);
    for (int i = 0; i < k; i++) cin >> temp_vector_ll[i];
    p = *new Polynomial(temp_vector_ll);
    q = *new Polynomial();
    q.resize(k + 1);
    q.set(0, 1);
    ll x;
    for (int i = 1; i <= k; i++) {
        cin >> x;
        q.set(i, (-x + MOD) % MOD);
    }
}


void solve() {
    while (n > k - 1) {
        for (int i = k; i < 2 * k; i++) {
            p.set(i, 0);
            for (int j = 1; j <= k; j++) {
                p.change(i, -((q.get(j) * p.get(i - j)) % MOD));
            }
        }
        Polynomial power;
        power.resize(k + 1);
        for (int i = 0; i <= k; i++) {
            power.set(i, (i % 2 == 0 ? q.get(i) : ((-q.get(i)) + MOD) % MOD));
        }
        Polynomial temp = q * power;
        for (int i = 0; i <= k; i++) {
            q.set(i, temp.get(i * 2));
        }
        for (int i = n % 2; i < 2 * k; i += 2) {
            p.set(i / 2, p.get(i));
        }
        n /= 2;
    }
}

void write() {
    cout << p.get(n);
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