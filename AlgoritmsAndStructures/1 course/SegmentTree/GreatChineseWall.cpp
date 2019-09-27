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

struct vertex {
	int min, posmin, update;
};

const int MAXN = 1e6 + 3;

vertex t[3 * MAXN];

void build(int v, int tl, int tr) {
	if (tl == tr) {
		t[v].posmin = tl;
	} else {
		int tm = (tl + tr) / 2;
		build(2 * v, tl, tm);
		build(2 * v + 1, tm + 1, tr);
		t[v].posmin = tl;
	}
}

void kick(int v, int tl, int tr) {
	if (tl != tr && t[v].update) {
		t[v * 2].min = max(t[v * 2].min, t[v].min);
		t[v * 2 + 1].min = max(t[v * 2 + 1].min, t[v].min);
		t[v * 2].update = true;
		t[v * 2 + 1].update = true;
		t[v].update = false;
	}
}

void update(int v, int tl, int tr, int l, int r, int value) {
	if (tl == l && tr == r) {
		t[v].min = max(t[v].min, value);
		t[v].update = true;
		return;
	}
	int tm = (tl + tr) / 2;
	if (l <= tm) {
		kick(v * 2, tl, tm);
		update(v * 2, tl, tm, l, min(tm, r), value);
	}
	if (r > tm) {
		kick(v * 2 + 1, tm + 1, tr);
		update(v * 2 + 1, tm + 1, tr, max(l, tm + 1), r, value);
	}
	if (t[v * 2].min <= t[v * 2 + 1].min) {
		t[v].min = t[v * 2].min;
		t[v].posmin = t[v * 2].posmin;
	} else {
		t[v].min = t[v * 2 + 1].min;
		t[v].posmin = t[v * 2 + 1].posmin;
	}
}

pair <int, int> get(int v, int tl, int tr, int l, int r) {
	if (tl == l && tr == r) {
		return { t[v].min, t[v].posmin };
	}
	int tm = (tl + tr) / 2;
	pair <int, int> left = { 1e8, 1e8 }, rigth = { 1e8, 1e8 };
	if (l <= tm) {
		kick(v * 2, tl, tm);
		left = get(v * 2, tl, tm, l, min(tm, r));
	}
	if (r > tm) {
		kick(v * 2 + 1, tm + 1, tr);
		rigth = get(v * 2 + 1, tm + 1, tr, max(tm + 1, l), r);
	}
	if (left.first <= rigth.first) {
		return left;
	} else {
		return rigth;
	}
}

int n, m;

void read() {
	cin >> n >> m;
	build(1, 0, n - 1);
}

void questions() {
	string s;
	int l, r, x;
	pair <int, int> ans;
	while (cin >> s >> l >> r) {
		l--, r--;
		if (s[0] == 'a') {
			kick(1, 0, n - 1);
			ans = get(1, 0, n - 1, l, r);
			cout << ans.first << ' ' << ans.second + 1 << endl;
		} else {
			cin >> x;
			kick(1, 0, n - 1);
			update(1, 0, n - 1, l, r, x);
		}
	}
}

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#endif
	ios_base::sync_with_stdio(NULL);
	cin.tie(NULL);
	cout.tie(NULL);
	read();
	questions();
	return 0;
}