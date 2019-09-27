#define _CRT_SECURE_NO_WARNINGS
#include <iostream>
#include <string>
#include <vector>
#include <map>
#include <algorithm>
#include <cmath>
#include <set>
#include <iomanip>

using namespace std;

typedef long long ll;
typedef long double ld;

vector <int> a;

int kth(int l, int r, int k) {
	int m = a[(l + r) / 2];
	int i = l, j = r;
	while (i <= j) {
		while (a[i] < m) i++;
		while (a[j] > m) j--;
		if (i <= j) {
			swap(a[i], a[j]);
			i++;
			j--;
		}
	}
	if (l <= k && k <= j) return kth(l, j, k);
	if (i <= k && k <= r) return kth(i, r, k);
	return a[k];
}

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("kth.in", "r", stdin);
	freopen("kth.out", "w", stdout);
#endif
	int n, k;
	cin >> n >> k;
	k--;
	a.resize(n);
	int A, B, C;
	cin >> A >> B >> C >> a[0] >> a[1];
	for (int i = 2; i < n; i++) a[i] = A * a[i - 2] + B * a[i - 1] + C;
	cout << kth(0, n - 1, k);
	return 0;
}
