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
typedef long long ll;
typedef unsigned long long ull;

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

vector <vector <vector <int> > > g, Ng;
vector <bool> term, Nterm;

void Tompson() {
	g.resize(100, vector <vector <int> >(26));
	int set_count = 1;
	map <set <int>, int> sets;
	queue <set <int> > Q;
	set <int> q;
	q.insert(0);
	sets.insert({ q, 0 });
	set_count++;
	Q.push(q);
	while (!Q.empty()) {
		q = Q.front();
		Q.pop();
		bool isTerm = false;
		for (int i : q) {
			if (Nterm[i]) {
				isTerm = true;
				break;
			}
		}
		term[sets.find(q)->second] = isTerm;
		for (int i = 0; i < 26; ++i) {
			set<int> tmp;
			for (int j : q) {
				for (int dest : Ng[j][i]) {
					tmp.insert(dest);
				}
			}
			if (sets.find(tmp) != sets.end()) {
				g[sets.find(q)->second][i].push_back(sets.find(tmp)->second);
			} else if (!tmp.empty()) {
				Q.push(tmp);
				sets.insert({ tmp, set_count });
				g[sets.find(q)->second][i].push_back(set_count++);
			}
		}
	}
}

ll dp_counter(int l) {
	vector <int> curr(100), next(100);
	curr[0] = 1;
	for (int i = 0; i < l; i++) {
		for (int j = 0; j < 100; j++) {
			for (int c = 0; c < 26; c++) {
				for (int f = 0; f < g[j][c].size(); f++) {
					next[g[j][c][f]] += curr[j];
					next[g[j][c][f]] %= MOD;
				}
			}
		}
		curr = next;
		next.clear();
		next.resize(100, 0);
	}
	int ans = 0;
	for (int i = 0; i < 100; i++) {
		ans += (term[i] ? curr[i] : 0);
		ans %= MOD;
	}
	return ans;
}

int main() {
	// begin of my useless prog
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("problem5.in", "r", stdin);
	freopen("problem5.out", "w", stdout);
#endif
	ios_base::sync_with_stdio(NULL);
	cin.tie(NULL);
	cout.tie(NULL);
	int n, m, k, l, t;
	cin >> n >> m >> k >> l;
	term.resize(100, false);
	Nterm.resize(n, false);
	for (int i = 0; i < k; i++) {
		cin >> t;
		t--;
		Nterm[t] = true;
	}
	Ng.resize(n, vector <vector <int> >(26));
	int v, u;
	char c;
	for (int i = 0; i < m; i++) {
		cin >> v >> u >> c;
		v--, u--, c -= 'a';
		Ng[v][c].push_back(u);
	}
	Tompson();
	cout << dp_counter(l) << endl;
	return 0;
}