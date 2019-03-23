#define _CRT_SECURE_NO_WARNINGS
#include <iostream>
#include <string>
#include <vector>
#include <map>
#include <algorithm>
#include <cmath>
#include <set>
#include <iomanip>
#include <cassert>
#include <fstream>
#include <bitset>

using namespace std;

typedef long long ll;
typedef long double ld;
typedef unsigned int ui;
typedef unsigned long long ull;

ll n, k;
vector <int> a;

ll c(int n, int k) {
	if (k == 0) return 1;
	if (n == k) return 1;
	return c(n - 1, k) + c(n - 1, k - 1);
}

ll rec(int m) {
	ll returned = 0;
	if (m == k + 1) return 0;
	for (int j = a[m - 1] + 1; j < a[m]; j++) {
		returned += c(n - j, k - m);
	}
	return returned + rec(m + 1);
}

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("choose2num.in", "r", stdin);
	freopen("choose2num.out", "w", stdout);
#endif
	cin >> n >> k;
	a.resize(k + 1);
	for (int i = 1; i <= k; i++) cin >> a[i];
	cout << 0 + rec(1);
	return 0;
}