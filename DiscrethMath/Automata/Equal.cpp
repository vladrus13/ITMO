#define _CRT_SECURE_NO_WARNINGS
#include <iostream>
#include <algorithm>
#include <vector>
#include <cmath>
#include <deque>
#include <string>
#include <vector>
#include <cmath>
#include <queue>
#include <map>
#include <string>
#include <vector>
#include <set>
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

vector <vector <int> > g[2];
vector<bool> term[2];

int num_of_k = 0;

void read() {
	int n, m, k, a;
	cin >> n >> m >> k;
	term[num_of_k].resize(n + 1);
	for (int i = 0; i < k; i++) {
		cin >> a;
		a--;
		term[num_of_k][a] = true;
	}
	g[num_of_k].resize(n + 1, vector <int>(26, n));
	int v, u;
	char c;
	for (int i = 0; i < m; i++) {
		cin >> v >> u >> c;
		v--, u--, c -= 'a';
		g[num_of_k][v][c] = u;
	}
}

int main() {
	// begin of my usless prog
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("equivalence.in", "r", stdin);
	freopen("equivalence.out", "w", stdout);
#endif
	ios_base::sync_with_stdio(NULL);
	cin.tie(NULL);
	cout.tie(NULL);
	read();
	num_of_k = 1;
	read();
	// DEIKSTRAA
	queue < pair <int, int> > q;
	vector <vector <bool> > used;
	used.resize(g[0].size(), vector <bool> (g[1].size(), false));
	q.push({ 0, 0 });
	while (!q.empty()) {
		pair <int, int> &p = q.front();
		q.pop();
		int v = p.first;
		int u = p.second;
		if (term[0][v] != term[1][u]) {
			cout << "NO\n";
			return 0;
		}
		used[v][u] = true; 
		for (int i = 0; i < 26; i++) {
			if (((g[0][v][i] != g[0].size() - 1) || (g[1][u][i] != g[1].size() - 1)) && (!used[g[0][v][i]][g[1][u][i]])) {
				q.push({ g[0][v][i], g[1][u][i] });
			}
		}
	}
	cout << "YES\n";
	return 0;
}