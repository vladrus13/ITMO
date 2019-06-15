#pragma once

template <typename T>
class node {
private:
	node *left = nullptr, *rigth = nullptr;
	T x;
public:
	node(const T &a, node *l, node *r)
		: left(l), rigth(r), x(a) {}
	node(T &a)
		: x(a) {}
	T get() {
		return x;
	}
	void set_l(node *l) {
		left = l;
	}

	void set_r(node *r) {
		rigth = r;
	}

	node * get_l() {
		return left;
	}

	node * get_r() {
		return rigth;
	}

	T get_x() {
		return x;
	}

	void set_x(T x) {
		this->x = std::move(x);
	}
};