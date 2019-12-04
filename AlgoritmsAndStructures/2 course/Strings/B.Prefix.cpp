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

vector<int32> prefix_function (string &s) {
    vector<int32> returned(s.size());
    for (int32 i = 1; i < s.size(); ++i) {
        int32 j = returned[i - 1];
        while (j > 0 && s[i] != s[j]) {
            j = returned[j - 1];
        }
        if (s[i] == s[j]) {
            j++;
        }
        returned[i] = j;
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
    vector <int32> pref = prefix_function(s);
    for (int32 i : pref) {cout << i << ' '; }
    re
}
