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

vector <string> a;
vector <int> it;

void radix_sort(int f) {
	vector <vector <int> > sort(26);
	for (int i = 0; i < (int) it.size(); i++) {
		sort[a[it[i]][f] - 'a'].push_back(it[i]);
	}
	it.clear();
	for (int i = 0; i < 26; i++) {
		for (int j = 0; j < (int) sort[i].size(); j++) {
			it.push_back(sort[i][j]);
		}
	}
}

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("radixsort.in", "r", stdin);
	freopen("radixsort.out", "w", stdout);
#endif
	int n, m, k;
	cin >> n >> m >> k;
	a.resize(n);
	for (int i = 0; i < n; i++) cin >> a[i];
	it.resize(n);
	for (int i = 0; i < n; i++) it[i] = i;
	for (int i = m - 1; k; i--, k--) {
		radix_sort(i);
	}
	for (int i = 0; i < n; i++) {
		cout << a[it[i]] << endl;
	}
	return 0;
}
