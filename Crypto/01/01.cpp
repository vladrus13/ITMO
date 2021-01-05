#include <iostream>
#include <fstream>
#include <map>
#include <algorithm>

using namespace std;

char getStep(char c, int step) {
    int returned = (int) c + step;
    while (returned > 122) {
        returned -= 26;
    }
    return (char) returned;
}

map<char, double> frequency{
        {'a', 0.08167},
        {'b', 0.01492},
        {'c', 0.02782},
        {'d', 0.04253},
        {'e', 0.12702},
        {'f', 0.02228},
        {'g', 0.02015},
        {'h', 0.06094},
        {'i', 0.06966},
        {'j', 0.00153},
        {'k', 0.00772},
        {'l', 0.04025},
        {'m', 0.02406},
        {'n', 0.06749},
        {'o', 0.07507},
        {'p', 0.01929},
        {'q', 0.00095},
        {'r', 0.05987},
        {'s', 0.06327},
        {'t', 0.09056},
        {'u', 0.02758},
        {'v', 0.00978},
        {'w', 0.0236},
        {'x', 0.0015},
        {'y', 0.01975},
        {'z', 0.00074}
};

std::pair<char, double> get_max(const std::map<char, double> &x) {
    using pairtype = pair<char, double>;
    return *std::max_element(x.begin(), x.end(), [](const pairtype &p1, const pairtype &p2) {
        return p1.second < p2.second;
    });
}

int main() {
    ifstream in;
    in.open("020.cipher");
    string s;
    in >> s;
    int length_key = 8;
    cout << "Length key: " << length_key << '\n';
    string new_s = s;
    for (int j = 0; j < length_key; j++) {
        map<char, double> copy = frequency;
        map<char, double> realFluency;
        for (int i = 0; i + j < s.size(); i += length_key) {
            realFluency[s[i + j]]++;
        }
        map<char, char> dependency;
        for (char i = 'a'; i <= 'z'; i++) {
            realFluency[i] /= s.size();
        }
        double realChance = 100000000;
        int realStep = 0;
        for (int step = 0; step < 26; step++) {
            double chance = 0;
            for (char it = 'a'; it <= 'z'; it++) {
                chance += abs(realFluency[it] - frequency[getStep(it, step)]);
            }
            if (chance < realChance) {
                realChance = chance;
                realStep = step;
            }
        }
        for (int i = 0; i + j < s.size(); i += length_key) {
            new_s[i + j] = getStep(new_s[i + j], realStep);
        }
        cout << (char) ('a' + realStep);
    }
    cout << "\nWord: " << new_s << "\n\n";

    return 0;
}
