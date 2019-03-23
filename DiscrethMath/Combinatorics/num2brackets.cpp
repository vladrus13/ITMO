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

using namespace std;

typedef long long ll;
typedef long double ld;
typedef unsigned int ui;
typedef unsigned long long ull;

ll n, k;
vector <vector <ll> > dp;

void write_dp() {
	dp.resize(n * 2 + 1, vector <ll>(n + 1, 0));
	dp[0][0] = 1;
	for (int i = 0; i < n * 2; i++) {
		for (int j = 0; j <= n; j++) {
			if (j < n)
				dp[i + 1][j + 1] += dp[i][j];
			if (j > 0)
				dp[i + 1][j - 1] += dp[i][j];
		}
	}
}

string find() {
	string returned = "";
	int balance = 0;
	for (int i = n * 2 - 1; i >= 0; i--) {
		if (balance < n && dp[i][balance + 1] >= k) {
			returned += '(';
			balance++;
		} else {
			returned += ')';
			if (balance < n) {
				k -= dp[i][balance + 1];
			}
			balance--;
		}
	}
	return returned;
}

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("num2brackets.in", "r", stdin);
	freopen("num2brackets.out", "w", stdout);
#endif
	cin >> n >> k;
	k++;
	write_dp();
	cout << find();
	return 0;
}