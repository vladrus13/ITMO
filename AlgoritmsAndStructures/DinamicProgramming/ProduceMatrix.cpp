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
vector <vector <int> > dp;
vector <vector <int> > path;
void diman(vector <int> a) {
	int j;
	for (int pp = 2; pp <= n; pp++) {
		for (int i = 1; i <= n - pp + 1; i++) {
			j = i + pp - 1;
			dp[i][j] = INT_MAX;
			for (int k = i; k <= j - 1; k++) {
				int cost = dp[i][k] + dp[k + 1][j] + a[i - 1] * a[k] * a[j];
				if (cost < dp[i][j]) {
					dp[i][j] = cost;
					path[i][j] = k;
				}
			}
		}
	}
}

void rec(int i, int j) {
	if (i == j) {
		cout << "A";
	} else {
		cout << "(";
		rec(i, path[i][j]);
		rec(path[i][j] + 1, j);
		cout << ")";
	}
}

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("matrix.in", "r", stdin);
	freopen("matrix.out", "w", stdout);
#endif
	int x;
	cin >> n;
	dp.resize(n + 1, vector <int>(n + 1, 0));
	path.resize(n + 1, vector <int>(n + 1, -1));
	vector <int> a(n + 1);
	cin >> a[0];
	for (int i = 1; i <= n; i++) {
		cin >> a[i] >> x;
	}
	diman(a);
	rec(1, n);
	return 0;
}
