/// what about the useless text at the beginning
//                               of the program?
///                 I like to do useless things.
//       The program is made by vladrus13 (2019)

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

vector <vector <bool> > g;

deque <int> q;

void reverser(int begin, int end) {
    int size = end - begin + 1;
    for (int i = 0; i * 2 < size; i++) {
        swap(q[i + begin], q[end - i]);
    }
}

void find() {
    for (int i = 0; i < n; i++) q.push_back(i);
    for (int k = 0; k <= n * (n - 1); k++) {
        if (g[q[0]][q[1]] == 0) {
            int i = 2;
            while (i < q.size() - 1 && ((!g[q[0]][q[i]]) or (!g[q[1]][q[i + 1]]))) i++;
            if (i == q.size() - 1) {
                i = 2;
                while (i < q.size() && !g[q[0]][q[i]]) i++;
            }
            reverse(q.begin() + 1, q.begin() + i + 1);
        }
        q.push_back(q.front());
        q.pop_front();
    }
}

int main() {
    // begin of my useless prog
#ifdef MY_DEBUG
    freopen("input.txt", "r", stdin);
    freopen("output.txt", "w", stdout);
#else
    freopen("chvatal.in", "r", stdin);
    freopen("chvatal.out", "w", stdout);
#endif
    ios_base::sync_with_stdio(NULL);
    cin.tie(NULL);
    cout.tie(NULL);
    cin >> n;
    char ch;
    g.resize(n, vector <bool> (n, 0));
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < i; j++) {
            cin >> ch;
            if (ch == '1') {
                g[i][j] = 1;
                g[j][i] = 1;
            }
        }
    }
    find();
    for (int i = 0; i < q.size(); i++) {
        cout << q[i] + 1 << ' ';
    }
    return 0;
}