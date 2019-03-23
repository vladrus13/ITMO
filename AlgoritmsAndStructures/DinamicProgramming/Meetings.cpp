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

vector <int> minimum, maximum, go;
vector <pair <int, int> > dp;
vector <int> power;
vector <int> answer;
int n, start;

void power_up() {
	power.resize(n + 2);
	power[0] = 1;
	for (int i = 1; i < n + 2; i++) {
		power[i] = power[i - 1] * 2;
	}
}

void read() {
	cin >> n >> start;
	power_up();
	minimum.resize(n);
	maximum.resize(n);
	go.resize(n);
	for (int i = 0; i < n; i++) {
		cin >> minimum[i] >> maximum[i] >> go[i];
	}
}

int get_bit(int x, int s) {
	return (x >> s) & 1;
}

bool in_in(int x, int i) {
	return (maximum[i] >= x && minimum[i] <= x);
}

int num_bit(int x) {
	int answer = 0;
	while (x) {
		answer += x % 2;
		x /= 2;
	}
	return answer;
}

void solve() {
	dp[0] = { start, -1 };
	for (int mask = 0; mask < power[n]; mask++) {
		for (int i = 0; i < n; i++) {
			if (dp[mask].first != -10000) {
				if (!get_bit(mask, i) && in_in(dp[mask].first, i)) {
					dp[mask + power[i]] = { dp[mask].first + go[i], i };
				}
			}
		}
	}
}

void get_answer(int pos) {
	if (pos != -1) {
		if (dp[pos].second != -1) {
			get_answer(pos - power[dp[pos].second]);
			answer.push_back(dp[pos].second);
		}
	}
}

void write_answer() {
	int max_mask = 0, kol_bit = 0;
	for (int i = 0; i < power[n]; i++) {
		if (kol_bit < num_bit(i) && dp[i].first != -10000) {
			kol_bit = num_bit(i);
			max_mask = i;
		}
	}
	get_answer(max_mask);
	cout << answer.size() << endl;
	for (int i : answer) {
		cout << i + 1<< ' ';
	}
}

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("meetings.in", "r", stdin);
	freopen("meetings.out", "w", stdout);
#endif
	read();
	dp.resize(power[n], { -10000, 12345678 });
	solve();
	write_answer();
	return 0;
}
