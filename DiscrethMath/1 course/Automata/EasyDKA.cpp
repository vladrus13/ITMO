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
#include <random>

typedef long double ld;
typedef long long ll;
typedef unsigned long long ull;

using namespace std;
//#pragma comment (linker, "/STACK:5000000000")
#define INF (int)2e9;

//////////////////////////////////////////////////////////////////
// SYSTEM STARTS

//////////////////////////////////////////////////////////////////
// UTILS

/////////////////////////////////////////////////////////////////
// MAIN

vector <vector <pair <int, char> > > g;
vector <bool> endi;
string s;

bool dfs(int v, int i) {
	if (i == s.size()) {
		return endi[v];
	} else {
		for (pair <int, char> it : g[v]) {
			if (it.second == s[i]) {
				return dfs(it.first, i + 1);
			}
		}
		return false;
	}
}

int main() {
	// begin of my usless prog
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("problem1.in", "r", stdin);
	freopen("problem1.out", "w", stdout);
#endif
	ios_base::sync_with_stdio(NULL);
	cin.tie(NULL);
	cout.tie(NULL);
	int n, m, k, u, v;
	char c;
	cin >> s >> n >> m >> k;
	g.resize(n);
	endi.resize(n);
	for (int i = 0; i < k; i++) {
		cin >> u;
		u--;
		endi[u] = true;
	}
	for (int i = 0; i < m; i++) {
		cin >> u >> v >> c;
		u--, v--;
		g[u].push_back({ v, c });
	}
	cout << (dfs(0, 0) ? "Accepts" : "Rejects");
	return 0;
}