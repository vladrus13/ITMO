#define _CRT_SECURE_NO_WARNINGS
#include <iostream>
#include <algorithm>

using namespace std;

typedef long long ll;
typedef long double ld;
typedef unsigned int ui;
typedef unsigned long long ull;

const int MAXN = 10000000;

ll sparse[MAXN + 1];
int a[MAXN];
int n;

int question(int l, int r) {
	return sparse[r + 1] - sparse[l];
}

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#endif
	ios_base::sync_with_stdio(NULL);
	int x, y;
	cin >> n >> x >> y >> a[0];
	sparse[0] = 0;
	for (int i = 1; i < n; i++) {
		a[i] = (x * a[i - 1] + y) % 65536;
		sparse[i] = sparse[i - 1] + a[i - 1];
	}
	sparse[n] = sparse[n - 1] + a[n - 1];
	int m, z, t;
	ll blast, bcur;
	int clast, ccur;
	cin >> m >> z >> t >> blast;
	clast = blast % n;
	ll sum = 0;
	for (int i = 1; i < 2 * m; i++) {
		bcur = (z * blast + t) % 1073741824;
		ccur = bcur % n;
		if (i % 2 == 1) {
			sum += sparse[max(ccur, clast) + 1] - sparse[min(ccur, clast)];
		}
		blast = bcur;
		clast = ccur;
	}
	cout << sum;
	return 0;
}