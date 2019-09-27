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
#define INF (int)2e9;
#define MOD (int)1e9+7;

//////////////////////////////////////////////////////////////////
// SYSTEM STARTS

//////////////////////////////////////////////////////////////////
// UTILS

/////////////////////////////////////////////////////////////////
// MAIN

const int MAXN = 100001;

int count_hits;
int sizer[MAXN], h[MAXN], hmax[MAXN], inn[MAXN], ult[MAXN], ini[MAXN], s[MAXN], nshcch, sen[MAXN];

vector<int> g[MAXN];

struct HLDS {
	int lz, s;
}   tree[4 * MAXN];

void dfs(int v) {
	sizer[v] = 1;
	if (sen[v] != -1)
		h[v] = h[sen[v]] + 1;
	for (int i = 0; i < g[v].size(); i++) {
		int nxt = g[v][i];
		dfs(nxt);
		sizer[v] += sizer[nxt];
	}
}

void update(int idx, int ll, int rr, int l, int r, int op) {
	if (l > r || rr < l) return;
	if (tree[idx].lz != 0) {
		if (ll != rr) {
			int op = tree[idx].lz;
			int m = (ll + rr) / 2;
			tree[idx * 2].lz = op;
			tree[idx * 2 + 1].lz = op;
		}
		tree[idx].lz = 0;
	}
	if (ll >= l && rr <= r) {
		tree[idx].s = (rr - ll + 1) * (op - 1);
		tree[idx].lz = op;
		return;
	}
	int m = (ll + rr) / 2;
	update(idx * 2, ll, m, l, r, op);
	update(idx * 2 + 1, m + 1, rr, l, r, op);
	tree[idx].s = tree[idx * 2].s + tree[idx * 2 + 1].s;
}

void HLD(int v) {
	s[count_hits] = v;
	if (ini[nshcch] == -1) ini[nshcch] = count_hits;
	count_hits++;
	inn[v] = nshcch;
	int maxi = -1;
	int imaxi = 0;
	for (int nxt : g[v]) {
		if (maxi <= sizer[nxt]) {
			maxi = sizer[nxt];
			imaxi = nxt;
		}
	}
	if (maxi != -1)
		HLD(imaxi);
	for (int nxt : g[v]) {
		if (nxt == imaxi) continue;
		nshcch++;
		HLD(nxt);
	}
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
	for (int i = 0; i < MAXN; i++) ini[i] = -1;
	int raiz = 0;
	for (int a = 1; a <= n; a++) {
		cin >> sen[a];
		if (sen[a] == -1)
			raiz = a;
		else
			g[sen[a]].push_back(a);
	}
	for (int i = 0; i < MAXN; i++) ult[i] = -1;
	count_hits = 1;
	dfs(raiz);
	HLD(raiz);
	update(1, 1, count_hits - 1, 1, count_hits - 1, 2);
	int m;
	cin >> m;
	for (int a = 0; a < m; a++) {
		int res = 0;
		int t;
		cin >> t;
		for (int tt = 1; tt <= t; tt++) {
			int v;
			cin >> v;
			while (v != -1) {
				int cab = s[ini[inn[v]]];
				if (ult[cab] < a)
					hmax[cab] = h[cab];
				ult[cab] = a;
				res += max(0, h[v] - hmax[cab] + 1);
				hmax[cab] = max(hmax[cab], h[v] + 1);
				v = sen[cab];
			}
		}
		cout << res << endl;
	}
	return 0;
}