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
	int sum;
};

const int MAXN = 1e5 + 3;

int t[3 * MAXN];

int update(int v, int tl, int tr, int l, int r) {
	if (tl == tr) {
		if (tr - tl + 1 == t[v]) {
			return -1;
		} else {
			t[v] = 1;
			return tl;
		}
	}
	int tm = (tl + tr) / 2, ans = -1;
	if (l <= tm && (tm - tl + 1 != t[v * 2])) {
		ans = update(v * 2, tl, tm, l, min(r, tm));
	}
	if (r > tm && ans == -1 && (tr - (tm + 1) + 1 != t[v * 2 + 1])) {
		ans = update(v * 2 + 1, tm + 1, tr, max(l, tm + 1), r);
	}
	t[v] = t[v * 2] + t[v * 2 + 1];
	return ans;
}

void delet(int v, int tl, int tr, int pos) {
	if (tl == tr) {
		t[v] = 0;
		return;
	}
	int tm = (tl + tr) / 2;
	if (pos <= tm) {
		delet(v * 2, tl, tm, pos);
	} else {
		delet(v * 2 + 1, tm + 1, tr, pos);
	}
	t[v] = t[v * 2] + t[v * 2 + 1];
}

int n, m;

void read() {
	cin >> n >> m;
}

void questions() {
	string comm;
	int x;
	while (m--) {
		cin >> comm >> x;
		x--;
		if (comm[1] == 'n') {
			int ans = update(1, 0, n - 1, x, n - 1);
			if (ans == -1) {
				cout << update(1, 0, n - 1, 0, x - 1) + 1;
			} else {
				cout << ans + 1;
			}
			cout << endl;
		} else {
			delet(1, 0, n - 1, x);
		}
	}
}

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("parking.in", "r", stdin);
	freopen("parking.out", "w", stdout);
#endif
	ios_base::sync_with_stdio(NULL);
	cin.tie(NULL);
	cout.tie(NULL);
	read();
	questions();
	return 0;
}