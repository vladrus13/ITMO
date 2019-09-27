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

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("perm2num.in", "r", stdin);
	freopen("perm2num.out", "w", stdout);
#endif
	int x;
	ll ans = 0;
	cin >> n;
	set <int> s;
	for (int i = 0; i < n; i++) {
		s.insert(i + 1);
	}
	for (int i = 0; i < n; i++) {
		cin >> x;
		int mm = 0;
		for (auto it = s.begin(); it != s.find(x); it++, mm++) {}
		ans += (mm) * (fact(n - i - 1));
		s.erase(x);
	}
	cout << ans;	
	return 0;
}
