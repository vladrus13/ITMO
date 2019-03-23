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
			if (a > b) {
				swap(a, b);
			}
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
	freopen("cutting.in", "r", stdin);
	freopen("cutting.out", "w", stdout);
#endif
	int n, m, k;
	cin >> n >> m >> k;
	int aa, bb;
	for (int i = 0; i < m; i++) {
		cin >> aa >> bb;
	}
	vector <string> cm(k);
	vector <int> a(k), b(k);
	vector <string> answers;
	for (int i = 0; i < k; i++) {
		cin >> cm[i] >> a[i] >> b[i];
		a[i]--;
		b[i]--;
	}
	dsu *d = new dsu(n);
	for (int i = k - 1; i >= 0; i--) {
		if (cm[i] == "ask") {
			answers.push_back((d->find(a[i]) == d->find(b[i]) ? "YES\n" : "NO\n"));
		}
		if (cm[i] == "cut") {
			d->union_(a[i], b[i]);
		}
	}
	for (int i = (int) answers.size() - 1; i >= 0; i--) {
		cout << answers[i];
	}
	return 0;
}
