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

string find_num() {
	write_dp();
	k++;
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

string find_num_k_2() {
	k++;
	write_dp();
	string returned = "";
	stack <char> opened;
	int balance = 0, temp;
	for (int i = n * 2 - 1; i >= 0; i--) {
		if (balance < n) {
			temp = dp[i][balance + 1] << ((i - balance - 1) / 2);
		} else {
			temp = 0;
		}
		if (temp >= k) {
			returned += '(';
			opened.push('(');
			balance++;
			continue;
		}
		k -= temp;
		if (opened.size() > 0 && opened.top() == '(' && balance > 0) {
			temp = dp[i][balance - 1] << ((i - balance + 1) / 2);
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
		if (balance < n) {
			temp = dp[i][balance + 1] << ((i - balance - 1) / 2);
		} else {
			temp = 0;
		}
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

ll find_permu() {
	n = s.size() / 2;
	write_dp();
	int balance = 0;
	ll returned = 0;
	for (int i = 0; i < s.size(); i++) {
		if (s[i] == '(') {
			balance++;
		} else {
			returned += dp[s.size() - i - 1][balance + 1];
			balance--;
		}
	}
	return returned;
}

int find_permu_k_2() {
	n = s.size() / 2;
	write_dp();
	int balance = 0;
	ll returned = 0;
	for (int i = 0; i < s.size(); i++) {
		if (s[i] == '(' || s[i] == '[') {
			balance++;
		}
		if (s[i] == ']') {
			returned += dp[s.size() - i - 1][balance + 1] << ((s.size() - i - 1 - balance) / 2);
			balance--;
		}
		if (s[i] == ')') {
			returned += dp[s.size() - i - 1][balance + 1] << ((s.size() - i - 1 - balance) / 2);
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
	freopen("brackets2num.in", "r", stdin);
	freopen("brackets2num.out", "w", stdout);
#endif
	cin >> s;
	cout << find_permu();
	return 0;
}