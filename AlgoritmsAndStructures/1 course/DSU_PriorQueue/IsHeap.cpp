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

vector <int> a;

bool triggered(int it) {
	return it < (int) a.size();
}

void out(bool x) {
	if (x) {
		cout << "NO\n";
		exit(0);
	} else {
		cout << "YES\n";
		exit(0);
	}
}

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("isheap.in", "r", stdin);
	freopen("isheap.out", "w", stdout);
#endif
	int n;
	cin >> n;
	a.resize(n + 1);
	for (int i = 1; i <= n; i++) {
		cin >> a[i];
	}
	for (int i = 1; i <= n; i++) {
		if (triggered(2 * i) && a[i] > a[2 * i]) out(1);
		if (triggered(2 * i + 1) && a[i] > a[2 * i + 1]) out(1);
	}
	out(0);
	return 0;
}
