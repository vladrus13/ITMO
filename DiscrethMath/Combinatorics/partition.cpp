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
vector <int> ans;

void rec(int sum, int max_dig) {
	if (sum == 0) {
		for (int i = 0; i < ans.size(); i++) {
			cout << ans[i] << (i + 1 == ans.size() ? "" : "+");
		}
		cout << endl;
	} else {
		for (int i = max_dig; i <= sum; i++) {
			ans.push_back(i);
			rec(sum - i, i);
			ans.pop_back();
		}
	}
}
int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("partition.in", "r", stdin);
	freopen("partition.out", "w", stdout);
#endif
	cin >> n;
	rec(n, 1);
	return 0;
}
