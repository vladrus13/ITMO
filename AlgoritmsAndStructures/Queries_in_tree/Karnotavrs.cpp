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

vector <int> depth, parents;
vector <vector <int> > dp;
vector <bool> is_dead;

void i_wanna_be_a_dad(int v, int parent, int log_n) {
	if (is_dead[parent]) {
		i_wanna_be_a_dad(v, parents[parent], log_n);
	}
	parents[v] = parent;
	depth[v] = depth[parent] + 1;
	dp[v][0] = parent;
	for (int i = 1; i < log_n; ++i) {
		dp[v][i] = dp[dp[v][i - 1]][i - 1];
	}
}

void i_wanna_be_a_killer(int v, int log_n) {
	is_dead[v] = true;
}

int nearest_undead(int a) {
	if (is_dead[a]) {
		return nearest_undead(parents[a]);
	}
	return a;
}

int get_lca(int a, int b, int log_n) {
	if (depth[a] != depth[b]) {
		if (depth[a] < depth[b]) {
			swap(a, b);
		}
		int log = log_n;
		while (depth[a] != depth[b]) {
			if (depth[dp[a][log]] >= depth[b]) {
				a = dp[a][log];
			}
			log--;
		}
	}
	if (a == b) {
		return a;
	}
	int log = log_n;
	while (log >= 0) {
		if (dp[a][log] != dp[b][log]) {
			a = dp[a][log];
			b = dp[b][log];
		}
		log--;
	}
	return dp[a][0];
}

int lca(int a, int b, int log_n) {
	int lca = get_lca(a, b, log_n - 1);
	if (is_dead[lca]) {
		return parents[lca] = nearest_undead(parents[lca]);
	}
	return lca;
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
	int log_n = (int) (ceil(log((double) (n)) / log(2)));
	dp.resize(n + 1, vector <int>(log_n));
	for (int i = 0; i < log_n; ++i) {
		dp[1][i] = 1;
	}
	depth.resize(n + 1);
	parents.resize(n + 1);
	is_dead.resize(n + 1);
	char ch;
	int x, y;
	int count = 2;
	for (int i = 0; i < n; ++i) {
		cin >> ch;
		if (ch == '+') {
			cin >> x;
			i_wanna_be_a_dad(count, x, log_n);
			count++;
		} else {
			if (ch == '-') {
				cin >> x;
				i_wanna_be_a_killer(x, log_n);
			} else {
				cin >> x >> y;
				cout << lca(x, y, log_n) << '\n';
			}
		}
	}
	return 0;
}