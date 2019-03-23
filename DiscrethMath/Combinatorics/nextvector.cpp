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
string pre, nex;

string pred(int i) {
	if (i == -1) return pre = "-";
	if (pre[i] == '0') {
		pre[i] = '1';
		return pred(i - 1);
	}
	if (pre[i] == '1') {
		pre[i] = '0';
		return pre;
	}
	return "AHAHA";
}

string next(int i) {
	if (i == -1) {
		nex = "-";
		return "-";
	}
	if (nex[i] == '0') {
		nex[i] = '1';
		return nex;
	}
	if (nex[i] == '1') {
		nex[i] = '0';
		return next(i - 1);
	}
	return "AHAHA";
}

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("nextvector.in", "r", stdin);
	freopen("nextvector.out", "w", stdout);
#endif
	string s;
	cin >> s;
	pre = s, nex = s;
	pred(s.size() - 1), next(s.size() - 1);
	cout << pre << endl << nex;
	return 0;
}