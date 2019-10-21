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
	int x, cnt, y;
	ll sum;
	treap(int x)
		:l(NULL), r(NULL), p(NULL), x(x), cnt(1), sum(x), y(rand() % (int) (1e9 + 457)) {}
	treap(int x, treap *p)
		:l(NULL), r(NULL), p(p), x(x), cnt(1), sum(x), y(rand() % (int) (1e9 + 436)) {}
};

int cnt(treap *t) {
	return t ? t->cnt : 0;
}

ll sum(treap *t) {
	return t ? t->sum : 0;
}

void upd(treap * t) {
	if (t) {
		t->cnt = cnt(t->l) + cnt(t->r) + 1;
		t->sum = sum(t->l) + sum(t->r) + t->x;
	}
}

bool exist(treap *t, int k) {
	if (t == NULL) {
		return false;
	}
	if (t->x == k) {
		return true;
	}
	if (t->x > k) {
		return exist(t->l, k);
	}
	if (t->x < k) {
		return exist(t->r, k);
	}
}

pair <treap*, treap*> split(treap *t, int k, int add) {
	if (t == NULL) {
		return { NULL, NULL };
	}
	int current_cnt = add + cnt(t->l);
	if (k > t->x) {
		pair <treap*, treap*> temp = split(t->r, k, add);
		t->r = temp.first;
		upd(t->r);
		upd(temp.second);
		upd(t);
		return { t, temp.second };
	} else {
		pair <treap*, treap*> temp = split(t->l, k, add + 1 + cnt(t->l));
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
	if (t1->y > t2->y) {
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

treap* insert(treap *t, int x) {
	pair <treap*, treap*> temp = split(t, x, 0);
	temp.first = merge(temp.first, new treap(x));
	return merge(temp.first, temp.second);
}

treap* remove(treap *t, int x) {
	pair <treap*, treap*> temp = split(t, x, 0);
	pair <treap*, treap*> temp2 = split(temp.second, x + 1, 0);
	return merge(temp.first, temp2.second);
}

ll get_sum(treap *t, int l, int r) {
	pair <treap*, treap*> temp = split(t, l, 0);
	pair <treap*, treap*> temp2 = split(temp.second, r + 1, 0);
	ll ans = (temp2.first ? temp2.first->sum : 0);
	treap* temp3 = merge(temp2.first, temp2.second);
	t = merge(temp.first, temp3);
	return ans;
}

//////////////////////////////////////////////////////////////////

/////////////////////////////////////////////////////////////////
// MAIN

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
	int n;
	char x;
	ll l, r, y;
	ll ans = 0;
	treap *t = NULL;
	cin >> n;
	for (int i = 0; i < n; i++) {
		cin >> x;
		if (x == '+') {
			cin >> y;
			if (!exist(t, (y + ans) % (int) 1e9)) {
				t = insert(t, (y + ans) % (int) 1e9);
			}
			ans = 0;
		}
		if (x == '?') {
			cin >> l >> r;
			ans = get_sum(t, l, r);
			cout << ans << endl;
		}
	}
	return 0;
}
