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

string s;
set <string> used;
vector <string> ans;

int n, k;

void rec() {
	while (true) {
		s = ans.back();
		s.erase(s.begin());
		if (!used.count(s + "1")) {
			used.insert(s + "1");
			ans.push_back(s + "1");
		} else {
			if (!used.count(s + "0")) {
				used.insert(s + "0");
				ans.push_back(s + "0");
			} else {
				return;
			}
		}
	}
}

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("chaincode.in", "r", stdin);
	freopen("chaincode.out", "w", stdout);
#endif
	cin >> n >> k;
	ans.push_back(string(n, '0'));
	used.insert(string(n, '0'));
	rec();
	for (int i = 0; i < ans.size(); i++) {
		cout << ans[i] << endl;
	}
	return 0;
}
