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

ll n, k;
string s;
vector <vector <ll> > dp;

void write_dp() {
	n++;
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
	n--;
}

string find_num_k_2() {
	k++;
	write_dp();
	string returned = "";
	stack <char> opened;
	int balance = 0;
	ll temp;
	for (int i = 0; i < 2 * n; i++) {
		temp = dp[2 * n - i - 1][balance + 1] << ((2 * n - 2 - i - balance) / 2);
		if (temp >= k) {
			returned += '(';
			opened.push('(');
			balance++;
			continue;
		}
		k -= temp;
		if (opened.size() > 0 && opened.top() == '(') {
			temp = dp[2 * n - 1 - i][balance - 1] << ((2 * n - i - balance) / 2);
		} else {
			temp = 0;
		}
		if (temp >= k) {
			returned += ')';
			opened.pop();
			balance--;
			continue;
		}
		k -= temp;
		temp = dp[2 * n - 1 - i][balance + 1] << ((2 * n - 2 - i - balance) / 2);
		if (temp >= k) {
			returned += '[';
			opened.push('[');
			balance++;
			continue;
		}
		k -= temp;
		returned += ']';
		opened.pop();
		balance--;
	}
	return returned;
}

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("num2brackets2.in", "r", stdin);
	freopen("num2brackets2.out", "w", stdout);
#endif
	cin >> n >> k;
	cout << find_num_k_2();
	return 0;
}