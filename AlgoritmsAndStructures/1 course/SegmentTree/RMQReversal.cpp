#define _CRT_SECURE_NO_WARNINGS
#include <iostream>
#include <algorithm>
#include <vector>
#include <cmath>
#include <deque>
#include <string>
#include <vector>
#include <iostream>
#include <cmath>
#include <queue>
#include <iostream>
#include <map>
#include <string>
#include <vector>
#include <algorithm>
#include <set>
#include <iostream>
#include <vector>
#include <algorithm>
#include <iostream>
#include <string>
#include <vector>
#include <algorithm>
#include <queue>
#include <iostream>
#include <string>
#include <vector>
#include <algorithm>
#include <queue>
#include <iomanip>
#include <bitset>
#include <cassert>

typedef long double ld;
typedef long long ll;
typedef unsigned long long ull;

using namespace std;
//#pragma comment (linker, "/STACK:5000000000")
#define INF (int)1e9;

class node /*yes, node*/ {
public:
	pair <pair <int, int>, int> value;
	node(pair <pair <int, int>, int>  b) {
		this->value = b;
	}
};

int pusher = 0;

class PriorityQueueBinaryMin {
public:
	vector <node*> q;

	PriorityQueueBinaryMin() {
		q.clear();
	}

	void swap(int i, int j) {
		node* t = q[i];
		q[i] = q[j];
		q[j] = t;
	}

	void shiftDown(int x) {
		while (2 * x + 1 < (int) q.size()) {
			int left = 2 * x + 1;
			int right = 2 * x + 2;
			int i;
			if (right < (int) q.size() && (*q[right]).value >(*q[left]).value) {
				i = right;
			} else {
				i = left;
			}
			if ((*q[x]).value >= (*q[i]).value) {
				return;
			}
			swap(x, i);
			x = i;
		}
	}

	void shiftUp(int x) {
		while ((*q[x]).value > (*q[(x - 1) / 2]).value) {
			swap(x, (x - 1) / 2);
			x = (x - 1) / 2;
		}
	}

	int size() {
		return (int) q.size();
	}

	pair <pair <int, int>, int> extractMin() {
		pair <pair <int, int>, int> min = (*q[0]).value;
		swap(0, (int) q.size() - 1);
		q.erase(q.end() - 1);
		shiftDown(0);
		return min;
	}

	void push(pair <pair <int, int>, int> x) {
		q.push_back(new node(x));
		shiftUp((int) q.size() - 1);
	}
};

class PriorityQueueBinary {
public:
	vector <node*> q;
	PriorityQueueBinary() {
		q.clear();
	}

	void swap(int i, int j) {
		node* t = q[i];
		q[i] = q[j];
		q[j] = t;
	}

	void shiftDown(int x) {
		while (2 * x + 1 < (int) q.size()) {
			int left = 2 * x + 1;
			int right = 2 * x + 2;
			int i;
			if (right < (int) q.size() && (*q[right]).value < (*q[left]).value) {
				i = right;
			} else {
				i = left;
			}
			if ((*q[x]).value <= (*q[i]).value) {
				return;
			}
			swap(x, i);
			x = i;
		}
	}

	void shiftUp(int x) {
		while ((*q[x]).value < (*q[(x - 1) / 2]).value) {
			swap(x, (x - 1) / 2);
			x = (x - 1) / 2;
		}
	}

	int size() {
		return (int) q.size();
	}

	pair <pair <int, int>, int> extractMin() {
		pair <pair <int, int>, int> min = (*q[0]).value;
		swap(0, (int) q.size() - 1);
		q.erase(q.end() - 1);
		shiftDown(0);
		return min;
	}

	void push(pair <pair <int, int>, int> x) {
		q.push_back(new node(x));
		shiftUp((int) q.size() - 1);
	}
};

/////////////////////////////////////////////////////////////////////

const int MAXN = 1e5 + 1;
ll t[3 * MAXN];
int n, m;
vector <int> a;

void build(int v, int tl, int tr) {
	if (tl == tr)
		t[v] = a[tl];
	else {
		int tm = (tl + tr) / 2;
		build(v * 2, tl, tm);
		build(v * 2 + 1, tm + 1, tr);
		t[v] = min(t[v * 2], t[v * 2 + 1]);
	}
}

int get_min(int v, int tl, int tr, int l, int r) {
	if (l > r) return 1e16;
	if (l == tl && r == tr) return t[v];
	int tm = (tl + tr) / 2;
	return min(get_min(v * 2, tl, tm, l, min(r, tm)), get_min(v * 2 + 1, tm + 1, tr, max(l, tm + 1), r));
}

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("rmq.in", "r", stdin);
	freopen("rmq.out", "w", stdout);
#endif
	ios_base::sync_with_stdio(NULL);
	cin.tie(NULL);
	cout.tie(NULL);
	int n, m;
	cin >> n >> m;
	vector <pair <pair <int, int>, int> > qq(m);
	a.resize(n);
	PriorityQueueBinary *q = new PriorityQueueBinary();
	PriorityQueueBinaryMin *mini = new PriorityQueueBinaryMin();
	for (int i = 0; i < m; i++) {
		cin >> qq[i].first.first >> qq[i].first.second >> qq[i].second;
		qq[i].first.second--, qq[i].first.first--;
		q->push(qq[i]);
	}
	pair <pair <int, int>, int> minima;
	pair <int, int> temp;
	for (int i = 0; i < n; i++) {
		minima.first.first = -1;
		if (q->size() != 0) {
			minima = q->extractMin();
		}
		while (q->size() != 0 && minima.first.first <= i) {
			mini->push({ {minima.second, minima.first.second}, 0 });
			minima = q->extractMin();
		}
		if (minima.first.first != -1) {
			if (minima.first.first <= i) {
				mini->push({ {minima.second, minima.first.second}, 0 });
			}
			q->push(minima);
		}
		temp.second = -1;
		while (mini->size() != 0 && temp.second < i) {
			temp = mini->extractMin().first;
		}
		if (temp.second != -1) {
			mini->push({ temp, 0 });
		}
		if (temp.second != -1) {
			a[i] = temp.first;
		} else {
			a[i] = 2147483647;
		}
	}
	build(1, 0, n - 1);
	for (int i = 0; i < m; i++) {
		if (get_min(1, 0, n - 1, qq[i].first.first, qq[i].first.second) != qq[i].second) {
			cout << "inconsistent\n";
			return 0;
		}
	}
	cout << "consistent\n";
	for (int i = 0; i < (int) a.size(); i++) {
		cout << a[i] << ' ';
	}
	return 0;
}