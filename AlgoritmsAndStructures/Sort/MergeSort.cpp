#define _CRT_SECURE_NO_WARNINGS
#include <iostream>
#include <string>
#include <vector>
#include <map>
#include <algorithm>
#include <cmath>
#include <set>
// I summon The Merge Sort

using namespace std;

typedef long long ll;
/*
void merge_sort(vector <int>::iterator begin, vector <int>::iterator end) {
	if (distance(begin, end) < 2) {
		return 
	}
	vector <int>::iterator middle = begin;
	advance(middle, distance(begin, end) / 2);

}*/ 
// iterators - pain

vector <int> a;

void merge(int left, int middle, int rigth) {
	vector <int> b(rigth - left + 1);  // outofmemoty pls
	int it1 = left, it2 = middle + 1, itg = 0;
	while (it1 <= middle || it2 <= rigth) {
		if (it1 > middle) {
			b[itg] = a[it2];
			it2++;
		} else {
			if (it2 > rigth) {
				b[itg] = a[it1];
				it1++;
			} else {
				if (a[it1] < a[it2]) {
					b[itg] = a[it1];
					it1++;
				} else {
					b[itg] = a[it2];
					it2++;
				}
			}
		}
		itg++;
	}
	for (int i = 0, j = left; j <= rigth; j++, i++) {
		a[j] = b[i];
	}
}

void merge_sort(int left, int rigth) {
	if (rigth - left < 1) {
		return;
	}
	int middle = (left + rigth) / 2;
	merge_sort(left, middle);
	merge_sort(middle + 1, rigth);
	merge(left, middle, rigth);
}

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("sort.in", "r", stdin);
	freopen("sort.out", "w", stdout);
#endif
	int n;
	cin >> n;
	a.resize(n);
	for (int i = 0; i < n; i++) cin >> a[i];
	merge_sort(0, n - 1);
	for (int i = 0; i < n; i++) cout << a[i] << ' ';
	return 0;
}
