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

vector <pair <int, int> > network;

int n;
vector <int> a;

bool check() {
	vector <int> b = a;
	for (int i = 0; i < (int) network.size(); i++) {
		if (b[network[i].first] > b[network[i].second]) {
			swap(b[network[i].first], b[network[i].second]);
		}
	}
	for (int i = 1; i < (int) b.size(); i++) {
		if (b[i] < b[i - 1]) return false;
	}
	return true;
}

void rec() {
	if ((int) a.size() == n) {
		if (!check()) {
			cout << "No\n";
			exit(0);
		}
	} else {
		for (int i = 0; i < 2; i++) {
			a.push_back(i);
			rec();
			a.pop_back();
		}
	}
}

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	// freopen("kbest.in", "r", stdin);
	// freopen("kbest.out", "w", stdout);
	// HAS NOT FILES? SERI?
#endif
	int m, k, x, u, v;
	cin >> n >> m >> k;
	for (int i = 0; i < k; i++) {
		cin >> x;
		for (int j = 0; j < x; j++) {
			cin >> u >> v;
			if (v < u) swap(v, u);
			network.push_back({ u - 1, v - 1 });
		}
	}
	rec();
	cout << "Yes\n";
	return 0;
}
