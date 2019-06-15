#pragma once
#include <iostream>
#include "node.h"
#include <iterator>
#include <algorithm>
#include <vector>
#include "randomized_queue.hpp"
#include <random>
#include <cassert>

extern std::mt19937 gen(std::random_device().operator()());

template <typename T>
inline randomized_queue<T>::randomized_queue() = default;

template <typename T>
inline randomized_queue<T>::randomized_queue(T x) {
	node<T> * temp = new node<T>(x);
	first = temp;
	last = temp;
	si = 1;
}

template<typename T>
inline randomized_queue<T>::~randomized_queue() {
	node <T> * next = first->get_r(), *head = first;
	while (next != nullptr) {
		delete head;
		head = next;
		*next = *next->get_r();
	}
	delete first;
	delete last;
}

template<typename T>
inline uint64_t randomized_queue<T>::get_rand() const {
	if (si == 1) {
		return 0;
	}
	std::uniform_int_distribution<> randomizer(1, si - 1);
	return randomizer(gen);
}

template <typename T>
inline node<T> * randomized_queue<T>::advance(node<T> * first, uint64_t dist) const {
	node <T> *iterable = first;
	for (uint64_t i = 0; i < dist; i++) iterable = iterable->get_r();
	return iterable;
}

template <typename T>
inline uint64_t randomized_queue<T>::size() const {
	return si;
}

template <typename T>
inline bool randomized_queue<T>::empty() const {
	return size() == 0;
}

template <typename T>
void randomized_queue<T>::enqueue(T x) {
	if (empty()) {
		*this = randomized_queue<T>(std::move(x)); // необходимо рассматривать этот случай отдельно, так как тут создается НОВАЯ ОЧЕРЕДЬ, а не НОВЫЙ node. Что очень отличается
	} else {
		first = new node<T>(std::move(x), nullptr, first);
	}
	this->inc_size();
}

template <typename T>
T& randomized_queue<T>::dequeue() {
	assert(!empty());
	node<T> * deleted = advance(first, get_rand());
	if (deleted->get_r()) {
		deleted->get_r()->set_l(deleted->get_l());
	}
	if (deleted->get_l()) {
		deleted->get_l()->set_r(deleted->get_r());
	}
	T x = deleted->get_x();
	if (deleted->get_l() == nullptr) {
		first = deleted->get_r();
	}
	if (deleted->get_r() == nullptr) {
		last = deleted->get_l();
	}
	delete deleted;
	si--;
	return x;
}

template <typename T>
T& randomized_queue<T>::sample() const {
	assert(!empty());
	return move(first, get_rand())->get_x();
}

template<typename T>
typename randomized_queue<T>::iterator randomized_queue<T>::begin() {
	return iterator(first, last, 0);
}

template <typename T>
inline typename randomized_queue<T>::iterator randomized_queue<T>::end() {
	iterator it(first, last, si - 1);
	return it;
}

template<typename T>
inline void randomized_queue<T>::inc_size() {
	si++;
}

template<typename T>
inline void randomized_queue<T>::dec_size() {
	si--;
}

template <typename T>
inline randomized_queue<T>::iterator::iterator(node<T> *begin, node<T> *end, int pos) {
	for (node <T> * it = begin; it != end; it = it->get_r()) {
		q.push_back(it->get_x());
	}
	q.push_back(end->get_x());
	shuffle(q.begin(), q.end(), gen);
	index = pos;
}

template <typename T>
inline bool randomized_queue<T>::iterator::operator != (iterator const& other) const {
	return index != other.index || q != other.q;
}

template <typename T>
inline bool randomized_queue<T>::iterator::operator== (iterator const& other) const {
	return index == other.index && q == other.q;
}