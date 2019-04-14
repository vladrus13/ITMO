#define _CRT_SECURE_NO_WARNINGS
#include <iostream>
#include <algorithm>
#include <vector>
#include <cmath>
#include <deque>
#include <string>
#include <vector>
#include <iostream>
#include <cmath>
#include <queue>
#include <iostream>
#include <map>
#include <string>
#include <vector>
#include <algorithm>
#include <set>
#include <iostream>
#include <vector>
#include <algorithm>
#include <iostream>
#include <string>
#include <vector>
#include <algorithm>
#include <queue>
#include <iostream>
#include <string>
#include <vector>
#include <algorithm>
#include <queue>
#include <iomanip>
#include <bitset>
#include <cassert>

typedef long double ld;
typedef long long ll;
typedef unsigned long long ull;

using namespace std;
//#pragma comment (linker, "/STACK:5000000000")
// #define INF (int)2e9;

const int INF = (int) 2e9;

//////////////////////////////////////////////////////////////////
// SYSTEM STARTS

//////////////////////////////////////////////////////////////////

/////////////////////////////////////////////////////////////////
// MAIN

ld f[101][101], s[101][101];
int n;

void read() {
	cin >> n;
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++) {
			cin >> f[i][j];
		}
	}
}

void da_solve() {
	bool flag;
	while (true) {
		flag = true;
		memset(s, 0, sizeof s);
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				for (int k = 0; k < n; k++)
					s[i][j] += f[i][k] * f[k][j];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {
				if (abs(f[i][j] - s[i][j]) > 1e-4)
					flag = false;
				f[i][j] = s[i][j];
			}
		if (flag) break;
	}
}

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("markchain.in", "r", stdin);
	freopen("markchain.out", "w", stdout);
#endif
	ios_base::sync_with_stdio(NULL);
	cin.tie(NULL);
	cout.tie(NULL);
	read();
	da_solve();
	cout << fixed << setprecision(30);
	for (int i = 0; i < n; i++) 
		cout << (ld) f[0][i] << '\n';
	return 0;
}