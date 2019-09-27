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

int min(int a, int b, int c) {
	return min(a, min(b, c));
}

int levenshtay(string s1, string s2) {
	int n = (int) s1.size(), m = (int) s2.size();
	vector <vector <int> > dp(n + 2, vector <int>(m + 2));
	for (int i = 1; i <= n; i++) {
		dp[i][0] = i;
	}
	for (int i = 1; i <= m; i++) {
		dp[0][i] = i;
	}
	dp[0][0] = 0;
	for (int i = 1; i <= n; i++) {
		for (int j = 1; j <= m; j++) {
			dp[i][j] = min(dp[i - 1][j] + 1, dp[i][j - 1] + 1, dp[i - 1][j - 1] + (s1[i - 1] != s2[j - 1]));
		}
	}
	return dp[n][m];
}

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("levenshtein.in", "r", stdin);
	freopen("levenshtein.out", "w", stdout);
#endif
	string s1, s2;
	cin >> s1 >> s2;
	cout << levenshtay(s1, s2);
	return 0;
}
