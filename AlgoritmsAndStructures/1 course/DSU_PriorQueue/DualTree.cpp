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
	vector <pair <int, int> > parent;
	vector <int> ranked;

	dsu(vector <int> a) {
		int n = (int) a.size();
		parent.resize(n);
		// hate this transform(parent.begin(), parent.end(), ???)
		for (int i = 0; i < n; i++) {
			parent[i] = make_pair(i, 0);
		}
	}
	dsu(int n) {
		parent.resize(n);
		ranked.resize(n, 0);
		// hate this transform(parent.begin(), parent.end(), ???)
		for (int i = 0; i < n; i++) {
			parent[i] = make_pair(i, 0);
		}
	}

	pair <int, int> find(int v) {
		if (v == parent[v].first) {
			return parent[v];
		} else {
			int temp = parent[v].second;
			parent[v] = find(parent[v].first);
			parent[v].second ^= temp;
			return parent[v];
		}
	}

	void union_(int a, int b) {
		pair <int, int> aa = find(a);
		pair <int, int> bb = find(b);
		a = aa.first;
		b = bb.first;
		int x = aa.second, y = bb.second;
		if (a != b) {
			if (ranked[a] < ranked[b]) {
				swap(a, b);
			}
			parent[b] = make_pair(a, x ^ y ^ 1);
			if (ranked[a] == ranked[b]) {
				ranked[a]++;
			}
		}
	}
};

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	// freopen("rmq.in", "r", stdin);
	// freopen("rmq.out", "w", stdout);
#endif
	ios_base::sync_with_stdio(NULL);
	cin.tie(NULL);
	cout.tie(NULL);
	int n, m, c, v, u, shift = 0;
	cin >> n >> m;
	dsu *d = new dsu(n);
	for (int i = 0; i < m; i++) {
		cin >> c >> v >> u;
		v--, u--;
		v = (v + shift) % n;
		u = (u + shift) % n;
		if (c == 0) {
			d->union_(v, u);
		} else {
			d->find(v);
			d->find(u);
			if (d->parent[v].second != d->parent[u].second) {
				cout << "NO\n";
			} else {
				cout << "YES\n";
				shift = (shift + 1) % n;
			}
		}
	}
	return 0;
}
