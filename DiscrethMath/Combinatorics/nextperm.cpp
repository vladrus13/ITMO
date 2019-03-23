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

int n;
vector <int> a_copy, a;

void prev() {
	for (int i = n - 2; i >= 0; i--) {
		if (a[i] > a[i + 1]) {
			int pos = i + 1;
			for (int j = i + 1; j < n; j++) {
				if (a[j] > a[pos] && a[j] < a[i]) {
					pos = j;
				}
			}
			swap(a[i], a[pos]);
			for (int j = 0; j < (n - i - 2) / 2 + 1; j++) {
				swap(a[i + j + 1], a[n - j - 1]);
			}
			for (int j = 0; j < n; j++) {
				cout << a[j] << ' ';
			}
			return;
		}
	}
	for (int i = 0; i < n; i++) {
		cout << "0 ";
	}
}

void next() {
	for (int i = n - 2; i >= 0; i--) {
		if (a[i] < a[i + 1]) {
			int pos = i + 1;
			for (int j = i + 1; j < n; j++) {
				if (a[j] < a[pos] && a[j] > a[i]) {
					pos = j;
				}
			}
			swap(a[i], a[pos]);
			for (int j = 0; j < (n - i - 2) / 2 + 1; j++) {
				swap(a[i + j + 1], a[n - j - 1]);
			}
			for (int j = 0; j < n; j++) {
				cout << a[j] << ' ';
			}
			return;
		}
	}
	for (int i = 0; i < n; i++) {
		cout << "0 ";
	}
}

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("nextperm.in", "r", stdin);
	freopen("nextperm.out", "w", stdout);
#endif
	cin >> n;
	a_copy.resize(n);
	for (int i = 0; i < n; i++) {
		cin >> a_copy[i];
	}
	a = a_copy;
	prev();
	cout << endl;
	a = a_copy;
	next();
	return 0;
}