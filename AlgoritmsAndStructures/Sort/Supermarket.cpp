#define _CRT_SECURE_NO_WARNINGS
#include <iostream>
#include <string>
#include <vector>
#include <map>
#include <algorithm>
#include <cmath>
#include <set>
#include <iomanip>

using namespace std;

typedef long long ll;
typedef long double ld;
typedef unsigned int ui;

int m, n, p;
ll timee;
vector <vector <int> > c;
// [0] - a, [1] - b, [2] - t   _i

ll cass(int it) {
	if (c[it][0] == 0) return (int) 1e8;
	return (max((ll) 0, timee - c[it][2] - c[it][1])) / c[it][0];
}

int sorted[(int) 1e5 + 1];



bool check(ll tim) {
	for (int i = 0; i < p; i++) sorted[i] = 0;
	timee = tim;
	if (timee < 0) {
		return false;
	}

	for (int i = 0; i < m; i++) {
		if (cass(i) >= p) {
			return true;
		} else {
			sorted[cass(i)]++;
		}
	}

	ll bying = 0;
	int ost = min(n, m);
	for (int i = p - 1; i >= 0; i--) {
		if (ost >= sorted[i]) {
			bying += sorted[i] * i;
			ost -= sorted[i];
		} else {
			bying += ost * i;
			ost -= ost;
		}
		if (bying >= p) return true;
		if (ost <= 0) return false;
	}
	return false;
}

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("supermarket.in", "r", stdin);
	freopen("supermarket.out", "w", stdout);
#endif
	cin >> m;
	c.resize(m, vector <int>(3));
	for (int i = 0; i < m; i++) {
		cin >> c[i][0] >> c[i][1] >> c[i][2];
	}
	cin >> n >> p;
	ll l = 0, r = (ll) 1e10, med;
	while (1 < r - l) {
		med = (r + l) / 2;
		if (check(med)) {
			r = med;
		} else {
			l = med;
		}
	}
	if (check(l - 1)) {
		cout << l - 1 << '\n';
		return 0;
	}
	if (check(l)) {
		cout << l << '\n';
		return 0;
	}
	if (check(r)) {
		cout << r << '\n';
		return 0;
	}
	cout << r + 1 << '\n';
	return 0;
}
