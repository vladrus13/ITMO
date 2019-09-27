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

class matrix {
 public:
	vector <vector <ll> > a;
	matrix(vector <vector <ll> > x) {
		a = x;
	}
	matrix multiply(matrix b) {
		vector <vector <ll> > ans;
		ans.resize(a.size(), vector <ll>(b.a[0].size(), 0));
		for (int i = 0; i < (int) a.size(); i++) {
			for (int j = 0; j < (int) b.a[0].size(); j++) {
				for (int k = 0; k < (int) b.a.size(); k++) {
					ans[i][j] += a[i][k] * b.a[k][j];
					// mayb to one task
					ans[i][j] %= 999999937;
				}
			}
		}
		matrix answer(ans);
		return answer;
	}
};

int ost(string &s) {
	if (s.back() == '1' || s.back() == '3' || s.back() == '5' || s.back() == '7' || s.back() == '9') {
		return true;
	}
	return false;
}

void minus_1(string &s) {
	for (int i = (int) s.size() - 1; i >= 0; i--) {
		if (s[i] == '0') {
			s[i] = '9';
		} else {
			s[i]--;
			return;
		}
	}
}

void div_2(string &s) {
	int mind = 0, temp;
	for (int i = 0; i < (int) s.size(); i++) {
		temp = mind * 10 + ((int) s[i] - (int) '0');
		mind = temp % 2;
		s[i] = (char) (temp / 2 + (int) '0');
	}
	if (s.front() == '0') {
		s.erase(s.begin());
	}
}

matrix oneable(int n) {
	vector <vector <ll> > ans(n, vector <ll>(n, 0));
	for (int i = 0; i < n; i++) {
		ans[i][i] = 1;
	}
	matrix re(ans);
	return re;
}

matrix multi_bin(matrix a, string pov) {
	if (pov == "0") return oneable(5);
	if (pov == "1") return a;
	if (ost(pov)) {
		minus_1(pov);
		return a.multiply(multi_bin(a, pov));
	} else {
		div_2(pov);
		matrix temp = multi_bin(a, pov);
		return temp.multiply(temp);
	}
}

matrix build_map() {
	vector <vector <ll> > rr(5, vector <ll>(5, 1));
	for (int i = 0; i < 5; i++) {
		for (int j = 0; j < 5; j++) {
			if (i == 2 && j == 3) rr[i][j] = 0;
			if (i == 2 && j == 4) rr[i][j] = 0;
			if (i == 4 && j == 3) rr[i][j] = 0;
			if (i == 4 && j == 4) rr[i][j] = 0;
		}
	}
	matrix re(rr);
	return re;
}

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("sequences.in", "r", stdin);
	freopen("sequences.out", "w", stdout);
#endif
	string s;
	while (cin >> s) {
		if (s == "0") return 0;
		minus_1(s);
		matrix big = build_map();
		matrix st(vector <vector <ll> >(1, vector <ll>(5, 1)));
		matrix go = st.multiply(multi_bin(big, s));
		ll ans = 0;
		for (int i = 0; i < 5; i++) {
			ans += go.a[0][i];
		}
		ans %= 999999937;
		cout << ans << endl;
	}
	return 0;
}
