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

long long adooha(unsigned int a1) {
	unsigned int v1; // edx

	v1 = (((16
			* (((4 * (((2 * (a1 & 0x55555555)) | (a1 >> 1) & 0x55555555) & 0x33333333)) | (((2 * (a1 & 0x55555555)) | (a1 >> 1) & 0x55555555) >> 2) & 0x33333333) & 0xF0F0F0F)) | (((4 * (((2 * (a1 & 0x55555555)) | (a1 >> 1) & 0x55555555) & 0x33333333)) | (((2 * (a1 & 0x55555555)) | (a1 >> 1) & 0x55555555) >> 2) & 0x33333333) >> 4) & 0xF0F0F0F) >> 8) & 0xFF00 | ((((unsigned __int16) (16 * (((4 * (((2 * (a1 & 0x5555)) | (a1 >> 1) & 0x5555) & 0x3333)) | (((2 * (a1 & 0x55555555)) | (a1 >> 1) & 0x55555555) >> 2) & 0x3333) & 0xF0F)) | (((4 * (((2 * (a1 & 0x55555555)) | (a1 >> 1) & 0x55555555) & 0x33333333)) | (((2 * (a1 & 0x55555555)) | (a1 >> 1) & 0x55555555) >> 2) & 0x33333333) >> 4) & 0xF0F) & 0xFF00) << 8) | (((16 * (((4 * (((2 * (a1 & 0x55555555)) | (a1 >> 1) & 0x55555555) & 0x33333333)) | (((2 * (a1 & 0x55555555)) | (a1 >> 1) & 0x55555555) >> 2) & 0x33333333) & 0xF0F0F0F)) | (((4 * (((2 * (a1 & 0x55555555)) | (a1 >> 1) & 0x55555555) & 0x33333333)) | (((2 * (a1 & 0x55555555)) | (a1 >> 1) & 0x55555555) >> 2) & 0x33333333) >> 4) & 0xF0F0F0F) << 24);
	return v1 | (((16
				   * (((4 * (((2 * (a1 & 0x55555555)) | (a1 >> 1) & 0x55555555) & 0x33333333)) | (((2 * (a1 & 0x55555555)) | (a1 >> 1) & 0x55555555) >> 2) & 0x33333333) & 0xF0F0F0F)) | (((4 * (((2 * (a1 & 0x55555555)) | (a1 >> 1) & 0x55555555) & 0x33333333)) | (((2 * (a1 & 0x55555555)) | (a1 >> 1) & 0x55555555) >> 2) & 0x33333333) >> 4) & 0xF0F0F0F) >> 24);
}

long long sub_400765(string a1) {
	int v2; // [rsp+8h] [rbp-10h]
	int i; // [rsp+Ch] [rbp-Ch]
	long long v4; // [rsp+10h] [rbp-8h]
	long long v5; // [rsp+14h] [rbp-4h]

	v2 = 0;
	v5 = -1;
	while (a1[v2]) {
		v4 = adooha(a1[v2]);
		for (i = 0; i <= 7; ++i) {
			if (((v4 ^ v5) & 0x80000000) == 0)
				v5 *= 2;
			else
				v5 = (2 * v5) ^ 0x4C11DB7;
			v4 *= 2;
		}
		++v2;
	}
	return adooha(~v5);
}

long long hexadecimalToDecimal(string hexVal) {
	int len = hexVal.length();

	// Initializing base value to 1, i.e 16^0 
	long long base = 1;

	long long dec_val = 0;

	// Extracting characters as digits from last character 
	for (int i = len - 1; i >= 0; i--) {
		// if character lies in '0'-'9', converting  
		// it to integral 0-9 by subtracting 48 from 
		// ASCII value. 
		if (hexVal[i] >= '0' && hexVal[i] <= '9') {
			dec_val += (hexVal[i] - 48)*base;

			// incrementing base by power 
			base = base * 16;
		}

		// if character lies in 'A'-'F' , converting  
		// it to integral 10 - 15 by subtracting 55  
		// from ASCII value 
		else if (hexVal[i] >= 'A' && hexVal[i] <= 'F') {
			dec_val += (hexVal[i] - 55)*base;

			// incrementing base by power 
			base = base * 16;
		}
	}

	return dec_val;
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
	vector <string> off_6020C0(200);
	for (int i = 0; i < 200; i++) {
		string temp;
		cin >> temp;
		while (temp.front() < 0) {
			temp.erase(temp.begin());
		}
		for (char ch : temp) {
			if (ch < 0) cout << "ALERT!!!";
		}
		off_6020C0[i] = temp;
		// cout << (unsigned int) strtol(off_6020C0[i], 0LL, 16) << endl;
	}
	int flag[200];
	int last = 0;
	for (int i = 0; i <= 49; ++i) {
		for (int current_number = last; current_number < 100; current_number++) {
			long long temp = sub_400765(off_6020C0[2 * current_number]);
			long long need = hexadecimalToDecimal(off_6020C0[2 * current_number + 1]);
			if (temp == need) {
				flag[i] = current_number;
				last = current_number + 1;
				break;
			}
			if (current_number == 99) {
				cout << "ERROR: " << i << endl;
			}
		}
	}
	for (int i = 0; i < 200; i++) {
		cout << flag[i] << ',';
	}
}
