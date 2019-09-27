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

ll n, m;
vector <ll> power;
vector <vector <pair <ll, ll> > > g;
vector <vector <ll> > dp;

void power_up() {
	power.resize(n + 2);
	power[0] = 1;
	for (int i = 1; i < n + 2; i++) {
		power[i] = power[i - 1] * 2;
	}
}

void resizer() {
	power_up();
	g.resize(n);
	dp.resize(n, vector <ll>(power[n], (ll) 1e17));
}

void read() {
	ll v, u, c;
	cin >> n >> m;
	resizer();
	for (int i = 0; i < m; i++) {
		cin >> v >> u >> c;
		v--, u--;
		g[v].push_back({ u, c });
		g[u].push_back({ v, c });
	}
}

ll get_bit(ll x, ll s) {
	return (x >> s) & 1;
}

void relax(ll &a, ll b) {
	a = min(a, b);
}

ll rec(ll v, ll mask) {
	if (mask == 0) {
		return 0LL;
	}
	if (dp[v][mask] != (ll) 1e17) {
		return dp[v][mask];
	}
	for (pair <ll, ll> u : g[v]) {
		if (get_bit(mask, u.first)) {
			relax(dp[v][mask], rec(u.first, mask - power[u.first]) + u.second);
		}
	}
	return dp[v][mask];
}

ll solve() {
	dp[0][0] = 0;
	ll answer = (ll) 1e17;
	for (int i = 0; i < n; i++) {
		relax(answer, rec(i, power[n] - 1 - power[i]));
	}
	return answer == (ll) 1e17 ? (ll) -1 : answer;
}

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("salesman.in", "r", stdin);
	freopen("salesman.out", "w", stdout);
#endif
	read();
	cout << solve();
	return 0;
}
