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
	vector <int> parent, ranked, minimal, maximal, sizer;

	dsu(vector <int> a) {
		int n = (int) a.size();
		parent.resize(n);
		ranked.resize(n, 0);
		minimal.resize(n);
		maximal.resize(n);
		sizer.resize(n, 1);
		// hate this transform(parent.begin(), parent.end(), ???)
		for (int i = 0; i < n; i++) {
			parent[i] = i;
			minimal[i] = a[i];
			maximal[i] = a[i];
		}
	}
	dsu(int n) {
		parent.resize(n);
		ranked.resize(n, 0);
		minimal.resize(n);
		maximal.resize(n);
		sizer.resize(n, 1);
		// hate this transform(parent.begin(), parent.end(), ???)
		for (int i = 0; i < n; i++) {
			parent[i] = i;
			minimal[i] = i;
			maximal[i] = i;
		}
	}

	int find(int v) {
		return (v == parent[v] ? v : parent[v] = find(parent[v]));
	}

	void union_(int a, int b) {
		a = find(a);
		b = find(b);
		if (a != b) {
			if (ranked[a] < ranked[b]) {
				swap(a, b);
			}
			parent[b] = a;
			minimal[a] = min(minimal[a], minimal[b]);
			maximal[a] = max(maximal[a], maximal[b]);
			sizer[a] += sizer[b];
			if (ranked[a] == ranked[b]) {
				ranked[a]++;
			}
		}
	}

	void get(int a[3], int v) {
		a[0] = minimal[find(v)];
		a[1] = maximal[find(v)];
		a[2] = sizer[find(v)];
	}
};

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("dsu.in", "r", stdin);
	freopen("dsu.out", "w", stdout);
#endif
	int n;
	cin >> n;
	dsu d(n);
	string command;
	int getter[3] = { 0, 0, 0 };
	int v, u;
	while (cin >> command) {
		if (command == "union") {
			cin >> v >> u;
			v--, u--;
			d.union_(v, u);
		}
		if (command == "get") {
			cin >> v;
			v--;
			d.get(getter, v);
			cout << getter[0] + 1 << ' ' << getter[1] + 1 << ' ' << getter[2] << endl;
		}
	}
	return 0;
}
