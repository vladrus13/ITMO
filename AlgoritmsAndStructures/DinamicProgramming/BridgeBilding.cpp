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

int x, a, y, b, l;
vector <vector <int> > dp;

void read() {
	cin >> x >> a >> y >> b >> l;
}

void relax(int &a, int b) {
	a = max(a, b);
}

bool check(int w) {
	dp.clear();
	dp.resize(x + 1, vector <int>(y + 1, 0));
	// dp[i][j] - min length bridge on i and j usage wood
	for (int wx = 0; wx <= x; wx++) {
		for (int wy = 0; wy <= y; wy++) {
			for (int i = 0; (i - 1) * a <= w; i++) {
				int j = max(0, w - i * a);
				j = (j + b - 1) / b;
				if (wx + i <= x && wy + j <= y) {
					relax(dp[wx + i][wy + j], dp[wx][wy] + 1);
				}
			}
		}
	}
	int max_l = 0;
	for (int i = 0; i <= x; i++) {
		for (int j = 0; j <= y; j++) {
			relax(max_l, dp[i][j]);
		}
	}
	if (max_l >= l) return true;
	return false;
}

void solve() {
	int l = 0, r = x * a + y * b, m;
	// binary search wehee
	while (1 < r - l) {
		m = (r + l) / 2;
		if (check(m)) {
			l = m;
		} else {
			r = m;
		}
	}
	if (check(r)) {
		cout << r;
	} else {
		cout << l;
	}
}

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("bridge.in", "r", stdin);
	freopen("bridge.out", "w", stdout);
#endif
	// stupid task arrr...
	read();
	solve();
	return 0;
}
