/// what about the useless text at the beginning
//                               of the program?
///                 I like to do useless things.
//       The program is made by vladrus13 (2018)

// Do you want to optimize?
#define _CRT_SECURE_NO_WARNINGS

#include <iostream>
#include <algorithm>
#include <vector>
#include <cmath>
#include <deque>
#include <string>
#include <vector>
#include <cmath>
#include <queue>
#include <map>
#include <string>
#include <set>
#include <queue>
#include <iomanip>
#include <bitset>
#include <cassert>
#include <unordered_map>
#include <fstream>
#include <list>

typedef long double ld;
typedef int64_t ll;
typedef uint64_t ull;
typedef int32_t int32;

using namespace std;

void re() {
#ifdef _DEBUG
    system("pause");
#endif
    exit(0);
}

#define re re();

//////////////////////////////////////////////////////////////////
// SYSTEM STARTS

//////////////////////////////////////////////////////////////////
// UTILS

/////////////////////////////////////////////////////////////////
// MAIN

class node {
public:
    int32 par;
    char par_ch;
    int32 suf_link = -1;
    int32 visited = 0;
    map<char, int32> links;
    map<char, int32> child;

    node(int32 par, char ch) : par(par), par_ch(ch) {}

    int32 operator[](char ch) const {
        auto it = links.find(ch);
        return (it == links.end() ? -1 : it->second);
    }
};

class aho {
public:
    std::vector<node> nodes;
    std::vector<int32> term;

    aho() {
        nodes.emplace_back(-1, '\0');
    }

    void add(string const &str) {
        int32 current = 0;
        for (auto ch: str) {
            int32 to = nodes[current][ch];
            if (to != -1) {
                current = to;
                continue;
            }
            nodes[current].links[ch] = nodes[current].child[ch] = nodes.size();
            nodes.emplace_back(current, ch);
            current = nodes.size() - 1;
        }
        term.push_back(current);
    }

    vector<int32> calc(string const &text) {
        int32 curr = 0;
        for (auto ch: text) {
            curr = get_link(curr, ch);
            nodes[curr].visited++;
        }
        for (auto i: bfs()) {
            nodes[get_link_suf(i)].visited += nodes[i].visited;
        }
        vector<int32> returned(term.size(), 0);
        for (int32 i = 0; i < term.size(); ++i) {
            returned[i] = nodes[term[i]].visited;
        }
        return returned;
    }

private:
    int32 get_link_suf(int32 index) {
        return ((nodes[index].suf_link != -1) ? nodes[index].suf_link : ((nodes[index].par == -1 ||
                                                                          nodes[index].par == 0)
                                                                         ? nodes[index].suf_link = 0
                                                                         : nodes[index].suf_link = get_link(
                        get_link_suf(nodes[index].par), nodes[index].par_ch)));
    }

    int32 get_link(int32 index, char ch) {
        int32 res = nodes[index][ch];
        if (res != -1) {
            return res;
        }
        return (index == 0 ? nodes[index].links[ch] = 0 : nodes[index].links[ch] = get_link(get_link_suf(index), ch));
    }

    list<int32> bfs() {
        list<int32> result = {0};
        for (auto i = result.begin(); i != result.end(); ++i) {
            for (auto j: nodes[*i].child) {
                result.push_back(j.second);
            }
        }
        reverse(result.begin(), result.end());
        return move(result);
    }
};

int main() {
    // begin of my useless prog
#ifdef MY_DEBUG
    freopen("input.txt", "r", stdin);
    freopen("output.txt", "w", stdout);
#else
    freopen("search5.in", "r", stdin);
    freopen("search5.out", "w", stdout);
#endif
    ios_base::sync_with_stdio(NULL);
    cin.tie(NULL);
    cout.tie(NULL);
    aho f;
    string str;
    int32 size;
    cin >> size;
    for (int32 i = 0; i < size; ++i) {
        cin >> str;
        f.add(str);
    }
    string text;
    cin >> text;
    vector<int32> quantity = f.calc(text);
    for (int32 i = 0; i < size; ++i) {
        cout << quantity[i] << '\n';
    }
    re
}
