/// what about the useless text at the beginning
//                               of the program?
///                 I like to do useless things.
//       The program is made by vladrus13 (2020)

// Do you want to optimize?
#define _CRT_SECURE_NO_WARNINGS

#include <iostream>
#include <algorithm>
#include <vector>
#include <string>
#include <iomanip>
#include <list>
#include <math.h>

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

int32 n, m, a, b, count_breaks, p;
vector <vector <int32> > g1, g2;

vector <int32> ans1, ans2;
vector <bool> used;

int32 get_num(int32 i, int32 j) {
    return i * m + j;
}

void read() {
    cin >> n >> m >> a >> b;
    vector <string> in(n);
    for (int32 i = 0; i < n; i++) cin >> in[i];
    p = n * m;
    g1.resize(p);
    g2.resize(p);
    ans1.resize(p, -1);
    ans2.resize(p, -1);
    used.resize(p, false);
    for (int32 i = 1; i < n; i++) {
        for (int32 j = 1; j < m; j++) {
            if (in[i][j] == '*') {
                if ((i + j) % 2 == 0) {
                    if (in[i - 1][j] == '*') {
                        g1[get_num(i, j)].push_back(get_num(i - 1, j));
                        g2[get_num(i - 1, j)].push_back(get_num(i, j));
                    }
                    if (in[i][j - 1] == '*') {
                        g2[get_num(i, j - 1)].push_back(get_num(i, j));
                        g1[get_num(i, j)].push_back(get_num(i, j - 1));
                    }
                } else {
                    if (in[i - 1][j] == '*') {
                        g2[get_num(i, j)].push_back(get_num(i - 1, j));
                        g1[get_num(i - 1, j)].push_back(get_num(i, j));
                    }
                    if (in[i][j - 1] == '*') {
                        g1[get_num(i, j - 1)].push_back(get_num(i, j));
                        g2[get_num(i, j)].push_back(get_num(i, j - 1));
                    }
                }
                count_breaks++;
            }
        }
    }
    for (int32 i = 1; i < m; i++) {
        if (in[0][i] == '*') {
            if ((0 + i) % 2 == 0) {
                if (in[0][i - 1] == '*') {
                    g2[get_num(0, i - 1)].push_back(get_num(0, i));
                    g1[get_num(0, i)].push_back(get_num(0, i - 1));
                }
            } else {
                if (in[0][i - 1] == '*') {
                    g1[get_num(0, i - 1)].push_back(get_num(0, i));
                    g2[get_num(0, i)].push_back(get_num(0, i - 1));
                }
            }
            count_breaks++;
        }
    }
    for (int32 i = 1; i < n; i++) {
        if (in[i][0] == '*') {
            if ((i + 0) % 2 == 0) {
                if (in[i - 1][0] == '*') {
                    g1[get_num(i, 0)].push_back(get_num(i - 1, 0));
                    g2[get_num(i - 1, 0)].push_back(get_num(i, 0));
                }
            } else {
                if (in[i - 1][0] == '*') {
                    g2[get_num(i, 0)].push_back(get_num(i - 1, 0));
                    g1[get_num(i - 1, 0)].push_back(get_num(i, 0));
                }
            }
            count_breaks++;
        }
    }
    if (in[0][0] == '*') count_breaks++;
}

// kun? no) "i like Ford"
bool dfs(int32 x) {
    if (used[x]) return false;
    used[x] = true;
    for (int32 y : g1[x]) {
        if (ans2[y] == -1) {
            ans1[x] = y;
            ans2[y] = x;
            return true;
        } else {
            if (dfs(ans2[y])) {
                ans1[x] = y;
                ans2[y] = x;
                return true;
            }
        }
    }
    return false;
}

void Ford() {
    bool isPath = true;
    while (isPath) {
        isPath = false;
        used.assign(n, false);
        for (int i = 0; i < p; i++) {
            if (ans1[i] == -1) {
                if (dfs(i)) {
                    isPath = true;
                }
            }
        }
    }
}

void solve() {
    read();
    Ford();
    int32 counter = 0;
    for (int i = 0; i < p; i++) {
        counter += (ans1[i] == -1 ? 0 : 1);
        counter += (ans2[i] == -1 ? 0 : 1);
    }
    counter /= 2;
    if (b < 0 && a < 0) {
        if ((-b) * 2 >= (-a)) {
            cout << count_breaks * b << endl;
        } else {
            cout << counter * a + (count_breaks - counter * 2) * b << endl;
        }
    }
    if (b < 0 && a >= 0) {
        cout << count_breaks * b << endl;
    }
    if (b >= 0 && a < 0) {
        cout << counter * a + (count_breaks - counter * 2) * b << endl;
    }
    if (b >= 0 && a >= 0) {
        if (b * 2 <= a) {
            cout << b * count_breaks;
        } else {
            cout << counter * a + (count_breaks - counter * 2) * b << endl;
        }
    }
}

int main() {
    // begin of my useless prog
#ifdef MY_DEBUG
    freopen("input.txt", "r", stdin);
    freopen("output.txt", "w", stdout);
#else
    freopen("dominoes.in", "r", stdin);
    freopen("dominoes.out", "w", stdout);
#endif
    ios_base::sync_with_stdio(NULL);
    cin.tie(NULL);
    cout.tie(NULL);
    solve();
    return 0;
}
