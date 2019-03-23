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

#define konec ending();
#define kill killer();
const int MAXN = 5e5;
ll t_sum[4 * MAXN];
int n, m;
vector <ll> a;

void build(int v, int tl, int tr) {
	if (tl == tr) {
		t_sum[v] = a[tl];
	} else {
		int tm = (tl + tr) / 2;
		build(v * 2, tl, tm);
		build(v * 2 + 1, tm + 1, tr);
		t_sum[v] = t_sum[v * 2] + t_sum[v * 2 + 1];
	}
}

ll get_sum(int v, int tl, int tr, int l, int r) {
	if (l > r) return 0;
	if (tl == l && tr == r) return t_sum[v];
	int tm = (tl + tr) / 2;
	return get_sum(v * 2, tl, tm, l, min(r, tm)) + get_sum(v * 2 + 1, tm + 1, tr, max(l, tm + 1), r);
}

void update(int v, int tl, int tr, int pos, ll new_val) {
	if (tl == tr) {
		t_sum[v] = new_val;
	} else {
		int tm = (tl + tr) / 2;
		if (pos <= tm)
			update(v * 2, tl, tm, pos, new_val);
		else
			update(v * 2 + 1, tm + 1, tr, pos, new_val);
		t_sum[v] = t_sum[v * 2] + t_sum[v * 2 + 1];
	}
}

void read() {
	cin >> n;
	a.resize(n, 0);
	for (int i = 0; i < n; i++) cin >> a[i];
	build(1, 0, n - 1);
}

void questions() {
	int l, r, x;
	string comm;
	while (cin >> comm) {
		cin >> l >> r;
		if (comm == "set") {
			update(1, 0, n - 1, l - 1, r);
		} else {
			cout << get_sum(1, 0, n - 1, l - 1, r - 1) << '\n';
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