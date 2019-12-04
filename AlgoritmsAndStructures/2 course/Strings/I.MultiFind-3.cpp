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

// from this moment, I HATE STRUCTS AND CLASSES

struct node {
    int32 link;
    int32 length;
    int32 childs[26];
    int32 id;
};

node aho[2000200];
node reversal_aho[2000200];

void add(node *aho, int32 &counter, int32 &last, char ch) {
    int32 p = counter;
    counter++;
    aho[p].length = aho[last].length + 1;
    aho[p].id = aho[p].length - 1;
    int32 v = last;
    while (v != -1 && aho[v].childs[ch] == -1) {
        aho[v].childs[ch] = p;
        v = aho[v].link;
    }
    if (v == -1) {
        aho[p].link = 0;
    } else {
        int32 u = aho[v].childs[ch];
        if (aho[v].length + 1 == aho[u].length) {
            aho[p].link = u;
        } else {
            int32 w = counter;
            counter++;
            aho[w].length = aho[v].length + 1;
            aho[w].link = aho[u].link;
            aho[w].id = aho[u].id;
            for (auto i = 0; i < 26; ++i) {
                aho[w].childs[i] = aho[u].childs[i];
            }
            while (v != -1 && aho[v].childs[ch] == u) {
                aho[v].childs[ch] = w;
                v = aho[v].link;
            }
            aho[p].link = w;
            aho[u].link = w;
        }
    }
    last = p;
}

void nullaber(node *aho, const string &s) {
    for (int32 i = 0; i < 2 * s.size() + 10; ++i) {
        aho[i].link = -1;
        aho[i].length = -1;
        for (int32 &j : aho[i].childs) {
            j = -1;
        }
    }
}

void build(node *aho, const string &s) {
    nullaber(aho, s);
    int32 last = 0;
    int32 counter = 1;
    aho[last].length = 0;
    for (char i : s) {
        add(aho, counter, last, i - 'a');
    }
}

int32 find(node *aho, string const &s) {
    int32 stat = 0;
    int32 v = 0;
    while (stat < s.size()) {
        v = aho[v].childs[s[stat] - 'a'];
        stat++;
        if (v == -1) {
            return -1;
        }
    }
    return aho[v].id - (ll) s.length() + 1;
}


int main() {
    // begin of my useless prog
#ifdef MY_DEBUG
    freopen("input.txt", "r", stdin);
    freopen("output.txt", "w", stdout);
#else
    freopen("search6.in", "r", stdin);
    freopen("search6.out", "w", stdout);
#endif
    ios_base::sync_with_stdio(NULL);
    cin.tie(NULL);
    cout.tie(NULL);
    int32 n;
    cin >> n;
    vector<string> lines;
    for (int32 i = 0; i < n; i++) {
        string tmp;
        cin >> tmp;
        lines.push_back(tmp);
    }
    string s;
    cin >> s;
    build(aho, s);
    reverse(s.begin(), s.end());
    build(reversal_aho, s);
    for (string &it : lines) {
        cout << find(aho, it);
        reverse(it.begin(), it.end());
        int32 reversal_pos = find(reversal_aho, it);
        if (reversal_pos == -1)
            cout << ' ' << -1 << '\n';
        else
            cout << ' ' << s.size() - it.size() - reversal_pos << '\n';
    }
    re
}
