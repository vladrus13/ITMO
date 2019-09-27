/// what about the useless text at the beginning 
//                               of the program?
///                 I like to do useless things.
//       The program is made by Vladislav (2018)

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
typedef int64_t ll;
typedef uint64_t ull;
typedef int32_t int32;

using namespace std;
//#pragma comment (linker, "/STACK:5000000000")
#define INF (int)2e9;
#define MOD (int)1e9+7;

//////////////////////////////////////////////////////////////////
// SYSTEM STARTS

//////////////////////////////////////////////////////////////////
// UTILS

/////////////////////////////////////////////////////////////////
// MAIN

vector <vector <vector <int> > > r_g;
vector <vector <int> > g;
vector <bool> term, good, used, comp;

void dfs(int v) {
	if (used[v]) return;
	used[v] = true;
	if (!comp[v]) {
		good[v] = false;
		return;
	}
	good[v] = true;
	for (int i = 0; i < 26; i++) {
		for (int it : r_g[v][i]) {
			dfs(it);
		}
	}
}

void comp_dfs(int v) {
	if (used[v]) return;
	used[v] = true;
	comp[v] = true;
	for (int i = 0; i < 26; i++) {
		comp_dfs(g[v][i]);
	}
}

int main() {
	// begin of my useless prog
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("minimization.in", "r", stdin);
	freopen("minimization.out", "w", stdout);
#endif
	ios_base::sync_with_stdio(NULL);
	cin.tie(NULL);
	cout.tie(NULL);
	int n, m, k, t;
	cin >> n >> m >> k;
	g.resize(n + 1, vector <int>(26));
	r_g.resize(n + 1, vector <vector <int> >(26));
	term.resize(n + 1);
	for (int i = 0; i < k; i++) {
		cin >> t;
		term[t] = true;
	}
	int u, v;
	char c;
	for (int i = 0; i < m; i++) {
		cin >> u >> v >> c;
		c -= 'a';
		g[u][c] = v;
		r_g[v][c].push_back(u);
	}
	used.resize(n + 1);
	comp.resize(n + 1);
	comp_dfs(1);
	used.clear();
	used.resize(n + 1);
	good.resize(n + 1);
	for (int i = 0; i <= n; i++) {
		if (term[i]) {
			dfs(i);
		}
	}
	if (comp[0]) {
		for (int i = 0; i <= n; i++) {
			for (char c = 0; c < 26; c++) { // c++ ahah... not funny :(
				if (!g[i][c] && comp[i]) {
					r_g[0][c].push_back(i);
				}
			}
		}
	}
	vector <vector <bool> > ultra_used(n + 1, vector <bool>(n + 1, false));
	queue <pair <int, int> > q;
	for (int i = 0; i <= n; i++) {
		for (int j = 0; j <= n; j++) {
			if (!ultra_used[i][j] && (term[i] != term[j])) {
				ultra_used[i][j] = (ultra_used[j][i] = true);
				q.push({ i, j });
			}
		}
	}
	while (!q.empty()) {
		pair <int, int> p = q.front();
		q.pop();
		for (int i = 0; i < 26; i++) {
			for (int it : r_g[p.first][i]) {
				for (int it2 : r_g[p.second][i]) {
					if (!ultra_used[it][it2]) {
						ultra_used[it][it2] = (ultra_used[it2][it] = true);
						q.push({ it, it2 });
					}
				}
			}
		}
	}
	vector <int> compon(n + 1, -1);
	for (int i = 0; i <= n; i++) {
		if (!ultra_used[0][i]) {
			compon[i] = 0;
		}
	}
	int count_compon = 0;
	for (int i = 1; i <= n; i++) {
		if (!comp[i]) continue;
		if (compon[i] == -1) {
			count_compon++;
			compon[i] = count_compon;
			for (int j = i + 1; j <= n; j++) {
				if (!ultra_used[i][j]) {
					compon[j] = count_compon;
				}
			}
		}
	}
	cout << count_compon << " ";
	vector<vector<int>> new_g(count_compon + 1, vector<int>(26));
	for (int i = 1; i <= n; i++) {
		for (int j = 0; j < 26; j++) {
			if (good[i] && g[i][j] != 0 && compon[i] >= 1 && compon[i] <= count_compon) {
				new_g[compon[i]][j] = compon[g[i][j]];
			}
		}
	}
	int numbers_of_moves = 0;
	for (int i = 1; i <= count_compon; ++i) {
		for (int j = 0; j < 26; ++j) {
			if (new_g[i][j] != 0) {
				numbers_of_moves++;
			}
		}
	}
	cout << numbers_of_moves << " ";
	vector<bool> new_term(count_compon + 1);
	for (int i = 1; i <= n; ++i) {
		if (term[i] && good[i]) {
			new_term[compon[i]] = true;
		}
	}
	int termi = 0;
	for (int i = 1; i <= count_compon; ++i) {
		if (new_term[i]) {
			termi++;
		}
	}
	cout << termi << endl;
	for (int i = 1; i <= count_compon; ++i) {
		if (new_term[i]) {
			cout << i << " ";
		}
	}
	cout << endl;
	for (int i = 1; i <= count_compon; ++i) {
		for (int j = 0; j < 26; ++j) {
			if (new_g[i][j] != 0) {
				cout << i << " " << new_g[i][j] << " " << (char) (j + 'a') << endl;
			}
		}
	}
	return 0;
}