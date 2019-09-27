#define _CRT_SECURE_NO_WARNINGS
#include <iostream>
#include <string>
#include <vector>
#include <map>
#include <algorithm>
#include <cmath>
#include <set>
#include <iomanip>
#include <cassert>
#include <fstream>
#include <bitset>
#include <stack>

using namespace std;

typedef long long ll;
typedef long double ld;
typedef unsigned int ui;
typedef unsigned long long ull;

ll n, k;
string s;
vector <vector <ll> > dp;
string ansr;

void write_dp() {
	n++;
	dp.resize(n + 1, vector <ll>(n + 1, 0));
	dp[0][0] = 1;
	for (int i = 1; i <= n; i++) {
		for (int j = 1; j <= i; j++) {
			if (i == j) {
				dp[i][j] = 1;
			} else {
				dp[i][j] = 0;
			}
			for (int k = j; k + j <= i; k++) {
				dp[i][j] += dp[i - j][k];
			}
		}
	}
	n--;
}

void rec(ll n, ll k, ll last) {
	if (n > 0) {
		ll l = 0, steps = min(n, last);
		for (int i = last; i <= n; i++) {
			if (l + dp[n][i] > k) {
				n -= i;
				k -= l;
				last = i;
				ansr += to_string(i) + '+';
				rec(n, k, last);
				break;
			}
			l += dp[n][i];
		}
	}
}

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("num2part.in", "r", stdin);
	freopen("num2part.out", "w", stdout);
#endif
	cin >> n >> k;
	write_dp();
	rec(n, k, 1);
	ansr.pop_back();
	cout << ansr;
	return 0;
}