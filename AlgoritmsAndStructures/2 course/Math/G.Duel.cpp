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
#include <complex>

#define _CRT_SECURE_NO_WARNINGS

using namespace std;

typedef int32_t int32;
typedef long double ld;
typedef long long ll;

vector <int> a, b;
bool neg;

void read() {
    string s;
    cin >> s;
    for_each(s.begin(), s.end(), [](char ch){
        a.push_back((int) (ch - '0'));
        b.push_back((int) (ch - '0'));
    });
}

void fft(vector <complex <double> > & a) {
    if (a.size() == 1) return;
    vector <complex<double> > left(a.size() / 2);
    vector <complex<double> > right(a.size() / 2);
    for (int i = 0; i < a.size(); i += 2) {
        left[i / 2] = a[i];
        right[i / 2] = a[i + 1];
    }
    fft(left);
    fft(right);
    ld angle = 2 * 3.14159265359 / a.size();
    complex<double> w(1, 0);
    complex<double> wn(cos(angle), sin(angle));
    for (int i = 0; i < a.size() / 2; i++) {
        a[i] = left[i] + w * right[i];
        a[i + a.size() / 2] = left[i] - w * right[i];
        w *= wn;
    }
}

void anti_fft(vector <complex <double> > & a) {
    if (a.size() == 1) return;
    vector <complex<double> > left(a.size() / 2);
    vector <complex<double> > right(a.size() / 2);
    for (int i = 0; i < a.size(); i += 2) {
        left[i / 2] = a[i];
        right[i / 2] = a[i + 1];
    }
    anti_fft(left);
    anti_fft(right);
    ld angle = -2 * 3.14159265359 / a.size();
    complex<double> w(1, 0);
    complex<double> wn(cos(angle), sin(angle));
    for (int i = 0; i < a.size() / 2; i++) {
        a[i] = left[i] + w * right[i];
        a[i + a.size() / 2] = left[i] - w * right[i];
        a[i] /= 2;
        a[i + a.size() / 2] /= 2;
        w *= wn;
    }
}

vector <int> multiply(vector <int> &a, vector <int> &b) {
    vector <complex<double> > fa(a.begin(), a.end());
    vector <complex<double> > fb(b.begin(), b.end());
    int power = 1;
    while (power < max(a.size(), b.size())) power *= 2;
    power *= 2;
    fa.resize(power);
    fb.resize(power);
    fft(fa);
    fft(fb);
    for (int i = 0; i < power; i++) fa[i] *= fb[i];
    anti_fft(fa);
    vector <int> answer(power);
    for (int i = 0; i < power; i++) {
        answer[i] = (int) round(fa[i].real());
    }
    return answer;
}

ll answer = 0;

void solve() {
    vector <int> c = multiply(a, b);
    for (int i = 0; i < a.size(); i++) {
        answer += (a[i] == 1 ? (c[2 * i] - 1) / 2 : 0);
    }
}

void write() {
    cout << answer << endl;
}

int main() {
    // begin of my useless prog
#ifdef MY_DEBUG
    freopen("input.txt", "r", stdin);
    freopen("output.txt", "w", stdout);
#else
    freopen("duel.in", "r", stdin);
    freopen("duel.out", "w", stdout);
#endif
    ios_base::sync_with_stdio(NULL);
    cin.tie(NULL);
    cout.tie(NULL);
    read();
    solve();
    write();
    return 0;
}
