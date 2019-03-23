#define _CRT_SECURE_NO_WARNINGS
#include <iostream>
#include <string>
#include <vector>
#include <map>
#include <algorithm>
#include <cmath>
#include <set>
#include <iomanip>

using namespace std;

typedef long long ll;
typedef long double ld;
typedef unsigned int ui;
typedef unsigned long long ull;
int v[100000][2], ballet[100000][2];
int cetta[100000];
int n;
ui a, b;

ui cur = 0;
ui nextRand24() {
	cur = cur * a + b;
	return cur >> 8;
}

ui nextRand32() {
	ui a = nextRand24(), b = nextRand24();
	return (a << 8) ^ b;
}

int global_answer[2];

void transfor(ui a) {
	global_answer[0] = a / 100000;
	global_answer[1] = a % 100000;
}

ll untransfor(int* a) {
	ll answer = 0;
	answer += ((ll) a[0]) * 100000;
	answer += ((ll) a[1]);
	return answer;
}

int counter = 0, temp = 0;

void radix_sort() {
	for (int i = 1; i >= 0; i--) {
		for (int j = 0; j < 100000; j++) {
			cetta[j] = 0;
		}
		for (int j = 0; j < n; j++) {
			cetta[v[j][i]]++;
		}
		counter = 0;
		for (int j = 0; j < 100000; j++) {
			temp = cetta[j];
			cetta[j] = counter;
			counter += temp;
		}
		for (int j = 0; j < n; j++) {
			for (int kk = 0; kk < 2; kk++) {
				ballet[cetta[v[j][i]]][kk] = v[j][kk];
			}
			cetta[v[j][i]]++;
		}
		for (int j = 0; j < n; j++) {
			for (int kk = 0; kk < 2; kk++) {
				v[j][kk] = ballet[j][kk];
			}
		}
	}
}

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("buckets.in", "r", stdin);
	freopen("buckets.out", "w", stdout);
#endif
	int t;
	ui x;
	cin >> t >> n;
	while (t--) {
		cin >> a >> b;
		for (int i = 0; i < n; i++) {
			x = nextRand32();
			transfor(x);
			for (int j = 0; j < 2; j++) {
				v[i][j] = global_answer[j];
			}
		}
		radix_sort();
		ull answer = 0;
		for (int i = 0; i < n; i++) {
			answer += (i + 1) * untransfor(v[i]);
		}
		cout << answer << endl;
	}
}
