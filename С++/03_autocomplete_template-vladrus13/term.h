#include "term.hpp"
#include <string>
#include <sstream>
#include <iostream>
#include <algorithm>

using std::string;
using std::function;

string lexical_cast(const uint64_t& str) {
	std::ostringstream oss;
	oss << str;
	return oss.str();
}

inline void term::set_standart() {
	s = "";
	weight = 0;
}
inline term::term()
	:s(""), weight((uint64_t) 0) {}
inline term::term(string s, uint64_t x)
	:s(s), weight(x) {}
inline bool term::operator == (term const& other) const {
	return (s == other.s) && (weight == other.weight);
}
inline term::term(term const& other)
	:s(other.s), weight(other.weight) {}

inline term& term::operator= (term const& other) {
	if (&other == this) return *this;
	s = other.s;
	weight = other.weight;
	return *this;
}

inline term & term::operator=(term && other) {
	if (&other == this) return *this;
	s = other.s;
	weight = other.weight;
	// other.set_standart();
	return *this;
}

bool operator<(const term & a, const term & b) {
	return a.s < b.s;
}

bool operator>(const term & a, const term & b) {
	return a.s > b.s;
}

inline bool operator<=(const term & a, const term & b) {
	return a.s <= b.s;
}

inline string term::to_string() const {
	return "" + lexical_cast(weight) + " " + s;
}

reverse_weight_order_functor term::by_reverse_weight_order() const {
	return reverse_weight_order_functor();
}

prefix_order_functor term::by_prefix_order(int r) const {
	return prefix_order_functor(r);
}

std::ostream & operator<< (std::ostream & out, const term &t) {
	out << t.to_string();
	return out;
}

bool prefix_order_functor::operator()(const term & x, const term & y) const {
	if (r > (int) x.s.size() || r > (int) y.s.size()) throw "In comparables strings size is smaller then num of symbols";
	for (int32_t i = 0; i < r; i++) {
		if (x.s[i] > y.s[i]) return true;
		if (x.s[i] < y.s[i]) return false;
	}
	return true;
}

bool reverse_weight_order_functor::operator()(term & x, term & y) const {
	return x.s < y.s;
}
