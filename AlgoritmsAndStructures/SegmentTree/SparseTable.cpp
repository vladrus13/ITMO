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
	int count, sum, v, length;
	bool left, rigth, set, setb;
	void setv(bool x) {
		left = x;
		rigth = x;
	}
	void deset() {
		set = 0;
		setb = 0;
	}
};

#define konec ending();
#define kill killer();

const int MAXN = 100002;

ll sparse[MAXN][30], a[MAXN];
ll n;

int get_log(int k) {
	int i = 0, s = 1;
	while (s <= k) {
		i++;
		s *= 2;
	}
	return i - 1;
}

void firstly() {
	for (int i = 1; i <= n; i++)
		sparse[i][0] = a[i];
	for (int j = 1; j < 30; j++)
		for (int i = 1; i <= MAXN; i++)
			if (i + (1 << (j - 1)) > -1 && i + (1 << (j - 1)) < MAXN)
				sparse[i][j] = min(sparse[i][j - 1], sparse[i + (1 << (j - 1))][j - 1]);
}

ll question(int l, int r) {
	ll k = get_log(r - l + 1);
	return min(sparse[l][k], sparse[r - (1 << k) + 1][k]);
}

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#endif
	ios_base::sync_with_stdio(NULL);
	cin.tie(NULL);
	cout.tie(NULL);
	ll m, a1, u1, v1, u2, v2, ans1, ans2;
	cin >> n >> m >> a1 >> u1 >> v1;
	a[1] = a1;
	for (int i = 2; i <= n; i++) a[i] = (23 * a[i - 1] + 21563) % 16714589;
	firstly();
	ans1 = question(min(u1, v1), max(u1, v1));
	for (int i = 1; i < m; ++i) {
		u2 = ((17 * u1 + 751 + ans1 + 2 * i) % n) + 1;
		v2 = ((13 * v1 + 593 + ans1 + 5 * i) % n) + 1;
		ans2 = question(min(u2, v2), max(u2, v2));
		u1 = u2, v1 = v2, ans1 = ans2;
	}
	cout << u1 << ' ' << v1 << ' ' << ans1 << '\n';
	return 0;
}