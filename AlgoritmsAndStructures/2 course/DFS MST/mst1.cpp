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
#include <stack>

typedef long double ld;
typedef int64_t ll;
// typedef uint64_t ull;
typedef int32_t int32;

using namespace std;
//#pragma comment (linker, "/STACK:5000000000")
#define INF (int)2e9;
#define MOD (ll)(1e9+7)

//////////////////////////////////////////////////////////////////
// SYSTEM STARTS



//////////////////////////////////////////////////////////////////
// UTILS

/////////////////////////////////////////////////////////////////
// MAIN

class point {
private:
    int x, y;
public:
    int getX() const {
        return x;
    }

    int getY() const {
        return y;
    }

    void setX(int x) {
        point::x = x;
    }

    void setY(int y) {
        point::y = y;
    }
};

ld sqr(ld x) {
    return x * x;
}

ld dist(point a, point b) {
    return sqrt(sqr(a.getX() - b.getX()) + sqr(a.getY() - b.getY()));
}

vector <point> xy;
vector <bool> used;
vector <ld> dp;

int main() {
    ios_base::sync_with_stdio(NULL);
    cin.tie(NULL);
    cout.tie(NULL);
    int n, m, current_ = 1, x, y;
    ld answer = 0;
    cin >> n;
    xy.resize(n);
    used.resize(n);
    dp.resize(n, 1e9);
    for (int i = 0; i < n; i++) {
        cin >> x >> y;
        xy[i].setX(x);
        xy[i].setY(y);
    }
    for (int i = 1; i < n; i++) {
        dp[i] = dist(xy[i], xy[0]);
    }
    dp[0] = 0;
    used[0] = true;
    while (true) {
        double minima = 1e9;
        int uv = 0;
        for (int i = 0; i < n; i++) {
            if (!used[i]) {
                if (minima > dp[i]) {
                    uv = i;
                    minima = dp[i];
                }
            }
        }
        if (uv != 0) {
            dp[uv] = 0;
            used[uv] = true;
            for (int i = 1; i < n; i++) {
                if (!used[i]) {
                    if (dist(xy[uv], xy[i]) < dp[i]) {
                        dp[i] = dist(xy[uv], xy[i]);
                    }
                }
            }
            answer += minima;
        } else {
            break;
        }
    }
    cout << fixed << setprecision(30) << answer << endl;
    return 0;
}
