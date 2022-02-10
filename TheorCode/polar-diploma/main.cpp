#include <iostream>
#include <fstream>
#include "polar/PolarCoder.h"

int main() {
    int L = 1;
    std::vector<int> channel;
    std::ifstream in("../frozen.txt");
    int x;
    while (in >> x) {
        channel.push_back(x);
    }
    int n = 10;
    int info_length = (1 << (n - 1));
    int crc_size = 0;
    PolarCode polarCode(n, crc_size, std::move(channel));
    std::vector<int> read;
    read.reserve(info_length);
    for (int i = 0; i < info_length; i++) {
        read.push_back(0);
    }
    std::vector<int> a = polarCode.encode(read);
    std::vector<double> probabilities1{};
    std::vector<double> probabilities0{};
    probabilities1.reserve(a.size());
    probabilities0.reserve(a.size());
    for (uint8_t i: a) {
        probabilities0.push_back(i + 0.1);
        probabilities1.push_back(1.0 - i);
    }
    std::vector<int> decoded = polarCode.decode(probabilities1, probabilities0, L);
    int count = 0;
    for (int i = 0; i < decoded.size(); i++) {
        if (decoded[i] != read[i]) {
            count++;
        }
    }
    for (bool it : polarCode.frozen) {
        std::cout << (it == 0 ? "false," : "true,");
    }
    return 0;
}
