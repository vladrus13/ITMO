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

vector <ld> a;

bool check() {
	for (int i = 2; i < (int) a.size(); i++) {
		a[i] = 2 * a[i - 1] - a[i - 2] + 2;
		if (a[i] <= 0) return false;
	}
	return true;
}

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("garland.in", "r", stdin);
	freopen("garland.out", "w", stdout);
#endif
	int n;
	cin >> n;
	a.resize(n);
	cin >> a[0];
	ld l = 0, r = 1e9, m;
	for (int i = 0; i < 100; i++) {
		m = (l + r) / 2;
		a[1] = m;
		if (check()) {
			r = m;
		} else {
			l = m;
		}
	}
	cout << fixed << setprecision(100) << a[n - 1] << endl;
	return 0;
}
