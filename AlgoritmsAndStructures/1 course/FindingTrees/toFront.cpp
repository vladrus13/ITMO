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

const int UC = (int) 1e9 + 1e8;

//////////////////////////////////////////////////////////////////
// SYSTEM STARTS

class treap {
public:
	treap *l, *r, *p;
	int prior, value, cnt;
	treap(int x)
		:l(NULL), r(NULL), p(NULL), value(x), cnt(1), prior(rand() % (int) (1e9 + 457)) {}
	treap(int x, treap *p)
		:l(NULL), r(NULL), p(p), value(x), cnt(1), prior(rand() % (int) (1e9 + 436)) {}
};

int cnt(treap *t) {
	return t ? t->cnt : 0;
}
void upd(treap * t) {
	if (t) {
		t->cnt = cnt(t->l) + cnt(t->r) + 1;
	}
}

pair <treap*, treap*> split(treap *t, int k, int add) {
	if (t == NULL) {
		return { NULL, NULL };
	}
	int current_cnt = add + cnt(t->l);
	if (k > current_cnt) {
		pair <treap*, treap*> temp = split(t->r, k, add + 1 + cnt(t->l));
		t->r = temp.first;
		upd(t->r);
		upd(temp.second);
		upd(t);
		return { t, temp.second };
	} else {
		pair <treap*, treap*> temp = split(t->l, k, add);
		t->l = temp.second;
		upd(t->l);
		upd(temp.first);
		upd(t);
		return { temp.first, t };
	}
}

treap* merge(treap *t1, treap *t2) {
	if (t1 == NULL) {
		return t2;
	}
	if (t2 == NULL) {
		return t1;
	}
	if (t1->prior > t2->prior) {
		t1->r = merge(t1->r, t2);
		upd(t2);
		upd(t1->r);
		upd(t1);
		return t1;
	} else {
		t2->l = merge(t1, t2->l);
		upd(t1);
		upd(t2->l);
		upd(t2);
		return t2;
	}
}

treap * move_to_front(treap * t, int l, int r) {
	pair <treap *, treap *> temp = split(t, r + 1, 0);
	pair <treap *, treap *> temp2 = split(temp.first, l, 0);
	treap * temp_t = merge(temp2.first, temp.second);
	t = merge(temp2.second, temp_t);
	return t;
}

//////////////////////////////////////////////////////////////////

/////////////////////////////////////////////////////////////////
// MAIN

void rec(treap *t) {
	if (t) {
		rec(t->l);
		cout << t->value << ' ';
		rec(t->r);
	}
	
}

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	// freopen("markchain.in", "r", stdin);
	// freopen("markchain.out", "w", stdout);
#endif
	ios_base::sync_with_stdio(NULL);
	cin.tie(NULL);
	cout.tie(NULL);
	int n, m;
	cin >> n >> m;
	treap * t = NULL;
	for (int i = 0; i < n; i++) {
		t = merge(t, new treap(i + 1));
	}
	int l, r;
	for (int i = 0; i < m; i++) {
		cin >> l >> r;
		l--, r--;
		t = move_to_front(t, l, r);
	}
	rec(t);
	return 0;
}
