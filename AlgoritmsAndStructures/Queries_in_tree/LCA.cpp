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

int n, m;
int p, q;

struct Node {
	int L, R, P;
	int PP;
};

Node LCT[230000];

inline void make_tree(int v) {
	if (v == -1) return;
	LCT[v].L = LCT[v].R = LCT[v].P = LCT[v].PP = -1;
}

inline void rotate(int v) {
	if (v == -1) return;
	if (LCT[v].P == -1) return;
	int p = LCT[v].P;
	int g = LCT[p].P;
	if (LCT[p].L == v) {
		LCT[p].L = LCT[v].R;
		if (LCT[v].R != -1) {
			LCT[LCT[v].R].P = p;
		}
		LCT[v].R = p;
		LCT[p].P = v;
	} else {
		LCT[p].R = LCT[v].L;
		if (LCT[v].L != -1) {
			LCT[LCT[v].L].P = p;
		}
		LCT[v].L = p;
		LCT[p].P = v;
	}
	LCT[v].P = g;
	if (g != -1) {
		if (LCT[g].L == p) {
			LCT[g].L = v;
		} else {
			LCT[g].R = v;
		}
	}
	LCT[v].PP = LCT[p].PP;
	LCT[p].PP = -1;
}

inline void splay(int v) {
	if (v == -1) return;
	while (LCT[v].P != -1) {
		int p = LCT[v].P;
		int g = LCT[p].P;
		if (g == -1) {
			rotate(v);
		} else if ((LCT[p].L == v) == (LCT[g].L == p)) {
			rotate(p);
			rotate(v);
		} else {
			rotate(v);
			rotate(v);
		}
	}
}

inline void expose(int v) {
	if (v == -1) return;
	splay(v);
	if (LCT[v].R != -1) {
		LCT[LCT[v].R].PP = v;
		LCT[LCT[v].R].P = -1;
		LCT[v].R = -1;
	}
	while (LCT[v].PP != -1) {
		int w = LCT[v].PP;
		splay(w);
		if (LCT[w].R != -1) {
			LCT[LCT[w].R].PP = w;
			LCT[LCT[w].R].P = -1;
		}
		LCT[w].R = v;
		LCT[v].P = w;
		splay(v);
	}
}

inline int find_root(int v) {
	if (v == -1) return -1;
	expose(v);
	int ret = v;
	while (LCT[ret].L != -1) ret = LCT[ret].L;
	expose(ret);
	return ret;
}

inline void link(int v, int w) {
	if (v == -1 || w == -1) return;
	expose(w);
	LCT[v].L = w;
	LCT[w].P = v;
	LCT[w].PP = -1;
}

inline void cut(int v) {
	if (v == -1) return;
	expose(v);
	if (LCT[v].L != -1) {
		LCT[LCT[v].L].P = -1;
		LCT[LCT[v].L].PP = -1;
		LCT[v].L = -1;
	}
}

inline int LCA(int p, int q) {
	expose(p);
	splay(q);
	if (LCT[q].R != -1) {
		LCT[LCT[q].R].PP = q;
		LCT[LCT[q].R].P = -1;
		LCT[q].R = -1;
	}

	int ret = q, t = q;
	while (LCT[t].PP != -1) {
		int w = LCT[t].PP;
		splay(w);
		if (LCT[w].PP == -1) ret = w;
		if (LCT[w].R != -1) {
			LCT[LCT[w].R].PP = w;
			LCT[LCT[w].R].P = -1;
		}
		LCT[w].R = t;
		LCT[t].P = w;
		LCT[t].PP = -1;
		t = w;
	}
	splay(q);

	return ret;
}
//////////////////////////////////////////////////////////////////
// UTILS

/////////////////////////////////////////////////////////////////
// MAIN

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
	int n, x;
	cin >> n;
	for (int i = 1; i <= n; i++) make_tree(i);
	for (int i = 2; i <= n; i++) {
		cin >> x;
		link(i, x);
	}
	cin >> n;
	int y;
	for (int i = 0; i < n; i++) {
		cin >> x >> y;
		cout << LCA(x, y) << endl;
	}
	return 0;
}