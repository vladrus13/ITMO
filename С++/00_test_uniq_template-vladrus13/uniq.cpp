#include <iostream>
#include <fstream>
#include <string>
#include <map>
#include <vector>

bool absolute_predicate(const std::string &first, const std::string &last) {
	if (first.size() != last.size()) return false;
	for (size_t i = 0; i < last.size(); i++) {
		if (first[i] != last[i]) return false;
	}
	return true;
}

bool lower_case_predicate(const std::string &first, const std::string &last) {
	if (first.size() != last.size()) return false;
	for (size_t i = 0; i < last.size(); i++) {
		if (tolower(first[i]) != tolower(last[i])) return false;
	}
	return true;
}

std::vector <std::pair <std::string, int32_t> >::iterator unique(std::vector <std::pair <std::string, int32_t> >::iterator begin,
																 std::vector <std::pair <std::string, int32_t> >::iterator end,
																 bool binary_predicate(const std::string &first, const std::string &last)) {
	if (begin == end) return end;
	auto result = begin;
	while (++begin != end) {
		if (!binary_predicate(result->first, begin->first)) {
			*(++result) = *begin;
		} else {
			result->second++;
		}
	}
	return ++result;
}

void uniq(std::istream & input, const bool should_count, const bool should_ignore_cast) {
	std::vector <std::pair <std::string, int32_t> > in;
	in.clear();
	std::string temp = "";
	input.seekg(0);
	while (std::getline(input, temp)) {
		in.push_back({ temp, 1 });
	}
	auto ender = unique(in.begin(), in.end(), (should_ignore_cast ? lower_case_predicate : absolute_predicate));
	in.erase(ender, in.end());
	for (auto pa : in) {
		if (should_count) {
			std::cout << pa.second << ' ';
		}
		std::cout << pa.first << '\n';
	}
}

int main(int argc, char ** argv) {
	/// parse the input of MAIN
	bool should_count = false, should_ignore_cast = false;
	const char * input_name = nullptr;
	for (int32_t i = 1; i < argc; ++i) {
		if (argv[i][0] == '-') {
			if (argv[i][1] != '-') {
				const size_t len = std::strlen(argv[i]);
				for (size_t j = 1; j < len; ++j) {
					switch (argv[i][j]) {
						case 'c':
							should_count = true;
							break;
						case 'i':
							should_ignore_cast = true;
							break;
					}
				}
			} else {
				if (std::strcmp(argv[i], "--count") == 0) {
					should_count = true;
				} else if (std::strcmp(argv[i], "--ignore-case") == 0) {
					should_ignore_cast = true;
				}
			}
		} else {
			input_name = argv[i];
		}
	}
	if (input_name != nullptr) {
		std::ifstream f(input_name);
		uniq(f, should_count, should_ignore_cast);
	} else {
		uniq(std::cin, should_count, should_ignore_cast);
	}
	system("pause");
	return 0;
}