#pragma once
#include <iostream>
#include "node.h"
#include <iterator>
#include <algorithm>
#include <vector>
#include <cassert>

template <typename T>
class Deque {
private:
	node<T> *first = nullptr, *last = nullptr; // ends of deque
	uint64_t si = 0; // size

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
		delete first;
		delete last;
	}

	// post: R = size
	uint64_t size() const {
		return si;
	}

	// post: R = (empty or not)
	bool empty() const {
		return size() == 0;
	}

	void push_front(T x) {
		first = new node<T>(std::move(x), nullptr, first);
		if (!last) {
			last = first;
		}
		si++;
	}

	void push_back(T x) {
		first = new node<T>(std::move(x), nullptr, last);
		if (!last) {
			last = first;
		}
		si++;
	}

	void pop_back() {
		assert(!empty());
		last = last->get_l();
		si--;
		if (si == 0) {
			last = nullptr;
			first = nullptr;
		}
	}

	void pop_front() {
		assert(!empty());
		first = first->get_r();
		si--;
		if (si == 0) {
			last = nullptr;
			first = nullptr;
		}
	}

	T& front() {
		assert(!empty());
		return first->get_x();
	}

	T& back() {
		assert(!empty());
		return last->get_x();
	}

	class iterator {
	private:
		std::vector <T> q;
		size_t index = 0;
	public:
		iterator(node<T> * begin, node<T> * end, int pos) {
			for (node <T> * it = begin; it != end; it = it->get_r()) {
				q.push_back(it->get_x());
			}
			q.push_back(end->get_x());
			index = pos;
		}

		bool operator != (iterator const& other) const { 
			return index != other.index || q != other.q; 
		}

		bool operator== (iterator const& other) const {
			return index == other.index && q == other.q;
		}

		iterator& operator++(void) {
			index++;
			return *this;
		}

		iterator operator++(int) {
			iterator result = *this;
			++*this;
			return result;
		}

		const T& operator*() const {
			return q[index];
		}
	};

	iterator begin() {
		return iterator(first, last, 0);
	}

	iterator end() {
		return iterator(first, last, si - 1);
	}
};