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

int r;

void ending() {
	exit(0);
}

void killer() {
	exit(1);
}

class matrix {
public:
	int a[2][2];
	void read_2_2() {
		scanf("%d%d%d%d", &a[0][0], &a[0][1], &a[1][0], &a[1][1]);
	}

	void write_2_2() {
		printf("%d %d\n%d %d\n\n", a[0][0], a[0][1], a[1][0], a[1][1]);
	}

	matrix multiply(matrix b) {
		matrix ans;
		ans.a[0][0] = 0;
		ans.a[0][1] = 0;
		ans.a[1][0] = 0;
		ans.a[1][1] = 0;

		for (int i = 0; i < (int) 2; i++) {
			for (int j = 0; j < (int) 2; j++) {
				for (int k = 0; k < (int) 2; k++) {
					ans.a[i][j] += a[i][k] * b.a[k][j];
					if (ans.a[i][j] >= r) ans.a[i][j] %= r;
					// mayb to one task
				}
			}
		}
		return ans;
	}
};

const int MAXN = 200001;

vector <matrix> a;
matrix t[3 * MAXN];

void build(int v, int tl, int tr) {
	if (tl == tr) {
		t[v].a[0][0] = a[tl].a[0][0];
		t[v].a[0][1] = a[tl].a[0][1];
		t[v].a[1][0] = a[tl].a[1][0];
		t[v].a[1][1] = a[tl].a[1][1];
	} else {
		int tm = (tl + tr) / 2;
		build(v * 2, tl, tm);
		build(v * 2 + 1, tm + 1, tr);
		t[v] = t[v * 2].multiply(t[v * 2 + 1]);
	}
}

matrix get(int v, int tl, int tr, int l, int r) {
	if (l == tl && r == tr) return t[v];
	int tm = (tl + tr) / 2;
	matrix left, rigth;
	bool lefter = false, rigther = false;
	if (l <= tm) {
		lefter = true;
		left = get(v * 2, tl, tm, l, min(r, tm));
	}
	if (r > tm) {
		rigther = true;
		rigth = get(v * 2 + 1, tm + 1, tr, max(l, tm + 1), r);
	}
	if (lefter) {
		if (rigther) {
			return left.multiply(rigth);
		} else {
			return left;
		}
	} else {
		return rigth;
	}
}

int n, m;

void read() {
	cin >> r >> n >> m;
	a.resize(n);
	for (int i = 0; i < n; i++) {
		a[i].read_2_2();
	}
	build(1, 0, n - 1);
}

void questions() {
	int l, r;
	matrix temp;
	while (m--) {
		cin >> l >> r;
		l--, r--;
		temp = get(1, 0, n - 1, l, r);
		temp.write_2_2();
	}
}

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("crypto.in", "r", stdin);
	freopen("crypto.out", "w", stdout);
#endif
	read();
	questions();
	return 0;
}