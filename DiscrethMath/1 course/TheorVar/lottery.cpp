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

int n, m;
ld ans = 0, p = 1, r = 0, x, y;

void read() {
	cin >> n >> m;
	for (int i = 0; i < m; i++) {
		cin >> x >> y;
		ans += (ld) r * (ld) p * ((ld) 1 - 1 / (ld) x);
		r = y;
		p /= x;
	}
	ans += (ld) r * (ld) p;
}


int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("lottery.in", "r", stdin);
	freopen("lottery.out", "w", stdout);
#endif
	ios_base::sync_with_stdio(NULL);
	cin.tie(NULL);
	cout.tie(NULL);
	read();
	cout << fixed << setprecision(30) << ((ld) n - ans);
	return 0;
}