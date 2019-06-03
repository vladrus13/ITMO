#include <iostream>
#include "RandomizeQueue.h"
#include <string>
#include <vector>
#include <regex>
#include <cmath>
#include <algorithm>

int main(int argc, char **argv) {
	randomized_queue <std::string> randomizeQueue;
	char *end;
	std::string s;
	int k;
	k = std::strtol(argv[0], &end, 10);
	while (getline(std::cin, s)) {
		randomizeQueue.enqueue(s);
	}
	k = std::min(k, (int) randomizeQueue.size());
	for (int i = 0; i < k; i++) {
		std::cout << randomizeQueue.dequeue() << ' ';
	}
	return 0;
}