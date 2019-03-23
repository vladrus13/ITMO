#define _CRT_SECURE_NO_WARNINGS
#include <iostream>
#include <algorithm>
#include <vector>
#include <cmath>
#include <deque>
#include <string>
#include <vector>
#include <iostream>
#include <cmath>
#include <queue>
#include <iostream>
#include <map>
#include <string>
#include <vector>
#include <algorithm>
#include <set>
#include <iostream>
#include <vector>
#include <algorithm>
#include <iostream>
#include <string>
#include <vector>
#include <algorithm>
#include <queue>
#include <iostream>
#include <string>
#include <vector>
#include <algorithm>
#include <queue>
#include <iomanip>
#include <bitset>
#include <cassert>

typedef long double ld;
typedef long long ll;
typedef unsigned long long ull;

using namespace std;
//#pragma comment (linker, "/STACK:5000000000")
#define INF (int)1e9;

void ending() {
	exit(0);
}

void killer() {
	exit(1);
}

struct vertex {
	ll min, add, set;
	bool addb, setb;

	void addv(ll x) {
		if (setb) {
			set += x;
			min = min + x;
			addb = false;
			setb = true;
		} else {
			addb = true;
			add += x;
			min = min + x;
		}
	}

	void deadd() {
		add = 0;
		addb = false;
	}

	void deset() {
		set = 0;
		setb = false;
	}

	void setv(ll x) {
		add = 0;
		min = x;
		addb = false;
		setb = true;
		set = x;
	}
};

#define konec ending();
#define kill killer();
const int MAXN = 2e5;
vertex t[4 * MAXN];
int n, m;
vector <ll> a;

void build(int v, int tl, int tr) {
	if (tl == tr) {
		t[v].addv(a[tl]);
	} else {
		int tm = (tl + tr) / 2;
		build(v * 2, tl, tm);
		build(v * 2 + 1, tm + 1, tr);
		t[v].min = min(t[v * 2].min, t[v * 2 + 1].min);
	}
}

void kick(int v) {
	if (t[v].addb) {
		ll value = t[v].add;
		t[v].deadd();
		t[v * 2].addv(value);
		t[v * 2 + 1].addv(value);
	}
	if (t[v].setb) {
		ll value = t[v].set;
		t[v].deset();
		t[v * 2].setv(value);
		t[v * 2 + 1].setv(value);
	}
}

ll get_min(int v, int tl, int tr, int l, int r) {
	if (l > r || r < tl || l > tr) return 1e18;
	if (tl != tr) kick(v);
	if (tl == l && tr == r) return t[v].min;
	int tm = (tl + tr) / 2;
	ll left = 1e18, rigth = 1e18;
	if (l <= tm) {
		left = get_min(v * 2, tl, tm, l, min(tm, r));
	}
	if (r > tm) {
		rigth = get_min(v * 2 + 1, tm + 1, tr, max(tm + 1, l), r);
	}
	return min(left, rigth);
}

void set_(int v, int tl, int tr, int l, int r, ll new_val) {
	if (r < l) return;
	if (tl != tr) kick(v);
	if (l == tl && r == tr) {
		t[v].setv(new_val);
	} else {
		int tm = (tl + tr) / 2;
		if (l <= tm) {
			set_(v * 2, tl, tm, l, min(tm, r), new_val);
		}
		if (r > tm) {
			set_(v * 2 + 1, tm + 1, tr, max(tm + 1, l), r, new_val);
		}
		t[v].min = min(t[v * 2].min, t[v * 2 + 1].min);
	}
}

void add_(int v, int tl, int tr, int l, int r, ll new_val) {
	if (r < l || tl > r || tr < l) return;
	if (tl != tr) kick(v);
	if (l == tl && r == tr) {
		t[v].addv(new_val);
	} else {
		int tm = (tl + tr) / 2;
		if (l <= tm) {
			add_(v * 2, tl, tm, l, min(tm, r), new_val);
		}
		if (r > tm) {
			add_(v * 2 + 1, tm + 1, tr, max(tm + 1, l), r, new_val);
		}
		t[v].min = min(t[v * 2].min, t[v * 2 + 1].min);
	}
}

void read() {
	cin >> n;
	a.resize(n, 0);
	for (int i = 0; i < n; i++) cin >> a[i];
	build(1, 0, n - 1);
}

void questions() {
	ll l, r, x;
	string comm;
	while (cin >> comm) {
		cin >> l >> r;
		if (comm == "set") {
			cin >> x;
			set_(1, 0, n - 1, l - 1, r - 1, x);
		}
		if (comm == "add") {
			cin >> x;
			add_(1, 0, n - 1, l - 1, r - 1, x);
		}
		if (comm == "min") {
			cout << get_min(1, 0, n - 1, l - 1, r - 1) << '\n';
		}
	}
}

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#endif
	ios_base::sync_with_stdio(NULL);
	read();
	questions();
	return 0;
}