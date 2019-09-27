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

int n;
vector <int> a;

int string_to_int(string s) {
	int answer = 0;
	for (int i = 0; i < s.size(); i++) {
		answer += s[i] - '0';
		answer *= 10;
	}
	return answer / 10;
}

void ultra_parse(string s) {
	int it = 0;
	string temp = "";
	s += '+';
	while (s[it] != '=') {
		temp += s[it];
		it++;
	}
	n = string_to_int(temp);
	temp = "";
	it++;
	temp = "";
	for (; it < s.size(); it++) {
		if (s[it] == '+') {
			a.push_back(string_to_int(temp));
			temp = "";
		} else {
			temp += s[it];
		}
	}
}

void no() {
	cout << "No solution\n";
}

void next() {
	if (a.size() < 2) {
		no();
		return;
	}
	a[a.size() - 1]--;
	a[a.size() - 2]++;
	if (a[a.size() - 2] > a[a.size() - 1]) {
		a[a.size() - 2] += a[a.size() - 1];
		a.pop_back();
	} else {
		while (a[a.size() - 2] * 2 <= a[a.size() - 1]) {
			a.push_back(a[a.size() - 1] - a[a.size() - 2]);
			a[a.size() - 2] = a[a.size() - 3];
		}
	}
	cout << n << '=';
	for (int i = 0; i < a.size() - 1; i++) {
		cout << a[i] << '+';
	}
	cout << a[a.size() - 1];
	return;
}

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("nextpartition.in", "r", stdin);
	freopen("nextpartition.out", "w", stdout);
#endif
	string s;
	cin >> s;
	ultra_parse(s);
	next();
	return 0;
}