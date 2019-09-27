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

vector<int> hh[2];
bool dfs(int v, int u, int h) {
	if (term[0][v] && !term[1][u] || !term[0][v] && term[1][u]) return false;
	if (hh[1][u] != -1 && hh[0][v] != -1) return true;
	if (hh[1][u] == -1 && hh[0][v] == -1) {
		hh[0][v] = h;
		hh[1][u] = h;
		bool returned = true;
		for (char i = 0; i < 26; i++) {
			if (g[0][v][i] != -1 && g[1][u][i] != -1) {
				returned &= dfs(g[0][v][i], g[1][u][i], h + 1);
			} else if (g[0][v][i] == -1 && g[1][u][i] == -1) {
				continue;
			} else {
				returned = false;
			}
		}
		return returned;
	} else
		return false;
}

int num_of_k = 0;

void read() {
	int n, m, k;
	cin >> n >> m >> k;
	term[num_of_k].resize(n);
	for (int i = 0; i < k; i++) {
		int a;
		cin >> a;
		a--;
		term[num_of_k][a] = true;
	}
	g[num_of_k].resize(n, vector <int>(26, -1));
	int v, u;
	char c;
	for (int i = 0; i < m; i++) {
		cin >> v >> u >> c;
		v--, u--, c -= 'a';
		g[num_of_k][v][c] = u;
	}
	hh[num_of_k].resize(n, -1);
}

int main() {
	// begin of my usless prog
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("isomorphism.in", "r", stdin);
	freopen("isomorphism.out", "w", stdout);
#endif
	ios_base::sync_with_stdio(NULL);
	cin.tie(NULL);
	cout.tie(NULL);
	read();
	num_of_k = 1;
	read();
	cout << (dfs(0, 0, 0) ? "YES" : "NO");
	return 0;
}