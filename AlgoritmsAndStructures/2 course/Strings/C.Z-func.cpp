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

vector<int32> z_function (string &s) {
    vector<int32> returned(s.size());
    for (int32 i = 1, l = 0, r = 0; i < s.size(); i++) {
        if (i <= r) {
            returned[i] = min(r - i + 1, returned[i - l]);
        }
        while (i + returned[i] < s.size() && s[returned[i]] == s[i + returned[i]]) {
            returned[i]++;
        }
        if (i + returned[i] - 1 > r) {
            l = i, r = i + returned[i] - 1;
        }
    }
    return returned;
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
    string s;
    cin >> s;
    vector <int32> z = z_function(s);
    z.erase(z.begin());
    for (int32 i : z) {cout << i << ' '; }
    re
}
