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
#include <random>

typedef long double ld;
typedef long long ll;
typedef unsigned long long ull;

using namespace std;
//#pragma comment (linker, "/STACK:5000000000")
// #define INF (int)2e9;

const int UC = (int) 1e9 + 1e8;

//////////////////////////////////////////////////////////////////
// SYSTEM STARTS

random_device rd;
mt19937 mersenne(rd());

class treap {
public:
	treap *l, *r, *p;
	int value, cnt, sum;
	ll prior;
	treap(int x)
		:l(NULL), r(NULL), p(NULL), value(x), cnt(1), prior(mersenne()), sum((x == 0 ? 1 : 0)) {}
	treap(int x, treap *p)
		:l(NULL), r(NULL), p(p), value(x), cnt(1), prior(mersenne()), sum((x == 0 ? 1 : 0)) {}
};

int cnt(treap *t) {
	return t ? t->cnt : 0;
}

int sum(treap *t) {
	return t ? t->sum : 0;
}
void upd(treap * t) {
	if (t) {
		t->cnt = cnt(t->l) + cnt(t->r) + 1;
		t->sum = sum(t->l) + sum(t->r) + (t->value == 0 ? 1 : 0);
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

treap * move_one(treap *t, int l, int r) {
	pair<treap*, treap*> temp = split(t, l, 0);
	pair<treap*, treap*> temp2 = split(temp.second, r - l + 1, 0);
	pair<treap*, treap*> temp3 = split(temp2.second, 1, 0);
	temp.first = merge(temp.first, new treap(0));
	temp.first = merge(temp.first, temp2.first);
	temp.first = merge(temp.first, temp3.second);
	delete temp3.first;
	return temp.first;
}

int first_null(treap *t) {
	if (t == NULL) {
		return 0;
	}
	if (sum(t->l)) {
		return first_null(t->l);
	}
	if (sum(t->l) + sum(t->r) != sum(t)) {
		return cnt(t->l);
	}
	if (sum(t->r)) {
		return first_null(t->r) + cnt(t->l) + 1;
	}
	return 0;
}

treap* build(int depth) {
	if (depth <= 0) return NULL;
	treap * returned = new treap(0);
	returned->l = build(depth / 2 + depth % 2 - 1);
	returned->r = build(depth / 2 - 1);
	upd(returned);
	return returned;
}

void sett(treap *t, int pos, int x, int add) {
	if (pos < add + cnt(t->l)) {
		sett(t->l, pos, x, add);
		upd(t);
		return;
	}
	if (pos == add + cnt(t->l)) {
		t->value = x;
		upd(t);
		return;
	}
	if (pos > add + cnt(t->l)) {
		sett(t->r, pos, x, add + 1 + cnt(t->l));
		upd(t);
		return;
	}
}

//////////////////////////////////////////////////////////////////

/////////////////////////////////////////////////////////////////
// MAIN

vector <int> check;

void writer(treap *t) {
	if (t) {
		writer(t->l);
		check.push_back(t->value);
		writer(t->r);
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
	int n, m, x;
	scanf("%d%d", &n, &m);
	treap *t = build(405275);
	pair <treap*, treap*> temp;
	int fn;
	for (int i = 0; i < n; i++) {
		scanf("%d", &x);
		x--;
		temp = split(t, x, 0);
		fn = first_null(temp.second) + x - 1;
		t = merge(temp.first, temp.second);
		t = move_one(t, x, fn);
		sett(t, x, i + 1, 0);
	}
	writer(t);
	int iter = check.size() - 1;
	while (check[iter] == 0) {
		iter--;
	}
	printf("%d\n", iter + 1);
	// cout << iter + 1 << endl;
	for (int i = 0; i < iter + 1; i++) {
		printf("%d ", check[i]);
	}
	return 0;
}
