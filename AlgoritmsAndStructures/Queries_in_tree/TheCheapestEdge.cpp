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

vector <int> depth, pow_2;
vector <vector <int> > dp, cost;

int get_min(int a, int lca, int log_n, int n) {
	int ans = INF;
	int delta = depth[a] - depth[lca];
	int log = log_n;
	while (delta != 0) {
		if (pow_2[log] <= delta) {
			delta -= pow_2[log];
			ans = min(ans, cost[a][log]);
			a = dp[a][log];
		}
		log--;
	}
	return ans;
}

int get_lca(int a, int b, int log_n, int n) {
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

int main() {
	// begin of my useless prog
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("minonpath.in", "r", stdin);
	freopen("minonpath.out", "w", stdout);
#endif
	ios_base::sync_with_stdio(NULL);
	cin.tie(NULL);
	cout.tie(NULL);
	int n;
	cin >> n;
	int log_n = (int) (ceil(log((double) (n)) / log(2)));
	pow_2.resize(log_n);
	if (n != 1) pow_2[0] = 1;
	for (int i = 1; i < log_n; ++i) {
		pow_2[i] = pow_2[i - 1] * 2;
	}
	dp.resize(n + 1, vector <int>(log_n));
	cost.resize(n + 1, vector <int>(log_n));
	for (int i = 0; i < log_n; ++i) {
		dp[1][i] = 1;
		cost[1][i] = INF;
	}
	depth.resize(n + 1);
	depth[1] = 0;
	for (int i = 2; i <= n; ++i) {
		int tmp;
		int local_min;
		cin >> tmp >> local_min;
		dp[i][0] = tmp;
		cost[i][0] = local_min;
		for (int j = 1; j < log_n; ++j) {
			dp[i][j] = dp[dp[i][j - 1]][j - 1];
			cost[i][j] = min(cost[dp[i][j - 1]][j - 1], cost[i][j - 1]);
		}
		depth[i] = depth[tmp] + 1;
	}
	int m;
	cin >> m;
	for (int i = 0; i < m; ++i) {
		int a, b;
		cin >> a >> b;
		int lca = get_lca(a, b, log_n - 1, n);
		cout << min(get_min(a, lca, log_n - 1, n), get_min(b, lca, log_n - 1, n)) << endl;
	}
	return 0;
}