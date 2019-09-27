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

int main() {
	// begin of my usless prog
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("problem2.in", "r", stdin);
	freopen("problem2.out", "w", stdout);
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
	// If TL on DFS
	// use ML on BFS
	// (c)
	set <int> vertex;
	set <int> next_vertex;
	vertex.insert(0);
	for (int s_length = 0; s_length < (int) s.size(); s_length++) {
		for (int v : vertex) {
			for (pair <int, char> it : g[v]) {
				if (it.second == s[s_length]) {
					next_vertex.insert(it.first);
				}
			}
		}
		vertex = next_vertex;
		next_vertex.clear();
	}
	for (int i : vertex) {
		if (endi[i]) {
			cout << "Accepts\n";
			return 0;
		}
	}
	cout << "Rejects\n";
	return 0;
}