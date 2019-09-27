/// what about the useless text at the beginning 
//                               of the program?
///                 I like to do useless things.
//       The program is made by vladrus13 (2018)

// Do you want to optimize? 
#pragma GCC optimize("Ofast,no-stack-protector")
#pragma GCC target("sse,sse2,sse3,ssse3,sse4,popcnt,abm,mmx,avx,avx2,tune=native")
#pragma GCC optimize("unroll-loops")
#pragma GCC optimize("fast-math")
#pragma GCC optimize("section-anchors")
#pragma GCC optimize("profile-values,profile-reorder-functions,tracer")
#pragma GCC optimize("vpt")
#pragma GCC optimize("rename-registers")
#pragma GCC optimize("move-loop-invariants")
#pragma GCC optimize("unswitch-loops")
#pragma GCC optimize("function-sections")
#pragma GCC optimize("data-sections")
#pragma GCC optimize("branch-target-load-optimize")
#pragma GCC optimize("branch-target-load-optimize2")
#pragma GCC optimize("btr-bb-exclusive")

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
#define INF (int)2e9;
#define MOD (int)1e9+7;

//////////////////////////////////////////////////////////////////
// SYSTEM STARTS



//////////////////////////////////////////////////////////////////
// UTILS

/////////////////////////////////////////////////////////////////
// MAIN

vector<vector<int> > g;
vector<int> vertical;
vector<int> pC;

int dfs(int p, int v) {
	int res = 1;
	for (int i = 0; i < g[v].size(); ++i) {
		if (g[v][i] != p) {
			res += dfs(v, g[v][i]);
		}
	}
	vertical[v] = res;
	return res;
}

int sC(int v) {
	dfs(v, v);
	int size = vertical[v];
	bool end = false;
	int p = v;
	while (!end) {
		end = true;
		for (int i = 0; i < g[v].size(); ++i) {
			if (g[v][i] != p && vertical[g[v][i]] > size / 2) {
				end = false;
				p = v;
				v = g[v][i];
				break;
			}
		}
	}
	return v;
}

void new_side(int v, int prev) {
	int cur = sC(v);
	pC[cur] = prev;
	vector<int> next;
	for (int i : g[cur]) {
		next.push_back(i);
	}
	g[cur].clear();
	for (int i : next) {
		for (auto j = g[i].begin(); j != g[i].end(); ++j) {
			if (*j == cur) {
				g[i].erase(j);
				break;
			}
		}
		new_side(i, cur);
	}
}

int main() {
	// begin of my useless prog
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	// freopen("minonpath.in", "r", stdin);
	// freopen("minonpath.out", "w", stdout);
#endif
	ios_base::sync_with_stdio(NULL);
	cin.tie(NULL);
	cout.tie(NULL);
	int n;
	cin >> n;
	int u, v;
	g.resize(n, vector<int>());
	vertical.resize(n);
	pC.resize(n);
	for (int i = 1; i < n; ++i) {
		cin >> u >> v;
		u--, v--;
		g[u].push_back(v);
		g[v].push_back(u);
	}
	new_side(0, -1);
	for (int i = 0; i < n; ++i) {
		cout << pC[i] + 1 << ' ';
	}
	return 0;
}