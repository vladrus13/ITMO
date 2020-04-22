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

//const ll MOD = 998244353;

ll gcd(ll a, ll b) {
    return b ? gcd(b, a % b) : a;
}

class Polynomial {
    vector<ll> a;

public:

    explicit Polynomial(vector<ll> a) {
        this->a = move(a);
    }

    Polynomial() {
        a = vector<ll>(0);
    }

    ll get(ll pos) const {
        if (pos >= a.size()) return 0;
        return a[pos];
    }

    friend Polynomial operator+(const Polynomial &a, const Polynomial &b) {
        vector<ll> result(max(a.size(), b.size()), 0);
        for (ll i = 0; i < a.size(); i++) result[i] += a.get(i);
        for (ll i = 0; i < b.size(); i++) result[i] += b.get(i);
        return *new Polynomial(result);
    }

    friend Polynomial operator-(const Polynomial &a, const Polynomial &b) {
        vector<ll> result(max(a.size(), b.size()), 0);
        for (ll i = 0; i < a.size(); i++) result[i] += a.get(i);
        for (ll i = 0; i < b.size(); i++) result[i] -= b.get(i);
        return *new Polynomial(result);
    }

    friend Polynomial operator*(const Polynomial &a, const Polynomial &b) {
        vector<ll> result(a.size() * b.size(), 0LL);
        for (ll i = 0; i < a.size(); i++) {
            for (ll j = 0; j < b.size(); j++) {
                result[i + j] += a.get(i) * b.get(j);
            }
        }
        return *new Polynomial(result);
    }

    friend Polynomial operator+=(Polynomial &a, const Polynomial &b) {
        a = a + b;
        return a;
    }

    friend Polynomial operator-=(Polynomial &a, const Polynomial &b) {
        a = a - b;
        return a;
    }

    friend Polynomial operator*=(Polynomial &a, const Polynomial &b) {
        a = a * b;
        return a;
    }

    Polynomial multiply(ll n) {
        Polynomial _new = *new Polynomial(a);
        for_each(_new.a.begin(), _new.a.end(), [&n](ll &element) { element *= n; });
        return _new;
    }

    [[nodiscard]] size_t size() const {
        return a.size();
    }

    void cut(ll n) {
        if (n < a.size()) {
            vector <ll> _new(n);
            for (ll i = 0; i < n; i++) _new[i] = a[i];
            a = _new;
        }
    }

    vector <ll> get_a() {
        return a;
    }
};

class Divide {
public:
    Divide(ll p, ll q)
            :p(p), q(q) {simplify();}

    Divide(ll p)
            :p(p), q(1) {simplify();}

    Divide() :p(0),q(1) {simplify();}

    friend Divide operator*(const Divide &a, const Divide &b) {
        Divide returned = *new Divide(a.p, a.q);
        returned.multiply(b);
        return returned;
    }

    friend Divide operator/(const Divide &a, const Divide &b) {
        Divide returned = *new Divide(a.p, a.q);
        returned.divide(b);
        return returned;
    }

    friend Divide operator-(const Divide &a, const Divide &b) {
        Divide returned = *new Divide(a.p, a.q);
        returned.minus(b);
        return returned;
    }

    friend Divide operator+(const Divide &a, const Divide &b) {
        Divide returned = *new Divide(a.p, a.q);
        returned.plus(b);
        return returned;
    }

    friend Divide operator+=(Divide &a, const Divide &b) {
        a = a + b;
        return a;
    }

    friend Divide operator-=(Divide &a, const Divide &b) {
        a = a - b;
        return a;
    }

    ll get_p() {
        return p;
    }

    ll get_q() {
        return q;
    }

    void simplify() {
        ll GCD = gcd(abs(p), abs(q));
        if (GCD != 0) {
            p /= GCD;
            q /= GCD;
        }
        if (q < 0) {
            q *= -1;
            p *= -1;
        }
    }

    void multiply(ll a) {
        p *= a;
        simplify();
    }

    void divide(ll a) {
        q *= a;
        simplify();
    }

    void multiply(Divide a) {
        ll GCD = gcd(q, a.p);
        q /= GCD;
        a.p /= GCD;
        GCD = gcd(a.q, p);
        a.q /= GCD;
        p /= GCD;
        q *= a.q;
        p *= a.p;
        simplify();
    }

    void divide(Divide a) {
        a.reversal();
        multiply(a);
        simplify();
    }

    void minus(Divide a) {
        if (p != 0 && a.p != 0) {
            ll k = q / gcd(q, a.q) * a.q ;
            ll k1 = k / q, k2 = k / a.q;
            ll p_new = p * k1 - a.p * k2;
            p = p_new;
            q = k;
        } else {
            if (p == 0) {
                p = -a.p;
                q = a.q;
            }
        }
        simplify();
    }

    void plus(Divide a) {
        if (p != 0 && a.p != 0) {
            ll k = q / gcd(q, a.q) * a.q;
            ll k1 = k / q, k2 = k / a.q;
            ll p_new = p * k1 + a.p * k2;
            p = p_new;
            q = k;
            simplify();
        } else {
            if (p == 0) {
                p = a.p;
                q = a.q;
            }
        }
    }

    void reversal() {
        swap(p, q);
    }

    ll p;
    ll q;
};

ll r, k;
Polynomial p;

void read() {
    cin >> r >> k;
    k++;
    vector <ll> temp(k);
    for (ll i = 0; i < k; i++) cin >> temp[i];
    p = *new Polynomial(temp);
}

ll fact(ll n) {
    ll result = 1;
    for (ll i = 1; i <= n; i++) result *= i;
    return result;
}

Polynomial factP(ll n) {
    Polynomial answer = *new Polynomial({1});
    for (int i = 1; i <= n; i++) {
        answer *= *new Polynomial({i, 1});
    }
    return answer;
}

Polynomial answer, power;

void solve() {
    vector <ll> koff(k);
    for (int i = k - 1; i >= 0; i--) {
        ll koff_on_i = p.get(i);
        ll how_much_we_minus = koff_on_i;
        koff[i] = how_much_we_minus * fact(i);
        p -= factP(i).multiply(how_much_we_minus);
    }
    answer = *new Polynomial({0});
    power = *new Polynomial({1});
    Polynomial powerUP = *new Polynomial({1, -r});
    for (int i = k - 1; i >= 0; i--) {
        answer += power.multiply(koff[i]);
        power *= powerUP;
    }
}

void write() {
    cout << k - 1 << endl;
    for (int i = 0; i < k; i++) {
        cout << answer.get(i) << ' ';
    }
    cout << endl << k << endl;
    for (int i = 0; i < k + 1; i++) {
        cout << power.get(i) << ' ';
    }
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