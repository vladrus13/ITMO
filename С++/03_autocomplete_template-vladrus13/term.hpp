#pragma once
#include <string>
#include <functional>

using std::string;
using std::function;

class term;

struct prefix_order_functor {
private:
	int r;
public:
	prefix_order_functor(int r): r(r) {}
	bool operator() (const term &x, const term &y) const;
};

struct reverse_weight_order_functor {
	bool operator() (term &x, term &y) const;
};

class term {
protected:
	string s;
	uint64_t weight;
	void set_standart();
	friend reverse_weight_order_functor; // for looking s from functors
	friend prefix_order_functor;
public:
	term();
	term(string s, uint64_t x);
	bool operator == (term const& other) const;
	term(term const& other);
	term& operator= (term const& other);
	term& operator= (term&& other);
	friend bool operator< (const term &a, const term &b);
	friend bool operator<= (const term &a, const term &b);
	friend bool operator> (const term &a, const term &b);
	string to_string() const;
	friend std::ostream& operator << (std::ostream &out, const term &t);
	reverse_weight_order_functor by_reverse_weight_order() const;
	prefix_order_functor by_prefix_order(int r) const;
};