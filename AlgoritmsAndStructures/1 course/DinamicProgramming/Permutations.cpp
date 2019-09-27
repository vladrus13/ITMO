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

vector <vector <int> > g;
vector <int> power;
vector <vector <ll> > dp;
vector <vector <int> > nod;
vector <int> a;
int n, k;
ll m;

void power_up() {
	power.resize(n + 2);
	power[0] = 1;
	for (int i = 1; i < n + 2; i++) {
		power[i] = power[i - 1] * 2;
	}
}

int gcd(int a, int b) {
	return (b == 0 ? a : gcd(b, a % b));
}

void read() {
	cin >> n >> m >> k;
	power_up();
	g.resize(n);
	a.resize(n);
	for (int i = 0; i < n; i++) {
		cin >> a[i];
	}
	sort(a.begin(), a.end());
}

int get_bit(int x, int s) {
	return (x >> s) & 1;
}

ll rec(int v, int mask) {
	if (mask == 0) {
		return 1;
	}
	if (dp[v][mask] != (ll) -1) {
		return dp[v][mask];
	}
	dp[v][mask] = 0;
	for (int i : g[v]) {
		if (get_bit(mask, i)) {
			dp[v][mask] += rec(i, mask - power[i]);
			if (dp[v][mask] > (ll) m) {
				return dp[v][mask];
			}
		}
	}
	return dp[v][mask];
}

int solve(int last, int mask) {
	ll sum = 0, tek;
	if (last == -1) {
		for (int i = 0; i < n; i++) {
			if (get_bit(mask, i)) {
				sum += (tek = rec(i, mask - power[i]));
				if (sum >= m) {
					cout << a[i] << ' ';
					m -= sum - tek;
					return i;
				}
			}
		}
		return -1;
	}
	for (int i = 0; i < (int) g[last].size(); i++) {
		if (get_bit(mask, g[last][i])) {
			sum += (tek = rec(g[last][i], mask - power[g[last][i]]));
			if (sum >= m) {
				cout << a[g[last][i]] << ' ';
				m -= sum - tek;
				return g[last][i];
			}
		}
	}
	return -1;
}

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("perm.in", "r", stdin);
	freopen("perm.out", "w", stdout);
#endif
	read();
	dp.resize(n, vector <ll>(power[n], -1LL));
	g.resize(n);
	for (int i = 0; i < n; i++) {
		for (int j = i + 1; j < n; j++) {
			if (gcd(a[i], a[j]) >= k) {
				g[i].push_back(j);
				g[j].push_back(i);
			}
		}
	}
	int last = -1, mask = power[n] - 1;
	while (mask) {
		last = solve(last, mask);
		if (last == -1) {
			cout << -1;
			exit(0);
		}
		mask -= power[last];
	}
	return 0;
}
