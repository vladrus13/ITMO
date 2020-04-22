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
        vector <ll> result(a.size() * b.size() + 1, 0);
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

    Polynomial reversal(int n) {
        vector <ll> reversal_b;
        reversal_b.push_back(1 / this->get(0));
        for (int i = 1; i <= n; i++) {
            ll k = 0;
            for (int j = 1; j <= i; j++) {
                k = (k + this->get(j) * reversal_b[i - j] + MOD) % MOD;
            }
            reversal_b.push_back((-k / this->get(0) + MOD) % MOD);
        }
        return Polynomial(reversal_b);
    }

    [[nodiscard]] size_t size() const {
        return a.size();
    }
};

int n, m;
Polynomial p, q;
Polynomial Aplus, Amultiply, Adivide;

void read() {
    vector <ll> pp, qq;
    cin >> n >> m;
    pp.resize(n + 1);
    qq.resize(m + 1);
    for (int i = 0; i < n + 1; i++) cin >> pp[i];
    for (int i = 0; i < m + 1; i++) cin >> qq[i];
    p = Polynomial(pp);
    q = Polynomial(qq);
}

void calculate_plus() {
    Aplus = p + q;
}

void calculate_multiply() {
    Amultiply = p * q;
}

void calculate_divide() {
    Adivide = p * q.reversal(1000);
}

void write() {
    int last_plus = Aplus.size() - 1;
    int last_multiply = Amultiply.size() - 1;
    while (last_plus != 0 && Aplus.get(last_plus) == 0) last_plus--;
    while (last_multiply != 0 && Amultiply.get(last_multiply) == 0) last_multiply--;
    cout << last_plus << endl;
    for (int i = 0; i <= last_plus; i++) cout << Aplus.get(i) << ' ';
    cout << endl;
    cout << last_multiply << endl;
    for (int i = 0; i <= last_multiply; i++) cout << Amultiply.get(i) << ' ';
    cout << endl;
    for (int i = 0; i < 1000; i++) cout << Adivide.get(i) << ' ';
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
    calculate_plus();
    calculate_multiply();
    calculate_divide();
    write();
    return 0;
}