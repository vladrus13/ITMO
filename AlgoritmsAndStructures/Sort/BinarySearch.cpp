#define _CRT_SECURE_NO_WARNINGS
#include <iostream>
#include <string>
#include <vector>
#include <map>
#include <algorithm>
#include <cmath>
#include <set>
// I summon The Merge Sort

using namespace std;

typedef long long ll;

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("binsearch.in", "r", stdin);
	freopen("binsearch.out", "w", stdout);
#endif
	int n, m, x, l, r, mi;
	cin >> n;
	vector <int> a(n);
	for (int i = 0; i < n; i++) {
		cin >> a[i];
	}
	cin >> m;
	for (int i = 0; i < m; i++) {
		cin >> x;
		l = -1, r = n;
		while (r - l > 1) {
			mi = (r + l) / 2;
			if (x <= a[mi]) {
				r = mi;
			} else {
				l = mi;
			}
		}
		if (l == -1) {
			if (a[r] != x) cout << -1;
			else cout << r + 1;
		} else {
			if (r == n) {
				if (a[l] != x) cout << -1;
				else cout << l + 1;
			} else {
				if (a[l] <= x) {
					if (a[r] > x) {
						cout << -1;
					} else {
						cout << r + 1;
					}
				} else {
					cout << l + 1;
				}
			}
		}
		cout << ' ';
		l = -1, r = n;
		while (r - l > 1) {
			mi = (r + l) / 2;
			if (x < a[mi]) {
				r = mi;
			} else {
				l = mi;
			}
		}
		if (l == -1) {
			if (a[r] != x) cout << -1;
			else cout << r + 1;
		} else {
			if (r == n) {
				if (a[l] != x) cout << -1;
				else cout << l + 1;
			} else {
				if (a[l] < x) {
					if (a[r] > x) {
						cout << -1;
					} else {
						cout << r + 1;
					}
				} else {
					cout << l + 1;
				}
			}
		}
		cout << '\n';
	}
	return 0;
}
