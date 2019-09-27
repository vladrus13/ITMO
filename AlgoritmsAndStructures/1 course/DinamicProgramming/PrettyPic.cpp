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

ll MOD;
int m;

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
					ans[i][j] %= MOD;
				}
			}
		}
		matrix answer(ans);
		return answer;
	}
};

vector <ll> power;

void power_up() {
	power.resize(8);
	power[0] = 1;
	for (int i = 1; i < 8; i++) power[i] = power[i - 1] * 2;
}

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
	if (pov == "0") return oneable((int) power[m]);
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

string inttostr_2(int n) {
	string s = string((int) m, '0');
	int it = (int) s.size() - 1;
	while (n) {
		if (n % 2) {
			s[it] = '1';
		}
		n /= 2;
		it--;
	}
	return s;
}

bool conf(string s1, string s2) {
	for (int i = 1; i < (int) s1.size(); i++) {
		if (s1[i] == s1[i - 1] && s1[i] == s2[i] && s1[i] == s2[i - 1]) {
			return false;
		}
	}
	return true;
}

matrix build_map() {
	vector <vector <ll> > rr((int) power[m], vector <ll>((int) power[m], 0));
	for (int i = 0; i < (int) power[m]; i++) {
		for (int j = 0; j < (int) power[m]; j++) {
			if (conf(inttostr_2(i), inttostr_2(j))) {
				rr[i][j] = 1;
			}
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
	freopen("nice3.in", "r", stdin);
	freopen("nice3.out", "w", stdout);
#endif
	string n;
	power_up();
	cin >> n >> m >> MOD;
	minus_1(n);
	matrix big = build_map();
	matrix st(vector <vector <ll> >(1, vector <ll>((int) power[m], 1)));
	matrix go = st.multiply(multi_bin(big, n));
	ll ans = 0;
	for (int i = 0; i < (int) power[m]; i++) {
		ans += go.a[0][i];
	}
	ans %= MOD;
	cout << ans << endl;
	return 0;
}
