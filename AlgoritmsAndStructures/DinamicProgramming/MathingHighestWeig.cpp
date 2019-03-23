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

vector <vector <pair <ll, ll> > > g;

void relax(ll &a, ll b) {
	a = max(a, b);
}

vector <ll> rec(ll v, ll p) {
	vector <ll> temp;
	ll maximum0 = 0, maximum1 = 0;
	for (pair <ll, ll> i : g[v]) {
		if (i.first != p) {
			temp = rec(i.first, v);
			relax(maximum0, temp[1] - temp[2] + i.second);
			maximum1 += temp[2];
		}
	}
	maximum0 += maximum1;
	vector <ll> ans = { maximum0, maximum1, max(maximum0, maximum1) };
	return ans;
}

ll matching() {
	vector <ll> ans = rec(0, -1);
	return ans[2];
}

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("matching.in", "r", stdin);
	freopen("matching.out", "w", stdout);
#endif
	ll n, v, u, c;
	cin >> n;
	g.resize(n);
	for (int i = 0; i < n - 1; i++) {
		cin >> u >> v >> c;
		u--, v--;
		g[u].push_back(make_pair(v, c));
		g[v].push_back(make_pair(u, c));
	}
	cout << matching();
	return 0;
}
