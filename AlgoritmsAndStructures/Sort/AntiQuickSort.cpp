#define _CRT_SECURE_NO_WARNINGS
#include <iostream>
#include <vector>

typedef long long ll;

using namespace std;

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("antiqs.in", "r", stdin);
	freopen("antiqs.out", "w", stdout);
#endif
	int n;
	cin >> n;
	vector <int> a(n);
	for (int i = 0; i < n; i++)
		a[i] = i + 1;
	for (int i = 2; i < n; i++)
		swap(a[i], a[i / 2]);
	for (int i = 0; i < n; i++)
		cout << a[i] << ' ';
	return 0;
}
