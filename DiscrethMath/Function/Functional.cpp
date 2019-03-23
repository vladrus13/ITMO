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

using namespace std;

typedef long long ll;
typedef long double ld;
typedef unsigned int ui;
typedef unsigned long long ull;

class bin {
private:
	string s;
public:
	bin() {
		this->s = "";
	}
	bin(string s) {
		this->s = s;
	}

	int size() {
		return s.size();
	}

	const bin operator not() {
		bin answer(string(s.size(), 0));
		for (int i = 0; i < s.size(); i++) {
			answer.s[i] = (s[i] == '0' ? '1' : '0');
		}
		return answer;
	}

	int diff_byte(bin a, bin b) {
		int answer = 0;
		for (int i = 0; i < a.size(); i++) {
			answer += (a.s[i] != b.s[i] ? 1 : 0);
		}
		return answer;
	}

	char get(int i) {
		return s[i];
	}

	string value() {
		return s;
	}
};

bool save_0(bin a) {
	return a.get(0) == '0';
}

bool save_1(bin a) {
	return a.get(a.size() - 1) == '1';
}

bool self_dual(int s, bin str) {
	for (int i = 0; i < str.size(); i++) {
		int q = i;

		for (int j = 0; j < s; j++) {
			q = q ^ (1 << j);
		}

		if (str.get(i) == str.get(q))
			return false;
	}

	return true;
}

bool linear(int s, bin str) {
	int ans[33];
	int lastZeg[33];
	int newZeg[33];

	fill(ans, ans + 33, 0);
	fill(lastZeg, lastZeg + 33, 0);
	fill(newZeg, newZeg + 33, 0);

	for (int i = 0; i < (1 << s); i++) {
		lastZeg[i] = int(str.get(i) - '0');
	}
	ans[0] = lastZeg[0];
	for (int i = 1; i < (1 << s); i++) {
		for (int j = 0; j < (1 << s) - i; j++) {
			newZeg[j] = (lastZeg[j] ^ lastZeg[j + 1]);
		}
		ans[i] = newZeg[0];
		swap(lastZeg, newZeg);
	}

	for (int i = 1; i < (1 << s); i++) {
		if (ans[i] == 0)
			continue;
		int num = i;
		while (num % 2 == 0) {
			num /= 2;
		}

		if (num != 1)
			return false;
	}

	return true;
}

bool mono(int s, bin str) {
	for (int i = 0; i < str.size(); i++) {
		if (str.get(i) == '0')
			continue;
		int q = i;
		for (int j = 0; j < s; j++) {
			if ((q & (1 << j)) != 0)
				continue;
			int tmp = q;
			tmp ^= (1 << j);
			if (str.get(tmp) == '0')
				return false;
		}
	}
	return true;
}

map <string, bin> cc;

void find_the_function(int pow, bin a) {
	cout << a.value() << "  ";
	string s = "" + ((save_0(a) == true) ? string("1") : string("0")) + 
		((save_1(a) == true) ? string("1") : string("0")) +
		((mono(pow, a) == true ) ? string("1") : string("0")) +
		((self_dual(pow, a) == true) ? string("1") : string("0")) +
		((linear(pow, a) == true) ? string("1") : string("0"));
	cc[s] = (cc.count(s) ? cc[s] : a);
	cout << (save_0(a) == true ? "    save_0   " : "not_save_0   ");
	cout << (save_1(a) == true ? "    save_1   " : "not_save_1   ");
	cout << (mono(pow, a) == true ? "    mono   " : "not_mono   ");
	cout << (self_dual(pow, a) == true ? "    self-dual   " : "not_self-dual   ");
	cout << (linear(pow, a) == true ? "    linear" : "not_linear");
	cout << endl;
}

vector <int> a;
int n = 0;

string vector_to_string(vector <int> a) {
	string ans = "";
	ans.resize(a.size(), 0);
	for (int i = 0; i < a.size(); i++) {
		ans[i] = (a[i] == 0 ? '0' : '1');
	}
	return ans;
}

void rec() {
	if (a.size() == pow(2, n)) {
		find_the_function(n, bin(vector_to_string(a)));
	} else {
		for (int i = 0; i < 2; i++) {
			a.push_back(i);
			rec();
			a.pop_back();
		}
	}
}

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("part2sets.in", "r", stdin);
	freopen("part2sets.out", "w", stdout);
#endif
	for (n = 0; n < 5; n++) {
		cout << endl << endl << string(100, '-') << endl << "N = " << n << endl << endl;
		rec();
		cout << endl << endl;
	}
	ofstream out("finder.txt");
	string s;
	while (cin >> s) {
		out << s << "        " << ((cc.count(s)) ? cc[s].value() : string("NOT FOUND"));
		out << endl;
	}
	return 0;
}