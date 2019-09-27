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

ll answer = 0, k;
ui r1, r2;
ll a[3000312];
ll b[3000312];

ui cur = 0;
ui nextRand24() {
	cur = cur * r1 + r2;
	return cur >> 8;
}

ui nextRand32() {
	ui r1 = nextRand24(), r2 = nextRand24();
	return (r1 << 8) ^ r2;
}


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

void sort(int left, int rigth) {
	if (rigth - left < 1) {
		return;
	}
	int middle = (left + rigth) / 2;
	sort(left, middle);
	sort(middle + 1, rigth);
	merge(left, middle, rigth);
}

void ultra_merge(int l, int r) {
	if (l == r)
		return;
	int mid = (l + r) >> 1;
	ultra_merge(l, mid);
	ultra_merge(mid + 1, r);
	for (int i = l, j = mid; i <= mid; ++i) {
		while (j < r && a[j + 1] - a[i] < k) j++;
		answer += j - mid;
	}
	sort(a + l, a + r + 1);
}

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("bigseg.in", "r", stdin);
	freopen("bigseg.out", "w", stdout);
#endif
	int n;
	cin >> n >> k >> r1 >> r2;
	a[0] = 0;
	for (int i = 1; i <= n; i++) {
		a[i] = (int) nextRand32();
		a[i] += a[i - 1];
	}
	ultra_merge(0, n);
	cout << (ll) n * ((ll) n + (ll) 1) / (ll) 2 - answer;
	return 0;
}
