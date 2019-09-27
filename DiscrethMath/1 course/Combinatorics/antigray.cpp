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

string a;
int n;

void cyclic(){
	for (int i = 0; i < a.size(); i++) {
		if (a[i] == '2') a[i] = '0'; else
		if (a[i] == '1') a[i] = '2'; else
		if (a[i] == '0') a[i] = '1';
	}
}

void rec() {
	if (n == a.size() + 1) {
		a = "0" + a;
		cout << a << endl;
		cyclic();
		cout << a << endl;
		cyclic();
		cout << a << endl;
		cyclic();
		a.erase(a.begin());
	} else {
		for (int i = '0'; i < '3'; i++) {
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
	freopen("antigray.in", "r", stdin);
	freopen("antigray.out", "w", stdout);
#endif
	cin >> n;
	rec();
	return 0;
}
