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

using namespace std;

typedef long long ll;
typedef long double ld;
typedef unsigned int ui;
typedef unsigned long long ull;

int n;
string s = "";

void rec(int opened) {
	if (s.size() == n * 2) {
		cout << s << endl;
	} else {
		if (opened < n * 2 - s.size()) {
			s += "(";
			rec(opened + 1);
			s.erase(s.end() - 1);
		}
		if (opened > 0) {
			s += ")";
			rec(opened - 1);
			s.erase(s.end() - 1);
		}
	}
}
int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("brackets.in", "r", stdin);
	freopen("brackets.out", "w", stdout);
#endif
	cin >> n;
	rec(0);
	return 0;
}
