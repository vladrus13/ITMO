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

using namespace std;

typedef long long ll;
typedef long double ld;
typedef unsigned int ui;
typedef unsigned long long ull;

set <vector <int> > answer;
vector <int> a;

int depth = 0;

int get_bit(int v, int i) {
	return (v >> i) % 2;
}

int size(int v) {
	return (v & (v - 1)) != 0;
}

void rec() {
	if (depth > 5000000) return;
	if (answer.count(a)) return;
	answer.insert(a);
	for (int i = 0; i < a.size(); i++) {
		for (int j = 0; j < a.size(); j++) {
			if (i != j) {
				if (size(a[i])) {
					for (int kk = 0; kk < 10; kk++) {
						if (get_bit(a[i], kk) == 1) {
							int temp = a[i];
							vector <int> restore = a;
							a[i] = 0;
							for (int l = 0; l < 10; l++) {
								if (kk != l) {
									a[i] += (int) pow(2, l) * get_bit(temp, l);
								}
							}
							a[j] += (int) pow(2, kk);
							depth++;
							sort(a.begin(), a.end());
							rec();
							a = restore;
						}
					}
				}
			}
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
	int n, k;
	cin >> n >> k;
	a.resize(k);
	for (int i = 0; i < k; i++) {
		a[i] += (int) pow(2, i);
	}
	for (int i = k; i < n; i++) {
		a[k - 1] += (int) pow(2, i);
	}
	rec();
	for (vector <int> ans : answer) {
		for (int i = 0; i < ans.size(); i++) {
			for (int j = 0; j < 10; j++) {
				if (ans[i] % 2) {
					cout << j + 1 << ' ';
				}
				ans[i] /= 2;
			}
			cout << endl;
		}
		cout << endl;
	}
	return 0;
}
