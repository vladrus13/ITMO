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
// #define INF (int)2e9;

const int INF = (int) 2e9;

//////////////////////////////////////////////////////////////////
// SYSTEM STARTS

struct vertex {
	vertex *l, *r, *p;
	int update, m, lp, rp, rigth;
	vertex()
		: m(0), update(0), lp(0), rp(0), rigth(0), l(NULL), r(NULL) {}
	/*
vertex(int xxx)
	: m(0), update(0), lp(xxx), rp(xxx), first(0), rigth(0), l(NULL), r(NULL) {}
vertex(int x, int y)
	: m(0), update(0), lp(x), rp(y), first(0), rigth(0), l(NULL), r(NULL) {}
vertex(vertex *left, vertex *rigth)
	: update(0), m(max(left ? left->m : 0, rigth ? rigth->m : 0)), l(left), r(rigth), first(left ? left->first : (rigth ? rigth->first : -1)), rigth(rigth ? rigth->rigth : (left ? left->rigth : -1)), lp(left ? left->lp : (rigth ? rigth->lp : -1)), rp(rigth ? rigth->rp : (left ? left->rp : -1)) {
	rigth->p = this;
	left->p = this;
}*/
};

int root = 1;

struct events {
	int ev; // 0 - update, 1 - find_h
	int a, b, d, h;
};

const int MAXN = (int) 4e5;

//////////////////////////////////////////////////////////////////

void build(vertex *&v) {
	if (!v) {
		v = new vertex();
		v->p = NULL;
		v->rp = root;
	}
	if ((v->rp - v->lp < 2) || (v->l)) return;
	v->l = new vertex();
	v->r = new vertex();
	v->l->p = v->r->p = v;
	v->l->lp = v->lp;
	v->l->rp = (v->lp + v->rp) / 2;
	v->r->lp = (v->lp + v->rp) / 2;
	v->r->rp = v->rp;
	v->l->m = v->r->m = v->r->rigth = v->l->rigth = ((v->lp + v->rp) / 2 - v->lp) * v->update;
	v->l->update = v->r->update = v->update;
	v->update = -INF;
}

void kick(vertex *&v) {
	if (!(v->l) || (v->update == -INF)) {
		// v->update = 0;
	} else {
		// left
		v->l->update = v->update;
		// rigth
		v->r->update = v->update;
		v->update = -INF;
	}
}

pair <int, int> get(vertex *&v) {
	if (v->update == -INF) {
		return { v->m, v->rigth };
	}
	return { (v->rp - v->lp) * v->update, (v->rp - v->lp) * v->update };
}

void minikick(vertex *&v) {
	pair <int, int> l = get(v->l);
	pair <int, int> r = get(v->r);
	v->m = max(l.first, (l.second + r.first));
	v->rigth = l.second + r.second;
}

void update(vertex *&v, int l, int r, int value) {
	if (v->lp >= r || v->rp <= l) return;
	if (v->lp >= l && v->rp <= r) {
		v->update = value;
		return;
	}
	if (!(v->l)) build(v);
	kick(v);
	update(v->l, l, r, value);
	update(v->r, l, r, value);
	minikick(v);
}

int find_h(vertex *&v, int h) {
	pair <int, int> temp = get(v);
	if (!(v->p) && temp.first <= h) return root + 1;
	if (v->rp - v->lp == 1) {
		return v->lp;
	}
	if (!(v->l)) build(v);
	kick(v);
	minikick(v);
	temp = get(v->l);
	if (temp.first > h) return find_h(v->l, h);
	pair <int, int> temp2 = get(v->r);
	return find_h(v->r, h - temp.second);
}

/////////////////////////////////////////////////////////////////
// MAIN

vertex * t = NULL;

void read() {
	int n;
	cin >> n;
	while (n > root) {
		root *= 2;
	}
	build(t);
	char comm = 'W';
	int l, r, x;
	events temp;
	while (comm != 'E') {
		cin >> comm;
		if (comm == 'I') {
			cin >> l >> r >> x;
			update(t, l - 1, r, x);
		}
		if (comm == 'Q') {
			cin >> x;
			cout << min(n, find_h(t, x)) << '\n';
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
	// question();
	return 0;
}