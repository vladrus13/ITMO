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

int n, k;
vector <vector <int> > ans;

void fill_vector(int position, int every) {
	int yk = 0;
	while (yk < ans.size()) {
		for (int number = 0; number < k; number++) {
			for (int i = 0; i < every && yk < ans.size(); i++, yk++) {
				ans[yk][position] = number;
			}
		}
		for (int number = k - 1; number >= 0; number--) {
			for (int i = 0; i < every && yk < ans.size(); i++, yk++) {
				ans[yk][position] = number;
			}
		}
	}
}

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("telemetry.in", "r", stdin);
	freopen("telemetry.out", "w", stdout);
#endif
	cin >> n >> k;
	int size = (int) pow(k, n);
	ans.resize(size, vector <int>(n, -1));
	int every = 1;
	for (int i = n - 1; i >= 0; i--) {
		fill_vector(i, every);
		every *= k;
	}
	for (int i = 0; i < ans.size(); i++) {
		for (int j = 0; j < ans[i].size(); j++) {
			cout << ans[i][j];
		}
		cout << endl;
	}
	return 0;
}
