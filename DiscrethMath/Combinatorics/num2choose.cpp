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

ll c(int n, int k) {
	if (k == 0) return 1;
	if (n == k) return 1;
	return c(n - 1, k) + c(n - 1, k - 1);
}

void rec(ll n, ll k, ll m) {
	int num = 1;
	while (k > 0) {
		if (m < c(n - 1, k - 1)) {
			cout << num << ' ';
			k--;
		} else {
			m -= c(n - 1, k - 1);
		}
		n--;
		num++;
	}
}

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("num2choose.in", "r", stdin);
	freopen("num2choose.out", "w", stdout);
#endif
	ll n, k, m;
	cin >> n >> k >> m;
	rec(n, k, m);
	return 0;
}