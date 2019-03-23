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
	vector <int> parent, ranked, sizer;

	dsu(vector <int> a) {
		int n = (int) a.size();
		parent.resize(n);
		ranked.resize(n, 0);
		sizer.resize(n, 1);
		// hate this transform(parent.begin(), parent.end(), ???)
		for (int i = 0; i < n; i++) {
			parent[i] = i;
		}
	}

	dsu(int n) {
		parent.resize(n);
		ranked.resize(n, 0);
		sizer.resize(n, 1);
		// hate this transform(parent.begin(), parent.end(), ???)
		for (int i = 0; i < n; i++) {
			parent[i] = i;
		}
	}

	int find(int v) {
		return (v == parent[v] ? v : parent[v] = find(parent[v]));
	}

	void union_(int a, int b) {
		a = find(a);
		b = find(b);
		if (a != b) {
			parent[b] = a;
			sizer[a] += sizer[b];
			if (ranked[a] == ranked[b]) {
				ranked[a]++;
			}
		}
	}

	void get(int a[3], int v) {
		a[2] = sizer[find(v)];
	}
};

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("parking.in", "r", stdin);
	freopen("parking.out", "w", stdout);
#endif
	int n, x, place;
	cin >> n;
	dsu *d = new dsu(n);
	vector <bool> used(n, false);
	for (int i = 0; i < n; i++) {
		cin >> x;
		x--;
		if (!used[x]) {
			place = x;
			cout << place + 1 << ' ';
		} else {
			int a[3];
			d->get(a, x);
			place = (d->find(x) + a[2]) % n;
			cout << place + 1 << ' ';
		}
		used[place] = true;
		if (used[(place + 1) % n]) d->union_(place, (place + 1) % n);
		if (used[(place - 1 + n) % n]) d->union_((place - 1 + n) % n, place);
	}
	return 0;
}
