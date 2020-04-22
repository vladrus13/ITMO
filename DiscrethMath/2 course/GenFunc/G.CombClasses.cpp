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

const ll MOD = 1e9 + 7;

vector <vector <vector <int32> > > dpSum, dpEqSum;

vector <vector <int32> > calculateDPEqSum(int32 n, int32 min) {
    vector <vector <int32> > answer;
    for (int element = min; element <= n; element++) {
        for (int counter = 1; counter <= n / element; counter++) {
            auto temp = calculateDPEqSum(n - element * counter, element + 1);
            for (auto &ele : temp) {
                for (int i = 1; i <= counter; i++) {
                    ele.push_back(element);
                }
                answer.push_back(move(ele));
            }

            if (counter * element == n) {
                vector <int> tmpp;
                for (int kk = 0; kk < counter; kk++) {
                    tmpp.push_back(element);
                }
                answer.push_back(move(tmpp));
            }
        }
    }
    return answer;
}

vector <vector <int32> > calculateDPSum(int n) {
    vector <vector <int32> > answer;
    for (int summer = 1; summer < n; summer++) {
        auto temp = calculateDPSum(n - summer);
        for (auto & element : temp) {
            element.push_back(summer);
            answer.push_back(move(element));
        }
    }
    if (n > 0) {
        answer.push_back({n});
    }
    return answer;
}

ll A(ll n, ll k) {
    if (k == 0) {
        return 1;
    }
    n = n + k - 1;
    if (n < k) {
        return 0;
    }
    ll answer = 1;
    for (ll i = n - k + 1; i <= n; i++) {
        answer *= i;
    }
    for (ll i = 2; i <= k; i++) {
        answer /= i;
    }
    return answer;
}

vector <ll> read() {
    char c, useless;
    cin >> c;
    if (c == 'B') {
        vector <ll> answer{0, 1, 0, 0, 0, 0, 0};
        return answer;
    }
    cin >> useless;
    vector <ll> recursive = read();
    cin >> useless;
    vector <ll> answer(7);
    switch (c) {
        case 'L':
            answer[0] = 1;
            for (int i = 1; i < 7; i++) {
                for (auto & element : dpSum[i]) {
                    ll temp = 1;
                    for (auto e : element) {
                        temp *= recursive[e];
                    }
                    answer[i] += temp;
                }
            }
            break;
        case 'S':
            answer[0] = 1;
            for (int i = 1; i < 7; i++) {
                for (auto & v : dpEqSum[i]) {
                    ll temp = 1;
                    vector <int> w(7);
                    for (auto e : v) {
                        w[e]++;
                    }
                    for (int j = 1; j < 7; j++) {
                        temp *= A(recursive[j], w[j]);
                    }
                    answer[i] += temp;
                }
            }
            break;
        case 'P':
            vector <ll> recursive2 = read();
            cin >> useless;
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j <= i; j++) {
                    answer[i] += recursive[j] * recursive2[i - j];
                }
            }
            break;
    }
    return answer;
}

vector <ll> answer;

void solve() {
    answer = read();
}

void write() {
    for (auto e : answer) {
        cout << e << ' ';
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
    for (int i = 0; i < 7; i++) {
        dpSum.push_back(move(calculateDPSum(i)));
        dpEqSum.push_back(move(calculateDPEqSum(i, 1)));
    }
    solve();
    write();
    return 0;
}