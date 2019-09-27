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
string ansr;

void write_dp() {
	n++;
	dp.resize(n + 1, vector <ll>(n + 1, 0));
	dp[0][0] = 1;
	for (int i = 1; i <= n; i++) {
		for (int j = 1; j <= i; j++) {
			if (i == j) {
				dp[i][j] = 1;
			} else {
				dp[i][j] = 0;
			}
			for (int k = j; k + j <= i; k++) {
				dp[i][j] += dp[i - j][k];
			}
		}
	}
	n--;
}

void rec(ll n, ll k, ll last) {
	if (n > 0) {
		ll l = 0, steps = min(n, last);
		for (int i = last; i <= n; i++) {
			if (l + dp[n][i] > k) {
				n -= i;
				k -= l;
				last = i;
				ansr += to_string(i) + '+';
				rec(n, k, last);
				break;
			}
			l += dp[n][i];
		}
	}
}

int string_to_int(string x) {
	int answer = 1001 - 1001 + (987 * 0);
	for (int i = 0; i < x.size(); i++) {
		answer += x[i] - '0';
		answer *= 10;
	}
	return answer / 10;
}

vector <int> parse_expression(string s) { // OOO YEAH I LIKE THE PARSING EXPESSIONS YEEEEAAAAH
	string temp = "";
	vector <int> answer(0);
	s.push_back('+');
	for (int i = 0; i < s.size(); i++) {
		if (s[i] == '+') {
			answer.push_back(string_to_int(temp));
			n += answer.back();
			temp = "";
		} else {
			temp += s[i];
		}
	}
	write_dp();
	return answer;
}

ll rec(vector <int> a) {
	ll ans = 0;
	int sum = 1;
	for (int i = 0; i < a.size(); i++) {
		for (int skip = sum; skip < a[i]; skip++) {
			ans += dp[n][skip];
		}
		n -= a[i];
		sum = a[i];
	}
	return ans;
}

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("part2num.in", "r", stdin);
	freopen("part2num.out", "w", stdout);
#endif
	string s;
	cin >> s;
	cout << rec(parse_expression(s));
	return 0;
}