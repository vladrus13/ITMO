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

//////////////////////////////////////////////////////////////////
// UTILS

/////////////////////////////////////////////////////////////////
// MAIN
const int MAXN = 131072;
const int INF = (int) 1e9;

vector<pair<int, int>> g, g2;
map<pair<int, int>, vector<int>> link, cut;
vector<pair<int, int>> tree[3 * MAXN];
int n, m;

int par[MAXN], tempar[MAXN], sizer[MAXN];

inline int get_par(int v) {
	return (par[v] == v) ? (v) : (get_par(par[v]));
}

struct Log {
	int first, second;
	bool wl;
	Log() {}
	Log(int first, int second, bool wl): first(first), second(second), wl(wl) {}
};

vector<Log> logger;

inline void apply_command(int a, int b) {
	int pa = get_par(a);
	int pb = get_par(b);

	bool flag = false;
	if (tempar[pa] == tempar[pb]) {
		tempar[pa]++;
		flag = true;
	}
	if (tempar[pa] > tempar[pb]) {
		logger.push_back(Log(pa, pb, flag));
		par[pb] = pa;
		sizer[pa] += sizer[pb];
	} else {
		logger.push_back(Log(pb, pa, flag));
		par[pa] = pb;
		sizer[pb] += sizer[pa];
	}
}

inline int get_size(int v) {
	return sizer[get_par(v)];
}

inline void backer() {
	Log c = logger.back();
	logger.pop_back();
	par[c.second] = c.second;
	sizer[c.first] -= sizer[c.second];
	if (c.wl) {
		--tempar[c.first];
	}
}

void add_log(int v, int vl, int vr, int l, int r, pair<int, int> e) {
	if (r < vl || vr < l) {
		return;
	}
	if (l <= vl && vr <= r) {
		tree[v].push_back(e);
		return;
	}
	int vm = (vl + vr) / 2;
	add_log(v * 2, vl, vm, l, r, e);
	add_log(v * 2 + 1, vm + 1, vr, l, r, e);
}

bool go(int v) {
	for (pair<int, int> e : tree[v]) {
		apply_command(e.first, e.second);
	}
	if (v >= MAXN) {
		if (v - MAXN >= (int) g.size()) {
			return false;
		}
		cout << (get_par(g[v - MAXN].second) == get_par(g2[v - MAXN].second) ? "1" : "0") << '\n';
	} else {
		if (!go(2 * v)) {
			return false;
		}
		if (!go(2 * v + 1)) {
			return false;
		}
	}

	int sz = tree[v].size();
	for (int i = 0; i < sz; ++i) {
		backer();
	}
	return true;
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
	cin >> n >> m;
	for (int i = 0; i < m; ++i) {
		string str;
		int u, v;
		cin >> str >> u;
		--u;
		if (str[1] == 'o') {
			cin >> v;
			v--;
			g.push_back({ i, u });
			g2.push_back({ i, v });
		}
		if (str[1] == 'i') {
			cin >> v;
			--v;
			link[{min(u, v), max(u, v)}].push_back(i);
		}
		if (str[1] == 'u') {
			cin >> v;
			--v;
			cut[{min(u, v), max(u, v)}].push_back(i);
		}
	}

	if (g.size() == 0) {
		return 0;
	}
	for (int i = 0; i < n; ++i) {
		par[i] = i;
		tempar[i] = 0;
		sizer[i] = 1;
	}
	for (auto& p : link) {
		vector<int> &l = p.second;
		vector<int> &c = cut[p.first];
		if (c.size() < l.size()) {
			c.push_back(m);
		}
		int sz = l.size();
		for (int i = 0; i < sz; ++i) {
			int posl = lower_bound(g.begin(), g.end(), make_pair(l[i], INF)) - g.begin();
			int posr = lower_bound(g.begin(), g.end(), make_pair(c[i], -INF)) - g.begin();
			--posr;
			if (posl > posr) {
				continue;
			}
			add_log(1, 0, MAXN - 1, posl, posr, p.first);
		}
	}
	go(1);
	return 0;
}