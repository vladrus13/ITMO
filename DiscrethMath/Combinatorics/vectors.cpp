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

vector <int> a;
vector < vector <int> > answer;
int n;

void rec() {
	if (a.size() == n) {
		answer.push_back(a);
	} else {
		for (int i = 0; i < 2; i++) {
			if (i != 1 || (i == 1 && (!a.size() || a.back() != 1))) {
				a.push_back(i);
				rec();
				a.pop_back();
			}
		}
	}
}

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("vectors.in", "r", stdin);
	freopen("vectors.out", "w", stdout);
#endif
	cin >> n;
	rec();
	cout << answer.size() << endl;
	for (int i = 0; i < answer.size(); i++) {
		for (int j = 0; j < answer[i].size(); j++) {
			cout << answer[i][j];
		}
		cout << endl;
	}
	return 0;
}
