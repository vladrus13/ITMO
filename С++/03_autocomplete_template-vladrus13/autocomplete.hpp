#pragma once
#include <string>
#include <vector>
#include "binary_search_deluxe.hpp"
#include "term.hpp"
#include <algorithm>

using std::string;

class autocomplete {
private:
	vector <term> a;
public:
	autocomplete(const vector <term> &get) {
		a = get;
		sort(a.begin(), a.end());
	}

	vector <term> all_matches(string &prefix) {
		int first, last;
		term key = term(prefix, 0);
		first = binary_search_deluxe::first_index_of(a, key, [](const term& a, const term& b) {return a < b; });
		last = binary_search_deluxe::last_index_of(a, key, [](const term& a, const term& b) {return a <= b; });
		vector <term> returned;
		for (int i = first; i < last; i++) {
			returned.push_back(a[i]);
		}
		return returned;
	}
	uint32_t number_of_matches(string &prefix) {
		int first, last;
		term key = term(prefix, 0);
		first = binary_search_deluxe::first_index_of(a, key, [](const term& a, const term& b) {return a < b; });
		last = binary_search_deluxe::last_index_of(a, key, [](const term& a, const term& b) {return a <= b; });
		return last - first;
	}
};