#include <cstddef>
#include "RandomizeQueue.h"

template <typename T>
class randomized_queue {
private:
	node<T> *first = nullptr, *last = nullptr; // ends of deque
	size_t si = 0;

	uint64_t get_rand() const;

	node<T> * advance(node<T> * first, uint64_t dist) const;

	void inc_size();
	void dec_size();
public:
	
	class iterator {
	private:
		std::vector <T> q;
		size_t index = 0;
	public:
		iterator(node<T> * begin, node<T> * end, int pos);

		bool operator != (iterator const& other) const;
		bool operator== (iterator const& other) const;

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

	randomized_queue(T x);

	~randomized_queue();

	randomized_queue();
	// post: R = size
	uint64_t size() const;

	// post: R = (empty or not)
	bool empty() const;
	void enqueue(T x);
	T& dequeue();
	T& sample() const;

	typename randomized_queue <T>::iterator end();
	typename randomized_queue <T>::iterator begin();
};