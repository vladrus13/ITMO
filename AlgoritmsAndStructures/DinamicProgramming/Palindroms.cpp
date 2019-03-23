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

int n;
vector <int> a;
vector <vector <ll> > dp;

bool check(int i, int j) {
	if (i < 0 || j >= n) return false;
	return true;
}

ll return_dp(int i, int j) {
	if (i == j) return 1LL;
	if (j < i) return 0LL;
	return dp[i][j];
}

void fill_dp() {
	ll temp;
	for (int size = 0; size < n; size++) {
		for (int i = 0; i < n; i++) {
			int j = i + size;
			if (check(i, j)) {
				if (a[i] == a[j]) {
					dp[i][j] = (return_dp(i, j - 1) + return_dp(i + 1, j) + 1 + (ll) 1e9) % (ll) (1e9);
				} else {
					temp = return_dp(i, j - 1) + return_dp(i + 1, j) - return_dp(i + 1, j - 1);
					dp[i][j] = (temp + (ll) 1e9) % (ll) (1e9);
				}
			}
		}
	}
}

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	// freopen("acm.in", "r", stdin);
	// freopen("acm.out", "w", stdout);
#endif
	cin >> n;
	a.resize(n);
	dp.resize(n, vector <ll>(n));
	for (int i = 0; i < n; i++) cin >> a[i];
	fill_dp();
	cout << dp[0][n - 1] << endl;
	return 0;
}
