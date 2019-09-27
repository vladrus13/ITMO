/// what about the useless text at the beginning 
//                               of the program?
///       I like to do useless things.
// the program is made by Vladislav (2018)

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

vector <vector <int> > moves, reverse_moves;
vector <bool> terminal, good, visited, connected, cycled;
vector <ll> answer;

ll get_ans(int n) {
	ll answer = terminal[n] ? 1 : 0;
	for (int i = 0; i < 26; ++i) {
		if (moves[n][i] != 0 && good[n]) {
			answer = (answer + get_ans(moves[n][i])) % 1000000007;
		}
	}
	return answer;
}

void componize(int n) {
	if (visited[n]) {
		return;
	} else {
		visited[n] = true;
		connected[n] = true;
		for (int i = 0; i < 26; ++i) {
			if (moves[n][i] != 0) {
				componize(moves[n][i]);
			}
		}
	}
}

void is_good_travel(int n) {
	if (visited[n]) {
		return;
	}
	visited[n] = true;
	good[n] = true;
	for (int i = 0; i < 26; ++i) {
		if (reverse_moves[n][i] != 0) {
			is_good_travel(reverse_moves[n][i]);
		}
	}
}

bool endless(int n) {
	if (visited[n]) {
		return false;
	}
	visited[n] = true;
	cycled[n] = true;
	bool result = false;
	for (int i = 0; i < 26; ++i) {
		if (moves[n][i] != 0) {
			if (good[moves[n][i]] && cycled[moves[n][i]]) {
				return true;
			}
			result = result || endless(moves[n][i]);
			if (result) {
				return true;
			}
		}
	}
	cycled[n] = false;
	return result;
}

int main() {
	// begin of my useless prog
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("problem3.in", "r", stdin);
	freopen("problem3.out", "w", stdout);
#endif
	ios_base::sync_with_stdio(NULL);
	cin.tie(NULL);
	cout.tie(NULL);
	int n, m, k;
	cin >> n >> m >> k;
	terminal.resize(n + 1);
	for (int i = 0; i < k; ++i) {
		int t;
		cin >> t;
		terminal[t] = true;
	}
	moves.resize(n + 1, vector <int>(26));
	reverse_moves.resize(n + 1, vector <int>(26));
	for (int i = 0; i < m; ++i) {
		int a, b;
		char c;
		cin >> a >> b >> c;
		moves[a][c - 'a'] = b;
		reverse_moves[b][c - 'a'] = a;
	}
	good.resize(n + 1);
	visited.resize(n + 1);
	for (int i = 1; i <= n; ++i) {
		if (terminal[i])
			is_good_travel(i);
	}
	visited = vector<bool>(n + 1);
	cycled.resize(n + 1);
	if (endless(1)) {
		cout << -1 << endl;
		return 0;
	}
	connected.resize(n + 1);
	visited = vector<bool>(n + 1);
	componize(1);
	visited = vector<bool>(n + 1);
	visited[1] = true;
	answer.resize(n + 1);
	answer[1] = 1;
	cout << get_ans(1) << endl;
	return 0;
}