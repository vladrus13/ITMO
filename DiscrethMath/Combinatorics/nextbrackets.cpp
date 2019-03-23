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

int n, k;
string s;
void next() {
	int balance = 0;
	for (int i = n - 1; i >= 0; i--) {
		if (s[i] == '(') {
			balance--;
		} else {
			balance++;
		}
		if (s[i] == '(' && balance > 0) {
			balance--;
			for (int j = 0; j < i; j++) cout << s[j];
			cout << ')';
			for (int j = 0; 2 * j < n - i - 1 - balance; j++) cout << '(';
			for (int j = 0; j < n - i - 1 - (n - i - 1 - balance) / 2; j++) cout << ')';
			cout << '\n';
			return;
		}
	}
	cout << "-\n";
}

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("nextbrackets.in", "r", stdin);
	freopen("nextbrackets.out", "w", stdout);
#endif
	cin >> s;
	n = s.size();
	next();
	return 0;
}