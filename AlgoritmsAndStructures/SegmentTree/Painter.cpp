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
	int count, sum;
	bool left, rigth, set, setb;
};

const int MAXN = 1e6 + 6;
vertex t[4 * MAXN];
int n;

void paint(int v, int tl, int tr, int l, int r, int color) {
	if (l > r || r < tl || l > tr) return;
	if (tl == l && tr == r) {
		t[v].left = color;
		t[v].rigth = color;
		t[v].count = color;
		t[v].sum = (tr - tl + 1) * color;
		t[v].setb = true;
		t[v].set = color;
		return;
	}
	if (tl != tr) {
		if (t[v].setb) {
			int value = (t[v].set == true ? 1 : 0);
			t[v * 2].count = value;
			t[v * 2].left = t[v].set;
			t[v * 2].rigth = t[v].set;
			t[v * 2 + 1].count = value;
			t[v * 2 + 1].left = t[v].set;
			t[v * 2 + 1].rigth = t[v].set;
			t[v * 2].setb = true;
			t[v * 2].set = t[v].set;
			t[v * 2 + 1].set = t[v].set;
			t[v * 2 + 1].setb = true;
			int temp = (tl + tr) / 2;
			t[v * 2].sum = (temp - tl + 1) * value;
			t[v * 2 + 1].sum = (tr - temp) * value;
			t[v].setb = false;
		}
	}
	int tm = (tl + tr) / 2;
	if (l <= tm) {
		paint(v * 2, tl, tm, l, min(tm, r), color);
	}
	if (r > tm) {
		paint(v * 2 + 1, tm + 1, tr, max(tm + 1, l), r, color);
	}
	t[v].left = t[v * 2].left;
	t[v].rigth = t[v * 2 + 1].rigth;
	t[v].count = t[v * 2].count + t[v * 2 + 1].count - (t[v * 2].rigth && t[v * 2 + 1].left ? 1 : 0);
	t[v].sum = t[v * 2].sum + t[v * 2 + 1].sum;
}

void read() {
	cin >> n;
}

void questions() {
	int l, r;
	char x;
	while (n--) {
		cin >> x >> l >> r;
		l += 500001;
		r = l + r - 1;
		paint(1, 0, 1e6 + 5, l, r, (x == 'B' ? 1 : 0));
		cout << t[1].count << ' ' << t[1].sum << '\n';
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