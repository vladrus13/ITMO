#pragma once
#include <iostream>
#include "deque.h"
#include <deque>
#include <time.h>
#include "RandomizeQueue.h"

void test_deque() {
	const int COUNT_TEST = 100, COUNT_OP = 10000;
	std::cout << "-------------------- test deque --------------\n";
	for (int i = 1; i <= COUNT_TEST; i++) {
		srand(time(NULL));
		std::deque<int> standard;
		Deque<int> testing;
		int size = 0;
		std::cout << "-----------------testing " << i << '\n';
		for (int j = 1; j <= COUNT_OP; j++) {
			int r = rand() % 7;
			if (r == 0) {
				if (standard.size() != testing.size()) {
					std::cout << "fail in test " << i << "\n";
				}
			}
			if (r == 1) {
				if (size > 0) {
					if (standard.front() != testing.front()) {
						std::cout << "fail in test " << i << "\n";
					}
				}
			}
			if (r == 2) {
				if (size > 0) {
					if (standard.back() != testing.back()) {
						std::cout << "fail in test " << i << "\n";
					}
				}
			}
			if (r == 3) {
				standard.push_front(j);
				testing.push_front(j);
				size++;
			}
			if (r == 4) {
				standard.push_back(j);
				testing.push_back(j);
				size++;
			}
			if (r == 5) {
				if (size > 0) {
					standard.pop_back();
					testing.pop_back();
					size--;
				}
			}
			if (r == 6) {
				if (size > 0) {
					standard.pop_front();
					testing.pop_front();
					size--;
				}
			}
		}
	}
}

void test_queue() {
	std::cout << "-------------------- test queue --------------\n";
	randomize_queue<int> a;
	if (a.size() != 0) {
		std::cout << "Wrong size";
	}
	a.enqueue(1);
	if (a.size() != 1) {
		std::cout << "Wrong size";
	}
	if (a.dequeue() != 1) {
		std::cout << "Wrong element";
	}
	a.enqueue(1);
	a.enqueue(2);
	a.enqueue(3);
	a.enqueue(4);
	a.sample();
	a.sample();
	a.sample();
	a.sample();
	if (a.size() != 4) {
		std::cout << "Wrong size";
	}
	a.dequeue();
	a.dequeue();
	a.dequeue();
	a.dequeue();
	if (a.size() != 0) {
		std::cout << "Wrong size";
	}
	auto it = a.begin();
}
/*
int main() {
	test_deque();
	test_queue();
	system("pause");
	return 0;
}*/