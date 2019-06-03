#pragma once
#include <iostream>
#include "node.h"
#include <iterator>
#include <algorithm>
#include <vector>

template <typename T>
class Deque {
private:
	node<T> *first = nullptr, *last = nullptr; // ends of deque
	int64_t si = 0; // size

public:
	Deque(T x) {
		node<T> * temp = new node<T>(x, nullptr, nullptr);
		first = temp;
		last = temp;
		first->set_x(x);
	}

	Deque() = default;
	~Deque() {
		node <T> * next = first->get_r(), *head = first;
		while (next != nullptr) {
			delete head;
			head = next;
			*next = *next->get_r();
		}
	}

	// post: R = size
	int64_t size() const {
		return si;
	}

	// post: R = (empty or not)
	bool empty() const {
		return size() == 0;
	}

	void push_front(T x) {
		first = new node<T>(std::move(x), nullptr, last);
		if (!last) {
			last = first;
		}
		si++;
	}

	void push_back(T x) {
		first = new node<T>(std::move(x), nullptr, first);
		if (!last) {
			last = first;
		}
		si++;
	}

	void pop_back() {
		if (empty()) {
			std::cerr << "Delete element from empty deque";
			exit(0);
		} else {
			last = last->get_l();
		}
		si--;
	}

	void pop_front() {
		if (empty()) {
			std::cerr << "Delete element from empty deque";
			exit(0);
		} else {
			first = first->get_r();
		}
		si--;
	}

	T& front() {
		if (empty()) {
			std::cerr << "Get element from empty deque";
			exit(0);
		}
		return first->get_x();
	}

	T& back() {
		if (empty()) {
			std::cerr << "Get element from empty deque";
			exit(0);
		}
		return last->get_x();
	}
};