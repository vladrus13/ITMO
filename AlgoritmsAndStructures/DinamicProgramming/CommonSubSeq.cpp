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

string s1, s2, rs1, rs2;

vector <int> local_answer, temp, l1, l2;

class sub {
 public:
	int i, j;

	sub(int x, int y) {
		i = x;
		j = y;
	}

	int size() {
		return j - i + 1;
	}

	void reverse(int s) {
		i = s - i - 1;
		j = s - j - 1;
		swap(i, j);
	}
};

void local_fill_n(sub x, sub y) {
	local_answer.clear();
	local_answer.resize((int) y.size() + 1);
	for (int i = x.i; i <= x.j; i++) {
		temp = local_answer;
		for (int j = y.i; j <= y.j; j++) {
			if (s1[i] == s2[j]) {
				local_answer[j + 1 - y.i] = temp[j - y.i] + 1;
			} else {
				local_answer[j + 1 - y.i] = max(local_answer[j - y.i], temp[j + 1 - y.i]);
			}
		}
	}
}

void local_fill_r(sub x, sub y) {
	x.reverse((int) s1.size());
	y.reverse((int) s2.size());
	local_answer.clear();
	local_answer.resize((int) y.size() + 1);
	for (int i = x.i; i <= x.j; i++) {
		temp = local_answer;
		for (int j = y.i; j <= y.j; j++) {
			if (rs1[i] == rs2[j]) {
				local_answer[j + 1 - y.i] = temp[j - y.i] + 1;
			} else {
				local_answer[j + 1 - y.i] = max(local_answer[j - y.i], temp[j + 1 - y.i]);
			}
		}
	}
}

string chtostr(char ch) {
	return string(1, ch);
}

string lcs(sub x, sub y) {
	int n = x.size();
	if (n <= 0 || y.size() < 0) {
		return "";
	}
	if (n == 1) {
		for (int i = y.i; i <= y.j; i++) {
			if (s1[x.i] == s2[i]) {
				return chtostr(s1[x.i]);
			}
		}
		return "";
	}
	int i = n / 2;
	sub xb = sub(x.i, x.i + i - 1), xe = sub(x.i + i, x.j);
	local_fill_n(xb, y);
	l1 = local_answer;
	local_fill_r(xe, y);
	l2 = local_answer;
	reverse(l2.begin(), l2.end());
	int maximum = -1, pos = 0;
	for (int k = 0; k < (int) l1.size(); k++) {
		if (l1[k] + l2[k] > maximum) {
			pos = k;
			maximum = l1[k] + l2[k];
		}
	}
	sub yb = sub(y.i, y.i + pos - 1), ye = sub(y.i + pos, y.j);
	return lcs(xb, yb) + lcs(xe, ye);
}

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	// freopen("matching.in", "r", stdin);
	// freopen("matching.out", "w", stdout);
#endif
	cin >> s1 >> s2;
	if (s1.size() > s2.size()) swap(s1, s2);
	rs1 = s1;
	rs2 = s2;
	reverse(rs1.begin(), rs1.end());
	reverse(rs2.begin(), rs2.end());
	cout << lcs(sub(0, (int) s1.size() - 1), sub(0, (int) s2.size() - 1));
	return 0;
}
