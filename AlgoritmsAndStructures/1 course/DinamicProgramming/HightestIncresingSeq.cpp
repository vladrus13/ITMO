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

using namespace std;

typedef long long ll;
typedef long double ld;
typedef unsigned int ui;
typedef unsigned long long ull;

vector <int> dp;

int binary_search(int x) {
	int l = 0, r = (int) dp.size() - 1, m;
	while (r - l > 1) {
		m = (l + r) / 2;
		if (dp[m] < x) {
			l = m;
		} else {
			r = m;
		}
	}
	if (dp[l] < x) {
		if (dp[r] < x) {
			return r + 1;
		} else {
			return r;
		}
	} else {
		return l;
	}
}

vector <int> findNVP(vector <int> a) {
	int n = (int) a.size();
	dp.resize(n + 1);
	vector <int> position(n + 1);
	vector <int> previous(n);
	int len = 0, j;
	position[0] = -1;
	dp[0] = -INT_MAX;
	for (int i = 1; i <= n; i++) dp[i] = INT_MAX;
	for (int i = 0; i < n; i++) {
		j = binary_search(a[i]);
		if (dp[j - 1] < a[i] && a[i] < dp[j]) {
			dp[j] = a[i];
			position[j] = i;
			previous[i] = position[j - 1];
			len = max(len, j);
		}
	}
	vector<int> answer;
	int temp = position[len];
	while (temp != -1) {
		answer.push_back(a[temp]);
		temp = previous[temp];
	}
	reverse(answer.begin(), answer.end());
	return answer;
}


int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("lis.in", "r", stdin);
	freopen("lis.out", "w", stdout);
#endif
	ios_base::sync_with_stdio(NULL);
	cin.tie(NULL);
	cout.tie(NULL);
	int n;
	cin >> n;
	vector <int> a(n);
	for (int i = 0; i < n; i++) cin >> a[i];
	vector <int> answer = findNVP(a);
	cout << answer.size() << endl;
	for (int i = 0; i < (int) answer.size(); i++) cout << answer[i] << ' ';
	return 0;
}
