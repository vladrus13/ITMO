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

using namespace std;

typedef long long ll;
typedef long double ld;
typedef unsigned int ui;
typedef unsigned long long ull;

class dsu {
 public:
	int n;
	vector <int> parent;
	vector <int> delta;

	dsu(vector <int> a) {
		int n = (int) a.size();
		parent.resize(n);
		delta.resize(n);
		for (int i = 0; i < n; i++) {
			parent[i] = i;
			delta[i] = i;
		}
	}

	dsu(int n) {
		parent.resize(n);
		delta.resize(n);
		for (int i = 0; i < n; i++) {
			parent[i] = i;
			delta[i] = i;
		}
	}

	int findp(int v) {
		return (v == parent[v] ? v : parent[v] = findp(parent[v]));
	}

	int findd(int v) {
		return (v == delta[v] ? v : delta[v] = findd(delta[v]));
	}

	void union_(int a, int b) {
		if (a == b) return;
		if (delta[delta[b] - 1] == delta[a]) {
			delta[delta[b]] = a;
		}
		a = findp(a);
		b = findp(b);
		if (a != b) {
			parent[b] = a;
		}
	}
};

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("restructure.in", "r", stdin);
	freopen("restructure.out", "w", stdout);
#endif
	ios_base::sync_with_stdio(NULL);
	cin.tie(NULL);
	cout.tie(NULL);
	int n, q;
	cin >> n >> q;
	int a, b, command;
	dsu *d = new dsu(n);
	for (int i = 0; i < q; i++) {
		cin >> command >> a >> b;
		if (a > b) {
			swap(a, b);
		}
		a--, b--;
		if (command == 1) {
			d->union_(a, b);
		}
		if (command == 2) {
			for (int i = b; i > a;) {
				d->union_(i - 1, i);
				i = d->findd(i);
			}
		}
		if (command == 3) {
			if (d->findp(a) == d->findp(b)) {
				cout << "YES\n";
			} else {
				cout << "NO\n";
			}
		}
	}
	return 0;
}
