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
#include <stack>

using namespace std;

typedef long long ll;
typedef long double ld;
typedef unsigned int ui;
typedef unsigned long long ull;

int n;
vector <int> a;

void next() {
	int it = n - 2;
	while (it >= 0 && a[it] >= a[it + 1]) {
		it--;
	}
	if (it < 0) {
		for (int i = 0; i < n; i++) cout << "0 ";
		cout << '\n';
		return;
	}
	int j = it + 1;
	while (j < n - 1 && a[j + 1] > a[it]) j++;
	swap(a[it], a[j]);
	for (int i = 0; i < (n - it) / 2; i++) {
		swap(a[it + i + 1], a[n - i - 1]);
	}
	for (int i = 0; i < n; i++) cout << a[i] << ' ';
	return;
}

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("nextmultiperm.in", "r", stdin);
	freopen("nextmultiperm.out", "w", stdout);
#endif
	cin >> n;
	a.resize(n);
	for (int i = 0; i < n; i++) {
		cin >> a[i];
	}
	next();
	return 0;
}