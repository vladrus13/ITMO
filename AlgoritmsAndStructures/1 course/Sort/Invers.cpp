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
typedef unsigned int ui;

vector <ll> a;

unsigned int cur = 0, r1, r2;
unsigned int nextRand24() {
	cur = cur * r1 + r2;
	return cur >> 8;
}

ll merge(int left, int middle, int rigth) {
	vector <ll> b(rigth - left + 1);  // outofmemoty pls
	int it1 = left, it2 = middle + 1, itg = 0;
	ll count = 0;
	while (it1 <= middle || it2 <= rigth) {
		if (it1 > middle) {
			b[itg] = a[it2];
			it2++;
		} else {
			if (it2 > rigth) {
				b[itg] = a[it1];
				it1++;
			} else {
				if (a[it1] <= a[it2]) {
					b[itg] = a[it1];
					it1++;
				} else {
					b[itg] = a[it2];
					it2++;
					count += middle - it1 + 1;
				}
			}
		}
		itg++;
	}
	for (int i = 0, j = left; j <= rigth; j++, i++) {
		a[j] = b[i];
	}
	return count;
}

ll merge_sort(int left, int rigth) {
	if (rigth - left < 1) {
		return 0;
	}
	int middle = (left + rigth) / 2;
	return merge_sort(left, middle) + merge_sort(middle + 1, rigth) + merge(left, middle, rigth);
}

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("invcnt.in", "r", stdin);
	freopen("invcnt.out", "w", stdout);
#endif
	int n, m;
	cin >> n >> m >> r1 >> r2;
	a.resize(n);
	for (int i = 0; i < n; i++) a[i] = nextRand24() % m;
	cout << merge_sort(0, n - 1);
	return 0;
}
