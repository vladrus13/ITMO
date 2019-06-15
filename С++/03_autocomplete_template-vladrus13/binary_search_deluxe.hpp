#pragma once
#include "term.hpp"
#include <vector>
#include <functional>
#include <algorithm>

using std::vector;
using std::function;

class binary_search_deluxe {
private:
	binary_search_deluxe() = default;
public:
	static int first_index_of(vector <term> a, term &key, function <bool(term const &, term const &)> comparator) {
		return distance(a.begin(), std::lower_bound(a.begin(), a.end(), key, comparator));
	}
	static int last_index_of(vector <term> a, term &key, function <bool(term const &, term const &)> comparator) {
		return distance(a.begin(), std::upper_bound(a.begin(), a.end(), key, comparator));
	}
};