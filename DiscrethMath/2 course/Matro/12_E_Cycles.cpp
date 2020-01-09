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
#include <unordered_map>
#include <fstream>
#include <list>

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

int32 n;
vector <bool> is;
vector <ll> weight;
vector <int32> masks;

void fill(int32 m) {
    weight.resize(n);
    masks.resize(m, 0);
    is.resize(1 << n, false);
}

void read() {
    int32 m;
    cin >> n >> m;
    fill(m);
    for (int32 i = 0; i < n; i++) {
        cin >> weight[i];
    }
    int32 count, v;
    for (int32 i = 0; i < m; i++) {
        cin >> count;
        for (int32 j = 0; j < count; j++) {
            cin >> v;
            v--;
            masks[i] |= (1 << v);
        }
    }
}

void dfs(int32 v) {
    is[v] = true;
    for (int32 i = 0; i < n; i++) {
        if ((v & (1 << i)) == 0 && !is[v | (1 << i)]) {
            dfs(v | (1 << i));
        }
    }
}

int main() {
    // begin of my useless prog
#ifdef MY_DEBUG
    freopen("input.txt", "r", stdin);
    freopen("output.txt", "w", stdout);
#else
    freopen("cycles.in", "r", stdin);
    freopen("cycles.out", "w", stdout);
#endif
    ios_base::sync_with_stdio(NULL);
    cin.tie(NULL);
    cout.tie(NULL);
    read();
    for (int32 mask : masks) {
        dfs(mask);
    }
    ll maxima_sum = 0, maxima_size = 0, current_sum = 0, current_size = 0;
    for (int32 mask = 1; mask < (1 << n); mask++) {
        if (!is[mask]) {
            current_size = 0;
            current_sum = 0;
            for (int32 i = 0; i < n; i++) {
                if (mask & (1 << i)) {
                    current_sum += weight[i];
                    current_size++;
                }
            }
            if (current_sum >= maxima_size) {
                maxima_size = current_size;
                maxima_sum = (maxima_size == current_size ? max(maxima_sum, current_sum) : current_sum);
            }
        }
    }
    cout << maxima_sum;
    re
}