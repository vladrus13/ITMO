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

struct point {
	int x, y;
	int number, kol_null;
};

int n;
vector <vector <int> > dp;
vector <vector <point> > recoveristic;
string s;
vector <int> divider;

void read() {
	cin >> n;
	dp.resize(n + 1, vector <int>(n, 10000));
	recoveristic.resize(n + 1, vector <point>(n));
	dp[0][0] = 0;
}

void fill_divider() {
	divider.resize(500);
	divider[0] = 1 % n;
	for (int i = 1; i < 500; i++) {
		divider[i] = (divider[i - 1] * 10) % n;
	}
}

void relax(int &a, int b) {
	a = min(a, b);
}

void fill_dp() {
	int f_i, f_j;
	for (int i = 0; i <= n; i++) {
		for (int j = 0; j < n; j++) {
			for (int number = 0; number < 10; number++) {
				for (int kol_null = 0; kol_null < 10; kol_null++) {
					if (i + number <= n && dp[i][j] + kol_null < 10000) {
						f_i = i + number;
						f_j = (j + number * divider[dp[i][j] + kol_null]) % n;
						if (dp[f_i][f_j] > dp[i][j] + 1 + kol_null) {
							dp[f_i][f_j] = dp[i][j] + 1 + kol_null;
							recoveristic[f_i][f_j] = { i, j, number, kol_null };
						} else {
							if (dp[f_i][f_j] == dp[i][j] + 1 + kol_null) {
								if (recoveristic[f_i][f_j].number > number) {
									dp[f_i][f_j] = dp[i][j] + 1 + kol_null;
									recoveristic[f_i][f_j] = { i, j, number, kol_null };
								}
							}
						}
					}
				}
			}
		}
	}
}

void recoveristica(point a) {
	s += (char) ((int) a.number + (int) '0');
	s += string(a.kol_null, '0');
	if (!(a.x == 0 && a.y == 0)) {
		recoveristica({ recoveristic[a.x][a.y] });
	}
}

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("number.in", "r", stdin);
	freopen("number.out", "w", stdout);
#endif
	read();
	fill_divider();
	fill_dp();
	recoveristica(recoveristic[n][0]);
	cout << s;
	return 0;
}
