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

string exec(const char* cmd) {
	char buffer[128];
	std::string result = "";
	FILE* pipe = _popen(cmd, "r");
	if (!pipe) throw std::runtime_error("popen() failed!");
	try {
		while (fgets(buffer, sizeof buffer, pipe) != NULL) {
			result += buffer;
		}
	} catch (...) {
		_pclose(pipe);
		throw;
	}
	_pclose(pipe);
	return result;
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
	string task2 = "D:\\Vl\\University\\Reverse\\15\\task2.exe";
	for (char a = 'a'; a <= 'z'; a++) {
		for (char b = 'a'; b <= 'z'; b++) {
			cout << a << b << " --- " << exec((task2 + ' ' + a + ' ' + b).c_str()) << endl;
		}
	}
}