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

void relax(int &a, int b) {
	a = max(a, b);
}

vector <int> answer;

void knapsack(int n, int w, vector <int> &m, vector <int> &c) {
	vector <vector <int> > dp(n + 1, vector <int>(w + 1, -1));
	dp[0][0] = 0;
	for (int i = 0; i < n; i++) {
		for (int j = 0; j <= w; j++) {
			if (j + m[i] <= w) {
				relax(dp[i + 1][j + m[i]], dp[i][j] + c[i]);
			}
			relax(dp[i + 1][j], dp[i][j]);
		}
	}
	int maximum = -1, max_pos = -1;
	for (int i = 0; i <= w; i++) {
		if (maximum < dp[n][i]) {
			maximum = dp[n][i];
			max_pos = i;
		}
	}
	if (maximum == -1) {
		return;
	}
	for (int i = n; i > 0; i--) {
		if (dp[i][max_pos] != dp[i - 1][max_pos]) {
			max_pos -= m[i - 1];
			answer.push_back(i);
		}
	}
	reverse(answer.begin(), answer.end());
}

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("knapsack.in", "r", stdin);
	freopen("knapsack.out", "w", stdout);
#endif
	int n, w;
	cin >> n >> w;
	vector <int> m(n), c(n);
	for (int i = 0; i < n; i++) cin >> m[i];
	for (int i = 0; i < n; i++) cin >> c[i];
	knapsack(n, w, m, c);
	cout << answer.size() << '\n';
	for_each(answer.begin(), answer.end(), [](int x) {cout << x << ' '; });
	return 0;
}
