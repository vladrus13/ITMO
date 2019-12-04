/// what about the useless text at the beginning
//                               of the program?
///                 I like to do useless things.
//       The program is made by vladrus13 (2018)

// Do you want to optimize?
#define _CRT_SECURE_NO_WARNINGS
#include <iostream>
#include <algorithm>
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

typedef long double ld;
typedef int64_t ll;
typedef uint64_t ull;
typedef int32_t int32;

using namespace std;

void re() {
#ifdef _DEBUG
    system("pause");
#endif
    exit(0);
}

#define re re();

//////////////////////////////////////////////////////////////////
// SYSTEM STARTS

//////////////////////////////////////////////////////////////////
// UTILS

/////////////////////////////////////////////////////////////////
// MAIN

vector <ll> power31, power37;
vector <ll> hash31, hash37;
int32 n;
string s;

void fill_power() {
    power31.resize(n);
    power37.resize(n);
    int32 p31 = 31, p37 = 37;
    power31[0] = 1, power37[0] = 1;
    for (int32 i = 1; i < n; i++) {
        power31[i] = power31[i - 1] * p31;
        power37[i] = power37[i - 1] * p37;
    }
}

void fill_hash() {
    hash31.resize(n), hash37.resize(n);
    for (int32 i = 0; i < n; i++) {
        hash31[i] = (s[i] - 'a' + 1) * power31[i];
        hash37[i] = (s[i] - 'a' + 1) * power37[i];
        if (i) {
            hash31[i] += hash31[i - 1];
            hash37[i] += hash37[i - 1];
        }
        //hash31[i] %= (int32) ((1e9) + 9);
        //hash37[i] %= (int32) ((1e9) + 7);
    }
}

ll findhash31(int start, int end) {
    ll answer = hash31[end];
    if (start) answer -= hash31[start - 1];
    return answer;
}

ll findhash37(int start, int end) {
    ll answer = hash37[end];
    if (start) answer -= hash37[start - 1];
    return answer;
}

bool equals_hash31(int a, int b, int c, int d) {
    ll hashAB = findhash31(a, b);
    ll hashCD = findhash31(c, d);
    return ((a < c) && (hashAB * power31[c - a] == hashCD)) ||
           ((c < a) && (hashCD * power31[a - c] == hashAB));
}

bool equals_hash37(int a, int b, int c, int d) {
    ll hashAB = findhash37(a, b);
    ll hashCD = findhash37(c, d);
    return ((a < c) && (hashAB * power37[c - a] == hashCD)) ||
            ((c < a) && (hashCD * power37[a - c] == hashAB));
}

bool equals(int a, int b, int c, int d) {
    if (b - a != d - c) return false;
    if (a == c) return true;
    return equals_hash31(a, b, c, d) && equals_hash37(a, b, c, d);
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
    cout.setf(ios::fixed);
    cout.precision(20);
    cin >> s;
    n = s.size();
    int32 m, a, b, c, d;
    cin >> m;
    fill_power();
    fill_hash();
    while (m--) {
        cin >> a >> b >> c >> d;
        a--, b--, c--, d--;
        cout << ((equals(a, b, c, d)) ? "Yes\n" : "No\n");
    }
    re
}
