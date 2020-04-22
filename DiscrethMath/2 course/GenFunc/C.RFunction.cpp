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

class Polynomial {
    vector<ll> a;

public:

    explicit Polynomial(vector<ll> a) {
        this->a = move(a);
    }

    Polynomial() {
        a = vector<ll>(0);
    }

    [[nodiscard]] ll get(int pos) const {
        if (pos >= a.size()) return 0;
        return a[pos];
    }

    friend Polynomial operator+(const Polynomial &a, const Polynomial &b) {
        vector<ll> result(max(a.size(), b.size()), 0);
        for (int i = 0; i < a.size(); i++) result[i] += a.get(i);
        for (int i = 0; i < b.size(); i++) result[i] += b.get(i);
        return Polynomial(result);
    }

    friend Polynomial operator-(const Polynomial &a, const Polynomial &b) {
        vector<ll> result(max(a.size(), b.size()), 0);
        for (int i = 0; i < a.size(); i++) result[i] += a.get(i);
        for (int i = 0; i < b.size(); i++) result[i] -= b.get(i);
        return Polynomial(result);
    }

    friend Polynomial operator*(const Polynomial &a, const Polynomial &b) {
        vector<ll> result(a.size() * b.size(), 0LL);
        for (int i = 0; i < a.size(); i++) {
            for (int j = 0; j < b.size(); j++) {
                result[i + j] += a.get(i) * b.get(j);
            }
        }
        return Polynomial(result);
    }

    friend Polynomial operator*=(Polynomial &a, const Polynomial &b) {
        a = a * b;
        return a;
    }

    friend Polynomial operator+=(Polynomial &a, const Polynomial &b) {
        a = a + b;
        return a;
    }

    Polynomial multiply(ll n) {
        Polynomial _new = Polynomial(a);
        for_each(_new.a.begin(), _new.a.end(), [&n](ll &element) { element *= n; });
        return _new;
    }

    [[nodiscard]] size_t size() const {
        return a.size();
    }

    void cut(ll n) {
        if (n < a.size()) {
            vector <ll> _new(n);
            for (int i = 0; i < n; i++) _new[i] = a[i];
            a = _new;
        }
    }

    vector <ll>& get_a() {
        return a;
    }
};

int k;
Polynomial a, n, p, q;

void read() {
    cin >> k;
    vector <ll> aa(k), nn(k + 1);
    for (int i = 0; i < k; i++) cin >> aa[i];
    for (int i = 1; i <= k; i++) cin >> nn[i];
    a = Polynomial(aa);
    n = Polynomial(nn);
}

void solve() {
    q = Polynomial(vector<ll> (1, 1)) - n;
    p = a * q;
    p.cut(k);
}

void write() {
    int lastP = p.size() - 1;
    int lastQ = q.size() - 1;
    while (lastP > 0 && p.get(lastP) == 0) lastP--;
    while (lastQ > 0 && q.get(lastQ) == 0) lastQ--;
    cout << lastP << endl;
    for (int i = 0; i <= lastP; i++) cout << p.get(i) << ' ';
    cout << endl << lastQ << endl;
    for (int i = 0; i <= lastQ; i++) cout << q.get(i) << ' ';
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