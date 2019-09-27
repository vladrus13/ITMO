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

///////////////////////////////////////////////////////////////////
// System points

const int MAXN = 4e5 + 15;

struct events {
	int y1, y2, x, i;
	bool type;

	friend bool operator<(events a, events b) {
		if (a.x != b.x) return a.x < b.x;
		if (a.type != b.type) return a.type > b.type;
		if (a.y1 != b.y1) return a.y1 < b.y1;
		if (a.y2 != b.y2) return a.y2 < b.y2;
		return a.i > b.i;
	}
};

struct vertex {
	int max, update;
};

int n;
vertex t[10 * MAXN];
vector <events> ev;
int max_x = 0, max_y = 0, max_k = 0;

///////////////////////////////////////////////////////////////
// TREE

void check(int a, int b) {
	if (a >= b) {
		cout << "-1\n-1 -1\n";
		exit(0);
	}
}

void kick(int v, int tl, int tr) {
	if (tl == tr && t[v].update != 0) {
		t[v].max += t[v].update;
		t[v].update = 0;
	}
	if (tl != tr && t[v].update != 0) {
		t[v * 2].update += t[v].update;
		t[v * 2 + 1].update += t[v].update;
		t[v].max = max(t[v * 2].max + t[v * 2].update, t[v * 2 + 1].max + t[v * 2 + 1].update);
		t[v].update = 0;
	}
}

int get_max(int v, int tl, int tr) {
	if (tl == tr) {
		return tl;
	}
	int tm = (tl + tr) / 2;
	if (t[v * 2].max + t[v * 2].update >= t[v * 2 + 1].max + t[v * 2 + 1].update) {
		kick(v * 2, tl, tm);
		return get_max(v * 2, tl, tm);
	} else {
		kick(v * 2 + 1, tm + 1, tr);
		return get_max(v * 2 + 1, tm + 1, tr);
	}
}

void update(int v, int tl, int tr, int l, int r, int value) {
	if (tl == l && tr == r) {
		t[v].update += value;
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
	t[v].max = max(t[v * 2].max + t[v * 2].update, t[v * 2 + 1].max + t[v * 2 + 1].update);
}

//////////////////////////////////////////////////////////////
// scanline

void scanline() {
	stable_sort(ev.begin(), ev.end());
	int xlast = -1;
	for (int i = 0; i < 2 * n; i++) {
		kick(1, 0, MAXN - 1);
		update(1, 0, MAXN - 1, ev[i].y1, ev[i].y2, (ev[i].type ? 1 : -1));
		if (true)
			if (t[1].max > max_k) {
				max_k = t[1].max;
				max_x = ev[i].x;
				kick(1, 0, MAXN - 1);
				max_y = get_max(1, 0, MAXN - 1);
			}
		xlast = ev[i].x;
	}
}

/////////////////////////////////////////////////////////////
// Main

void read() {
	cin >> n;
	int x1, x2, y1, y2;
	ev.resize(2 * n);
	for (int i = 0; i < 2 * n; i += 2) {
		cin >> x1 >> y1 >> x2 >> y2;
		x1 += 2e5;
		x2 += 2e5;
		y1 += 2e5;
		y2 += 2e5;
		///////// ev1
		ev[i].x = x1;
		ev[i].y1 = y1;
		ev[i].y2 = y2;
		ev[i].type = true;
		ev[i].i = i;
		//////// ev2
		ev[i + 1].x = x2;
		ev[i + 1].y1 = y1;
		ev[i + 1].y2 = y2;
		ev[i + 1].type = false;
		ev[i + 1].i = i + 1;
	}
}

void write() {
	cout << max_k << endl << max_x - 2e5 << ' ' << max_y - 2e5;
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
	scanline();
	write();
	return 0;
}