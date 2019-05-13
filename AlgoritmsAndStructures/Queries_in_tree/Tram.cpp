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
#include <random>

typedef long double ld;
typedef int64_t ll;
typedef uint64_t ull;
typedef int32_t int32;

using namespace std;
//#pragma comment (linker, "/STACK:5000000000")
#define MOD (int)1e9+7;

//////////////////////////////////////////////////////////////////
// SYSTEM STARTS


/////////////////////////////////////////////////////////////////
// MAIN

vector <int> x, y, u, c, used;
vector <vector <int> > g, rev;
int answer = -1;

int dfs2(int x) {
	c[x] = 0;
	int y = used[x];
	for (int i : g[x]) {
		if (c[i] == 2) {
			y += dfs2(i);
		}
	}
	if (y == 0) {
		answer++;
	}
	return y;
}

int gett(int x) {
	if (u[x] != x) {
		u[x] = gett(u[x]);
	}
	return u[x];
}

void uni(int x, int i) {
	u[gett(i)] = gett(x);
}

void dfs(int x) {
	c[x] = 1;
	for (int i : g[x]) {
		if (c[i] > 0) continue;
		dfs(i);
		uni(x, i);
	}
	for (int i : rev[x]) {
		if (c[y[i]] == 2) {
			int z = gett(y[i]);
			used[z] -= 2;
			used[y[i]]++;
			used[x]++;
		}
	}
	c[x] = 2;
}

int main() {
	// begin of my useless prog
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	// freopen("input.txt", "r", stdin);
	// freopen("output.txt", "w", stdout);
#endif
	ios_base::sync_with_stdio(NULL);
	cin.tie(NULL);
	cout.tie(NULL);
	int n;
	cin >> n;
	g.resize(n);
	x.resize(n - 1);
	y.resize(n - 1);
	vector <int> temp(n);
	for (int i = 0; i < n - 1; i++) {
		cin >> x[i] >> y[i];
		x[i]--, y[i]--;
		temp[x[i]]++;
		temp[y[i]]++;
	}
	for (int i = 0; i < n; i++) {
		g[i].resize(temp[i]);
		temp[i] = 0;
	}
	for (int i = 0; i < n - 1; i++) {
		g[x[i]][temp[x[i]]++] = y[i];
		g[y[i]][temp[y[i]]++] = x[i];
	}
	int m;
	cin >> m;
	x.clear();
	y.clear();
	x.resize(m + m, 0);
	y.resize(m + m, 0);
	for (int i = 0; i < m; i++) {
		cin >> x[2 * i] >> y[2 * i];
		x[2 * i]--, y[2 * i]--;
		x[2 * i + 1] = y[2 * i];
		y[2 * i + 1] = x[2 * i];
	}
	rev.clear();
	rev.resize(n);
	c.resize(n, 0);
	for (int i = 0; i < 2 * m; i++) {
		c[x[i]]++;
	}
	for (int i = 0; i < n; i++) {
		rev[i].resize(c[i]);
		c[i] = 0;
	}
	for (int i = 0; i < 2 * m; i++) {
		rev[x[i]][c[x[i]]++] = i;
	}
	u.clear();
	u.resize(n);
	for (int i = 0; i < n; i++) {
		u[i] = i;
	}
	c.clear();
	used.clear();
	c.resize(n, 0);
	used.resize(n, 0);
	dfs(0);
	dfs2(0);
	cout << answer << '\n';
	return 0;
}