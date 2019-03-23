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
	int link, value, value2;
	node(int a, int b, int c) {
		this->link = a;
		this->value = b;
		this->value2 = c;
	}
};

int pusher = 0;

class PriorityQueueBinary {
 public:
	vector <node*> q;
	vector <int> a;

	PriorityQueueBinary() {
		q.clear();
		a.clear();
	}

	void swap(int i, int j) {
		a[(*q[i]).link] = j;
		a[(*q[j]).link] = i;
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

	int extractMin() {
		if (q.size() == 0) {
			pusher = -1;
			return -1;
		}
		int min = (*q[0]).value;
		pusher = (*q[0]).value2;
		swap(0, (int) q.size() - 1);
		q.erase(q.end() - 1);
		shiftDown(0);
		return min;
	}

	void push(int x, int i) {
		a.push_back((int) q.size());
		q.push_back(new node((int) a.size() - 1, x, i));
		shiftUp((int) q.size() - 1);
	}

	void decreseKey(int i, int value) {
		if (a[i] < 0) return;
		if ((*q[a[i]]).value <= value) { return; }
		(*q[a[i]]).value = value;
		shiftUp(a[i]);
	}
};

int main() {
#ifdef _DEBUG
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
#else
	freopen("priorityqueue2.in", "r", stdin);
	freopen("priorityqueue2.out", "w", stdout);
#endif
	// NOTE: speed uup!
	ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	string s;
	int x, y;
	int command = 0;
	vector <int> pu(1, 0);
	PriorityQueueBinary *q = new PriorityQueueBinary();
	while (cin >> s) {
		pu.push_back(pu.back());
		command++;
		if (s == "push") {
			pu.back()++;
			cin >> x;
			q->push(x, command);
		}
		if (s == "extract-min") {
			int x = q->extractMin();
			if (pusher < 0) {
				cout << "*\n";
			} else {
				cout << x << ' ' << pusher << endl;
			}
		}
		if (s == "decrease-key") {
			cin >> x >> y;
			q->decreseKey(pu[x] - 1, y);
		}
	}
}
