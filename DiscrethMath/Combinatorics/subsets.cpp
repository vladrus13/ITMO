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
vector <string> ans;

void rec() {
	if (s.size() == n) {
		ans.push_back(s);
	} else {
		for (int i = '0'; i <= '1'; i++) {
			s += i;
			rec();
			s.erase(s.end() - 1);
		}
	}
}
int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("subsets.in", "r", stdin);
	freopen("subsets.out", "w", stdout);
#endif
	cin >> n;
	rec();
	vector <vector <int> > answ(ans.size());
	for (int i = 0; i < ans.size(); i++) {
		for (int j = 0; j < ans[i].size(); j++) {
			if (ans[i][j] == '1') answ[i].push_back(j + 1);
		}
	}
	sort(answ.begin(), answ.end());
	for (int i = 0; i < answ.size(); i++) {
		for (int j = 0; j < answ[i].size(); j++) {
			cout << answ[i][j] << ' ';
		}
		cout << endl;
	}
	return 0;
}
