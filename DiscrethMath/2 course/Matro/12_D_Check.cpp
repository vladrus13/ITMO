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

int32 n, m;
vector <vector <int32> > in;
vector <int32> masks;
vector <bool> is;

void fill() {
    in.resize(m);
    masks.resize(m, 0);
    is.resize((1 << n), 0);
}

void read() {
    int32 count, v;
    cin >> n >> m;
    fill();
    for (int32 i = 0; i < m; i++) {
        cin >> count;
        for (int32 j = 0; j < count; j++) {
            cin >> v;
            v--;
            in[i].push_back(v);
            masks[i] |= (1 << v);
        }
        is[masks[i]] = 1;
    }
}

bool try1() {
    return is[0];
}

bool try2() {
    for (int32 i = 0; i < m; i++) {
        for (int32 j = 0; j < n; j++) {
            if ((masks[i] & (1 << j)) && (!is[masks[i] ^ (1 << j)])) {
                return false;
            }
        }
    }
    return true;
}

bool try3() {
    for (int32 i = 0; i < m; i++) {
        for (int32 j = 0; j < m; j++) {
            if (in[i].size() > in[j].size()) {
                int32 first = masks[i];
                int32 second = masks[j];
                for (int32 l = 0; l < n; l++) {
                    if ((first & (1 << l)) && (second & (1 << l))) {
                        first ^= (1 << l);
                    }
                }
                bool flag = false;
                for (int32 l = 0; l < n; l++) {
                    if ((first & (1 << l)) && is[second + (1 << l)]) {
                        flag = true;
                    }
                }
                if (!flag) {
                    return false;
                }
            }
        }
    }
    return true;
}

int main() {
    // begin of my useless prog
#ifdef MY_DEBUG
    freopen("input.txt", "r", stdin);
    freopen("output.txt", "w", stdout);
#else
    freopen("check.in", "r", stdin);
    freopen("check.out", "w", stdout);
#endif
    ios_base::sync_with_stdio(NULL);
    cin.tie(NULL);
    cout.tie(NULL);
    read();
    cout << ((try1() && try2() && try3()) ? "YES\n" : "NO\n");
    re
}