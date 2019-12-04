/// what about the useless text at the beginning
//                               of the program?
///                 I like to do useless things.
//       The program is made by vladrus13 (2019)

// Do you want to optimize?
#define _CRT_SECURE_NO_WARNINGS
#include <iostream>
#include <algorithm>
#include <random>
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
#include <random>

typedef long double ld;
typedef int64_t ll;
typedef uint64_t ull;
typedef int32_t int32;

using namespace std;

//////////////////////////////////////////////////////////////////
// SYSTEM STARTS

//////////////////////////////////////////////////////////////////
// UTILS

/////////////////////////////////////////////////////////////////
// MAIN

int n;

vector <int> q;

void cut(int i) {
    int l = 0, r = q.size() - 1, m = 0;
    string s;
    while (l < r + 1) {
        m = (l + r) / 2;
        cout << "1 " << i << ' ' << q[m] << endl;
        cin >> s;
        if (s == "YES") {
            r = m - 1;
        } else {
            l = m + 1;
        }
    }
    q.insert(q.begin() + m, i);
    if (s == "NO") {
        swap(q[m], q[m + 1]);
    }
}

void ender() {
    cout << 0 << ' ';
    for (int i : q) {
        cout << i << ' ';
    }
    cout << endl;
}

int main() {
    // begin of my useless prog
#ifdef MY_DEBUG
    //freopen("input.txt", "r", stdin);
    //freopen("output.txt", "w", stdout);
#else
#endif
    ios_base::sync_with_stdio(NULL);
    cin.tie(NULL);
    cout.tie(NULL);
    cin >> n;
    q.push_back(1);
    for (int i = 2; i <= n; i++) cut(i);
    ender();
    return 0;
}