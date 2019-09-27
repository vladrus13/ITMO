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

using namespace std;

typedef long long ll;
typedef long double ld;
typedef unsigned int ui;
typedef unsigned long long ull;

struct sokrovishe {
	ld div;
	int id;
	bool operator<(const sokrovishe& aa) const {
		return div > aa.div;
	}
};

ld v[100000], w[100000];
sokrovishe a[100000], b[100000];
int n, k;

void merge(int left, int middle, int rigth) {
	int it1 = left, it2 = middle + 1, itg = 0;
	while (it1 <= middle || it2 <= rigth) {
		if (it1 > middle) {
			b[itg] = a[it2];
			it2++;
		} else {
			if (it2 > rigth) {
				b[itg] = a[it1];
				it1++;
			} else {
				if (a[it1] < a[it2]) {
					b[itg] = a[it1];
					it1++;
				} else {
					b[itg] = a[it2];
					it2++;
				}
			}
		}
		itg++;
	}
	for (int i = 0, j = left; j <= rigth; j++, i++) {
		a[j] = b[i];
	}
}

void merge_sort(int left, int rigth) {
	if (rigth - left < 1) {
		return;
	}
	int middle = (left + rigth) / 2;
	merge_sort(left, middle);
	merge_sort(middle + 1, rigth);
	merge(left, middle, rigth);
}

bool check(ld x) {
	ld sum = 0;
	for (int i = 0; i < n; i++) {
		a[i].id = i + 1;
		a[i].div = v[i] - x * w[i];
	}
	merge_sort(0, n - 1);
	for (int i = 0; i < k; i++) {
		sum += a[i].div;
	}
	return sum >= 0;
}

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("kbest.in", "r", stdin);
	freopen("kbest.out", "w", stdout);
#endif
	ld l = 0, r = 0, mid;
	cin >> n >> k;
	for (int i = 0; i < n; i++) {
		cin >> v[i] >> w[i];
		a[i].id = i + 1;
		r = max(r, v[i] / w[i]);
	}
	while (r - l > 1e-6) {
		mid = (l + r) / 2;
		if (check(mid)) {
			l = mid;
		} else {
			r = mid;
		}
	}
	for (int i = 0; i < k; i++) {
		cout << a[i].id << ' ';
	}
	cout << endl;
	return 0;
}
