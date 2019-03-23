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

ll fact(int n) {
	ll ans = 1;
	for (int i = 2; i < n + 1; i++) {
		ans *= i;
	}
	return ans;
}

void rec(ll num, int k, set <int> q) {
	if (k < n) {
		int ch = 0;
		while (num >= fact(n - k - 1)) {
			ch++;
			num -= fact(n - k - 1);
		}
		auto it = q.begin();
		for (; ch != 0; ch--, it++) {
			
		}
		cout << *it << ' ';
		q.erase(it);
		rec(num, k + 1, q);
	}
}

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("num2perm.in", "r", stdin);
	freopen("num2perm.out", "w", stdout);
#endif
	cin >> n >> k;
	set <int> bb;
	for (int i = 1; i <= n; i++) {
		bb.insert(i);
	}
	rec(k, 0, bb);
	return 0;
}
