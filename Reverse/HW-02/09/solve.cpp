/// what about the useless text at the beginning 
//                               of the program?
///                 I like to do useless things.
//       The program is made by vladrus13 (2018)

// Do you want to optimize? 
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
#include <set>
#include <queue>
#include <iomanip>
#include <bitset>
#include <cassert>
#include <random>
#include <stdexcept>
#include <stdio.h>

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

void check(string s) {
	int accum = 117;
	for (int i = 0; i < s.size(); i++) {
		accum = ((s[i] + 1) * accum) % 256;
	}
	if (accum == 118) {
		cout << s << endl;
	}
}

void rec(string s, int length) {
	if (length == 0) {
		check(s);
	} else {
		for (char i = 'a'; i <= 'z'; i++) {
			rec(s + i, length - 1);
		}
	}
}

int main() {
	// begin of my useless prog
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("nfc.in", "r", stdin);
	freopen("nfc.out", "w", stdout);
#endif
	ios_base::sync_with_stdio(NULL);
	cin.tie(NULL);
	cout.tie(NULL);
	for (int len = 1; len < 6; len++) {
		rec("", len);
	}
}