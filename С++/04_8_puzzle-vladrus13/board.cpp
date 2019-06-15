#pragma once
#include <vector>
#include <algorithm>
#include <string>
#include <math.h>

// precalculate

using std::vector;

namespace utils {
	//pre : size a < 4
	inline unsigned char char_length(const unsigned char & a) {
		return (a > 99 ? 3 : (a > 9 ? 2 : 1));
	}

	// pre: size a < 4
	inline std::string to_string(const unsigned char & a) {
		std::string returned = "";
		if (a > 99) returned += (char) (a / 100 + '0');
		if (a > 9) returned += (char) (a / 10 % 10 + '0');
		returned += (char) (a % 10 + '0');
		return returned;
	}

	inline bool is_alivable(unsigned char size, unsigned char i, unsigned char j) {
		return i < size && j < size; // we use trick with overflow unsigned char (0 - 1 = 255) and position -1 is error too.
	}
}

class point {
public:
	unsigned char x, y;
};

inline unsigned char manhattan_p(point &a, point &b) {
	return abs(a.x - b.x) + abs(a.y - b.y);
}

// board

class board {
private:
	vector <vector <unsigned char> > desk;
	unsigned char sizer = 0;
	point null = { 0, 0 };
	int32_t distance_manhatten = 0, distance_hamilton = 0;

	void make_manhatten() {
		if (distance_manhatten) throw "Make Manhatten second";
		point first, second; // used manhatten from point
		for (unsigned char i = 0; i < sizer; i++) {
			for (unsigned char j = 0; j < sizer; j++) {
				if (i == null.x && j == null.y) continue;
				first = { i, j };
				second = { static_cast<unsigned char> ((desk[i][j] - 1) / sizer), static_cast<unsigned char> ((desk[i][j] - 1) % sizer) };
				distance_manhatten += manhattan_p(first, second);
			}
		}
	}

	void make_hamiltone() {
		if (distance_hamilton) throw "Make Hamilton second";
		for (unsigned char i = 0; i < sizer; i++) {
			for (unsigned char j = 0; j < sizer; j++) {
				if (i == null.x && j == null.y) continue;
				distance_hamilton += ((i == (desk[i][j] - 1) / sizer) && (j == (desk[i][j] - 1) % sizer) ? 0 : 1);
			}
		}
	}

	void make_pre() {
		make_manhatten();
		make_hamiltone();
	}
public:
	board() = default;
	board(unsigned char size):sizer(size) {
		if (size > 10) throw "Very big desk"; // at the lecture You said that a 10 by 10 Board is enough. 
		                                      // To consume less memory, I used the char type and now the big 
		                                      // 10 by 10 boards won't fit exactly)
		desk.resize(size, vector <unsigned char> (size, 0));
		// generate random 
		vector <unsigned char> ite_desk(size * size);
		for (unsigned char i = 0; (unsigned char) i < ite_desk.size(); i++) ite_desk[i] = i;
		std::random_shuffle(ite_desk.begin(), ite_desk.end());
		for (unsigned char i = 0; i < (unsigned char) ite_desk.size(); i++) {
			desk[i / size][i % size] = ite_desk[i];
			if (ite_desk[i] == 0) null = { static_cast<unsigned char> (i / size), static_cast<unsigned char> (i % size) };
		}
		make_pre();
	}
	board(vector <vector <unsigned char> > &a) {
		// check that desk is corrent
		sizer = (char) a.size();
		if (sizer > 10) throw "Too big desk";
		for (unsigned char i = 0; i < sizer; i++) {
			if (a[i].size() != sizer) throw "Uncorrect desk";
		}
		desk = a;
		for (unsigned char i = 0; i < sizer; i++) {
			for (unsigned char j = 0; j < sizer; j++) {
				if (desk[i][j] == 0) null = { i, j };
			}
		}
		make_pre();
	}

	unsigned char size() const {
		return sizer;
	}

	int32_t hamming() const {
		return distance_hamilton;
	}

	int32_t manhattan() const {
		return distance_manhatten;
	}

	bool is_goal() const {
		return distance_hamilton == 0;
	}

	bool is_solvable() const {
		vector <unsigned char> p(sizer * sizer, 0);
		for (unsigned char i = 0; i < sizer; i++) {
			for (unsigned char j = 0; j < sizer; j++) {
				p[i * sizer + j] = desk[i][j];
			}
		}
		int perm = 0;
		for (unsigned char i = 0; i < p.size(); i++) {
			for (unsigned char j = i + 1; j < p.size(); j++) {
				if (p[i] > p[j]) perm++;
			}
		}
		bool u_perm = (perm + null.x + null.y) % 2;
		return ((perm + null.x + null.y) % 2) == ((p.size() - 1) % 2);
	}

	friend bool operator== (const board & a, const board & b) {
		return a.desk == b.desk;
	}

	friend bool operator!= (const board & a, const board & b) {
		return a.desk != b.desk;
	}

	friend bool operator < (const board &a, const board &b) {
		if (a.manhattan() != b.manhattan()) {
			return a.manhattan() < b.manhattan();
		}
		if (a.size() != b.size()) {
			return false;
		}
		for (int i = 0; i < a.size(); ++i) {
			for (int j = 0; j < b.size(); ++j) {
				if (a.desk[i][j] < b.desk[i][j]) {
					return true;
				}
				if (a.desk[i][j] > b.desk[i][j]) {
					return false;
				}
			}
		}
		return false;
	}

	friend bool operator > (const board &a, const board &b) {
		if (a == b) return false;
		return b < a;
	}

	std::string to_string() const {
		vector <unsigned char> width(sizer, 0);
		for (unsigned char i = 0; i < sizer; i++) {
			for (unsigned char j = 0; j < sizer; j++) {
				width[j] = std::max(width[j], utils::char_length(desk[i][j]));
			}
		}
		std::string result = "";
		for (unsigned char i = 0; i < sizer; i++) {
			for (unsigned char j = 0; j < sizer; j++) {
				if (j == sizer - 1) {
					result += std::string(width[j] - utils::char_length(desk[i][j]), ' ') + utils::to_string(desk[i][j]) + "\n";
				} else {
					result += std::string(width[j] - utils::char_length(desk[i][j]), ' ') + utils::to_string(desk[i][j]) + " ";
				}
			}
		}
		return result;
	}

	friend std::ostream &operator<<(std::ostream &stream, const board &a) {
		return stream << a.to_string();
	}

	const vector<unsigned char> &operator[](unsigned char n) {
		return desk[n];
	}

	board &operator = (const board & other) = default;

	std::pair <char, char> null_get() {
		return { null.x, null.y };
	}

	vector <board> generate_permut() const {
		vector <board> returned;
		vector <vector <unsigned char> > new_desk = desk;
		vector <point> generator = { {static_cast < unsigned char>(0),static_cast < unsigned char>(1)}, {static_cast < unsigned char>(0),static_cast<unsigned char>(-1)}, {static_cast<unsigned char>(-1),static_cast < unsigned char>(0)}, {static_cast<unsigned char>(1),static_cast<unsigned char>(0)} };
		for (short i = 0; i < 4; i++) {
			unsigned char future_x = null.x + generator[i].x, future_y = null.y + generator[i].y;
			if (utils::is_alivable(sizer, future_x, future_y)) {
				std::swap(new_desk[null.x][null.y], new_desk[future_x][future_y]);
				returned.push_back(board(new_desk));
				std::swap(new_desk[null.x][null.y], new_desk[future_x][future_y]);
			}
		}
		return returned;
	}
};