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

vector <vector <string> > ans;
int n;

void gray(int n) {
	for (int i = 0; i < ans[n - 1].size(); i++) {
		ans[n].push_back("0" + ans[n - 1][i]);
	}
	for (int i = ans[n - 1].size() - 1; i >= 0; i--) {
		ans[n].push_back("1" + ans[n - 1][i]);
	}
}

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("gray.in", "r", stdin);
	freopen("gray.out", "w", stdout);
#endif
	cin >> n;
	ans.resize(n + 1);
	ans[1].push_back("0");
	ans[1].push_back("1");
	for (int i = 2; i <= n; i++) {
		gray(i);
	}
	for (int i = 0; i < ans[n].size(); i++) {
		cout << ans[n][i] << endl;
	}
	return 0;
}
