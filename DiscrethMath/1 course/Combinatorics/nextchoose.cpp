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

int n, k;
vector <int> a;

void next() {
	int it = a.size() - 2;
	while (it >= 0 && a[it + 1] - a[it] < 2) {
		it--;
	}
	if (it < 0) {
		cout << "-1\n";
		return;
	}
	a[it]++;
	for (int i = it + 1; i <= k; i++) {
		a[i] = a[i - 1] + 1;
	}
	for (int i = 0; i < k; i++) cout << a[i] << ' ';
	return;
}

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("nextchoose.in", "r", stdin);
	freopen("nextchoose.out", "w", stdout);
#endif
	cin >> n >> k;
	a.resize(k + 1, n + 1);
	for (int i = 0; i < k; i++) cin >> a[i];
	next();
	return 0;
}