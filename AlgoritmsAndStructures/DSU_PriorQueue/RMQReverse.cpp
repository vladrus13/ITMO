#define _CRT_SECURE_NO_WARNINGS
#include <iostream>
#include <string>
#include <vector>
#include <map>
#include <algorithm>
#include <cmath>
#include <set>
#include <iomanip>
#include <cassert>
#include <fstream>

using namespace std;

typedef long long ll;
typedef long double ld;
typedef unsigned int ui;
typedef unsigned long long ull;

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
	pair <pair <int, int>, int> qq;
	vector <int> a(n);
	PriorityQueueBinary *q = new PriorityQueueBinary();
	PriorityQueueBinaryMin *mini = new PriorityQueueBinaryMin();
	for (int i = 0; i < m; i++) {
		cin >> qq.first.first >> qq.first.second >> qq.second;
		qq.first.second--, qq.first.first--;
		q->push(qq);
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
			a[i] = INT_MAX;
		}
	}
	for (int i = 0; i < (int) a.size(); i++) {
		cout << a[i] << ' ';
	}
	return 0;
}
