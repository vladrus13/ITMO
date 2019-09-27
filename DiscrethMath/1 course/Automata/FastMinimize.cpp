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
	freopen("fastminimization.in", "r", stdin);
	freopen("fastminimization.out", "w", stdout);
#endif
	ios_base::sync_with_stdio(NULL);
	cin.tie(NULL);
	cout.tie(NULL);
	int n, m, k, t;
	cin >> n >> m >> k;
	g.resize(n + 1, vector <int>(26));
	r_g.resize(n + 1, vector <vector <int> >(26));
	vector <int> pointer;
	pointer.push_back(0);
	term.resize(n + 1);
	for (int i = 0; i < k; i++) {
		cin >> t;
		term[t] = true;
	}
	// HEEEEELP PLES HEEEEELP
	int u, v;
	char c;
	for (int i = 0; i < m; i++) {
		cin >> u >> v >> c;
		c -= 'a';
		g[u][c] = v;
		r_g[v][c].push_back(u);
	}
	// HEEEEELP PLES HEEEEELP
	used.resize(n + 1);
	comp.resize(n + 1);
	comp_dfs(1);
	used.clear();
	used.resize(n + 1);
	good.resize(n + 1);
	for (int i = 1; i <= n; i++) {
		if (term[i]) {
			dfs(i);
		}
	}
	// HEEEEELP PLES HEEEEELP
	vector <int> bie(n + 1);
	for (int i = 1; i <= n; i++) {
		if (comp[i] && good[i]) {
			bie[i] = pointer.size();
			pointer.push_back(i);
		}
	}
	// HEEEEELP PLES HEEEEELP
	vector <vector <int> > g_g(pointer.size(), vector <int>(26));
	vector <vector <vector <int> > > r_g_g(pointer.size(), vector <vector <int> >(26));
	for (int i = 1; i < pointer.size(); ++i) {
		for (int j = 0; j < 26; ++j) {
			if (good[g[pointer[i]][j]]) {
				g_g[i][j] = bie[g[pointer[i]][j]];
				r_g_g[bie[g[pointer[i]][j]]][j].push_back(i);
			}
		}
	}
	// HEEEEELP PLES HEEEEELP
	vector<bool> g_term(pointer.size());
	for (int i = 1; i < pointer.size(); ++i) {
		if (term[pointer[i]]) {
			g_term[bie[pointer[i]]] = true;
		}
	}
	// HEEEEELP PLES HEEEEELP
	n = pointer.size();
	if (n == 1) {
		cout << 0 << " " << 0 << " " << 0 << endl;
		return 0;
	}
	// HEEEEELP PLES HEEEEELP
	vector <set <int> > P;
	vector <int> Class(n);
	queue< pair <int, int> > Queue;
	set <int> temp;
	for (int i = 1; i < n; ++i) {
		if (g_term[i]) {
			Class[i] = 0;
			temp.insert(i);
		}
	}
	// HEEEEELP PLES HEEEEELP
	if (!temp.empty()) {
		P.push_back(temp);
	}
	// HEEEEELP PLES HEEEEELP
	set <int> temp2;
	for (int i = 1; i < n; ++i) {
		if (!g_term[i]) {
			Class[i] = 1;
			temp2.insert(i);
		}
	}
	// HEEEEELP PLES HEEEEELP
	if (!temp2.empty()) {
		P.push_back(temp2);
	}
	// HEEEEELP PLES HEEEEELP
	for (int i = 0; i < 26; ++i) {
		if (!temp.empty()) {
			Queue.push({ 0, i });
		}
		if (!temp2.empty()) {
			Queue.push({ 1, i });
		}
	}
	// HEEEEELP PLES HEEEEELP
	while (!Queue.empty()) {
		pair<int, int> ultra_temp = Queue.front();
		Queue.pop();
		map<int, vector <int>> Involved;
		for (int q : P[ultra_temp.first]) {
			for (int r : r_g_g[q][ultra_temp.second]) {
				int i = Class[r];
				if (Involved.find(i) == Involved.end()) {
					Involved.insert({ i, vector<int>() });
				}
				Involved.find(i).operator*().second.push_back(r);
			}
		}
		for (pair<int, vector<int>> ultra_part : Involved) {
			if (ultra_part.second.size() < P[ultra_part.first].size()) {
				P.push_back(set<int>());
				int j = P.size() - 1;
				for (int r : ultra_part.second) {
					P[ultra_part.first].erase(r);
					P[j].insert(r);
				}
				if (P[j].size() > P[ultra_part.first].size()) {
					swap(P[j], P[ultra_part.first]);
				}
				for (int iii : P[j]) {
					Class[iii] = j;
				}
				for (int i = 0; i < 26; ++i) {
					Queue.push({ j, i } );
				}
			}
		}
	}
	// HEEEEELP PLES HEEEEELP
	for (int i = 0; i < P.size(); ++i) {
		for (int t : P[i]) {
			Class[t] = i;
		}
	}
	// HEEEEELP PLES HEEEEELP
	if (Class[1] != 0) {
		int s = Class[1];
		for (int t : P[0]) {
			Class[t] = s;
		}
		for (int t : P[s]) {
			Class[t] = 0;
		}
		swap(P[0], P[s]);
	}
	// HEEEEELP PLES HEEEEELP
	int count_p = P.size();
	cout << count_p << " ";
	vector<bool> e_term(count_p);
	int count_termm = 0;
	vector<int> term_term;
	for (int i = 0; i < count_p; ++i) {
		for (int t : P[i]) {
			if (g_term[t]) {
				e_term[i] = true;
				term_term.push_back(i);
				count_termm++;
				break;
			}
		}
	}
	// HEEEEELP PLES HEEEEELP
	vector<vector<int>> f_g(count_p, vector<int>(26, -1));
	for (int i = 0; i < count_p; ++i) {
		for (int t : P[i]) {
			for (int j = 0; j < 26; ++j) {
				if (g_g[t][j] != 0) {
					f_g[i][j] = Class[g_g[t][j]];
				}
			}
		}
	}
	// HEEEEELP PLES HEEEEELP
	int count_g = 0;
	for (int i = 0; i < count_p; ++i) {
		for (int j = 0; j < 26; ++j) {
			if (f_g[i][j] != -1) {
				count_g++;
			}
		}
	}
	// HEEEEELP PLES HEEEEELP
	cout << count_g << " " << count_termm << endl;
	for (int t : term_term) {
		cout << t + 1 << " ";
	}
	cout << endl;
	for (int i = 0; i < count_p; ++i) {
		for (int j = 0; j < 26; ++j) {
			if (f_g[i][j] != -1) {
				cout << i + 1 << " " << f_g[i][j] + 1 << " " << (char) ('a' + j) << endl;
			}
		}
	}
	return 0;
}