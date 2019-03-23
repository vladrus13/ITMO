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

const int MAXN = 130;

ll t[MAXN][MAXN][MAXN];

int n, m;

void read() {
	cin >> n;
}

int get(int x, int y, int z) {
	int w = y, c = z;
	ll ans = 0;
	while (x >= 0) {
		while (w >= 0) {
			while (c >= 0) {
				ans += t[x][w][c];
				c = (c & (c + 1)) - 1;
			}
			c = z;
			w = (w & (w + 1)) - 1;
		}
		w = y;
		x = (x & (x + 1)) - 1;
	}
	return ans;
}

void add(int x, int y, int z, int k) {
	int w = y, c = z;
	while (x <= n) {
		while (w <= n) {
			while (c <= n) {
				t[x][w][c] += k;
				c = (c | (c + 1));
			}
			c = z;
			w = (w | (w + 1));
		}
		w = y;
		x = (x | (x + 1));
	}
}

void questions() {
	int x1, x2, y1, y2, z1, z2;
	int k, comm, ans;
	while (true) {
		cin >> comm;
		if (comm == 1) {
			cin >> x1 >> y1 >> z1 >> k;
			add(x1, y1, z1, k);
		}
		if (comm == 2) {
			ans = 0;
			cin >> x1 >> y1 >> z1 >> x2 >> y2 >> z2;
			ans += get(x2 + 0, y2 + 0, z2 + 0);
			ans -= get(x1 - 1, y2 + 0, z2 + 0);
			ans -= get(x2 + 0, y1 - 1, z2 + 0);
			ans -= get(x2 + 0, y2 + 0, z1 - 1);
			ans += get(x1 - 1, y1 - 1, z2 + 0);
			ans += get(x1 - 1, y2 + 0, z1 - 1);
			ans += get(x2 + 0, y1 - 1, z1 - 1);
			ans -= get(x1 - 1, y1 - 1, z1 - 1);
			cout << ans << endl;
		}
		if (comm == 3) {
			exit(0);
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
	questions();
	return 0;
}