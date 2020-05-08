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

const ll MOD = 1e18 + 7;

int n;
vector <vector <ll> > matrix;
pair <ll, vector <ll> > answer;

void read() {
    cin >> n;
    matrix.resize(n + 1, vector <ll> (n + 1, 0));
    answer.second.resize(n + 1);
    for (int i = 1; i < n + 1; i++) {
        for (int j = 1; j <= n; j++) {
            cin >> matrix[i][j];
        }
    }
}

void solve() {
    vector <ll> u(n + 1), v(n + 1), matching(n + 1), way(n + 1), ans(n + 1);
    for (int row = 1; row <= n; row++) {
        matching[0] = row;
        int column = 0;
        vector <ll> min_v(n + 1, MOD);
        vector <bool> used(n + 1, false);
        do {
            used[column] = true;
            ll current_row = matching[column];
            ll de = MOD;
            ll current_column = 0;
            for (int j = 1; j <= n; j++) {
                if (!used[j]) {
                    ll current = matrix[current_row][j] - u[current_row] - v[j];
                    if (current < min_v[j]) {
                        min_v[j] = current;
                        way[j] = column;
                    }
                    if (min_v[j] < de) {
                        de = min_v[j];
                        current_column = j;
                    }
                }
            }
            for (int j = 0; j <= n; j++) {
                if (used[j]) {
                    u[matching[j]] += de;
                    v[j] -= de;
                } else {
                    min_v[j] -= de;
                }
            }
            column = current_column;
        } while (matching[column] != 0);
        do {
            int jj = way[column];
            matching[column] = matching[jj];
            column = jj;
        } while (column);
    }
    for (int j = 1; j <= n; j++) {
        answer.second[matching[j]] = j;
    }
    answer.first = -v[0];
}

void write() {
    cout << answer.first << endl;
    for (int i = 1; i <= n; i++) {
        cout << i << ' ' << answer.second[i] << endl;
    }
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
    solve();
    write();
    return 0;
}
