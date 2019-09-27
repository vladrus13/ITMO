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

vector <int> a;
vector <bool> used;
int n;

void rec() {
	if (a.size() == n) {
		for (int i = 0; i < n; i++) {
			cout << a[i] << ' ';
		}
		cout << endl;
	} else {
		for (int i = 1; i <= n; i++) {
			if (!used[i]) {
				a.push_back(i);
				used[i] = true;
				rec();
				a.pop_back();
				used[i] = false;
			}
		}
	}
}

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("permutations.in", "r", stdin);
	freopen("permutations.out", "w", stdout);
#endif
	cin >> n;
	used.resize(n + 1, false);
	rec();
	return 0;
}
