#include <list>
#include <iostream>
#include "board.cpp"
#include <iterator>
#include <set>
#include <vector>
#include <map>

using std::list;

class solver {
private:
	list <board> path;

	void solve() {
		if (path.size() != 1) {
			std::cerr << "Solve was found or not found start desk!";
			exit(0);
		}
		if (!path.begin()->is_solvable()) {
			path = {};
			return;
		}
		// 1 - hamming sort
		// 2 - manhattan sort
		// 3 - empty x sort
		// 4 - empty y sort // 101 2001 11 11
		std::map <board, uint64_t> used[2001];
		std::map <board, board> parents[2001];
		// very large, but useful
		std::set <std::pair <uint32_t, board> > q;
		q.insert({0, path.front()});
		path = {};
		uint32_t steps;
		board temp;
		while (!q.empty()) {
			steps = q.begin()->first;
			temp = q.begin()->second;
			if (temp.is_goal()) {
				// dont use 'for' because code is tooooooo looooong 
				while (parents[temp.manhattan()].count(temp) != 0) {
					path.push_front(temp);
					temp = parents[temp.manhattan()][temp];
				}
				path.push_front(temp);
				return;
			}
			used[temp.manhattan()][temp] = steps;
			q.erase(q.begin());
			vector <board> permutated = temp.generate_permut();
			for (board it : permutated) {
				if (used[it.manhattan()].count(it)) continue;
				if (q.find({ steps + 1, it }) == q.end() && q.find({ steps, it }) == q.end()) {
					q.insert({ steps + 1, it });
					parents[it.manhattan()][it] = temp;
				}
			}
		}
	}
public:

	/////////////////////////////////////////// ITERATOR ////////////////////////////////////
	class iterator {
	private:
		list<board>::iterator ptr;
	public:
		explicit iterator(list<board>::iterator iterator): ptr(iterator) {}

		bool operator==(iterator const &it) const {
			return ptr == it.ptr;
		}

		bool operator!=(iterator const &it) const {
			return ptr != it.ptr;
		}

		board operator*() const {
			return *ptr;
		}

		iterator operator++() {
			ptr++;
			return *this;
		}
	};
	/////////////////////////////////////////////////
	
	solver(const board &a) {
		path.push_back(a);
		solve();
	}

	uint64_t moves() {
		return path.size();
	}

	list<board>::iterator begin() {
		return path.begin();
	}

	list<board>::iterator end() {
		return path.end();
	}

	solver operator = (const solver & other) {
		this->path = other.path;
	}
};