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
    ll parent;
    char ch;
    bool term;
    ll link;
    map <char, ll> childs;
    bool visited_dfs;
    bool visited_link;
    set <ll> id;

    explicit node(ll parent = -1, char ch = 0, bool term = false)
    : parent(parent),
      ch(ch),
      term(term),
      link(-1),
      childs(map<char, ll>{}),
      visited_dfs(false),
      visited_link(false),
      id(set<ll>{})
    {}
    void child_add(char adding, ll ind) {
        childs.insert({adding, ind});
    }

    ll child(char ch) {
        auto it = childs.find(ch);
        return ((it == childs.end()) ? -1 : it->second);
    }

    void id_add(ll id_a) {
        id.insert(id_a);
    }
};

class aho {
public:
    vector <node> nodes;
    ll counter;
    aho()
    : nodes(vector<node>{node()}),
    counter(0LL)
    {}
    void add(string s) {
        ll current = 0;
        ll i = 0;
        for (int i = 0; i < s.size(); i++) {
            if (nodes[current].child(s[i]) == -1) {
                nodes[current].child_add(s[i], nodes.size());
                nodes.push_back(node(current, s[i], i + 1 == s.size()));
            }
            current = nodes[current].child(s[i]);
        }
        nodes[current].term = true;
        nodes[current].id_add(counter++);
    }

    void suff_build() {
        deque<ll> goes{0};
        while (!goes.empty()) {
            link_add(goes.front());
            for(auto it : nodes[goes.front()].childs)
                goes.push_back(it.second);
            goes.pop_front();
        }
    }

    set<ll> link_strings(string s) {
        set<ll> returned;
        ll i = 0, cur = 0;
        while (i < s.size()) {
            cur = next(s[i++], cur);
            nodes[cur].visited_link = true;
        }
        for (int j = 0; j < nodes.size(); ++j) {
            if (!nodes[j].visited_dfs && nodes[j].visited_link) {
                sift_dfs(j);
            }
        }
        for (int j = 1; j < nodes.size(); ++j) {
            if (nodes[j].visited_dfs && nodes[j].term) {
                returned.insert(nodes[j].id.begin(), nodes[j].id.end());
            }
            nodes[j].visited_link = false;
        }
        return returned;
    }

    void sift_dfs(ll cur) {
        nodes[cur].visited_dfs = true;
        if (nodes[cur].link != - 1 && !nodes[nodes[cur].link].visited_dfs) {
            sift_dfs(nodes[cur].link);
        }
    }

    void link_add(ll ind) {
        if (ind == 0)
            return;
        ll ind_cur = nodes[nodes[ind].parent].link;
        while (ind_cur != - 1 && nodes[ind_cur].child(nodes[ind].ch) == -1) {
            ind_cur = nodes[ind_cur].link;
        }
        if (ind_cur == -1) {
            nodes[ind].link = 0;
        } else {
            nodes[ind].link = nodes[ind_cur].child(nodes[ind].ch);
        }
    }
    ll next(char ch, ll ind) {
        while (ind != -1 && nodes[ind].child(ch) == -1) {
            ind = nodes[ind].link;
        }
        if (ind == -1) {
            return 0;
        } else {
            return nodes[ind].child(ch);
        }
    }
};

int main() {
    // begin of my useless prog
#ifdef MY_DEBUG
    freopen("input.txt", "r", stdin);
    freopen("output.txt", "w", stdout);
#else
    freopen("search4.in", "r", stdin);
    freopen("search4.out", "w", stdout);
#endif
    ios_base::sync_with_stdio(NULL);
    cin.tie(NULL);
    cout.tie(NULL);
    int32 n;
    cin >> n;
    aho ahoaho;
    string s;
    for (int i = 0; i < n; i++) {
        cin >> s;
        ahoaho.add(s);
    }
    ahoaho.suff_build();
    cin >> s;
    auto answer = ahoaho.link_strings(s);
    for (int i = 0; i < n; i++) {
        if (answer.find(i) != answer.end()) {
            cout << "YES\n";
        } else {
            cout << "NO\n";
        }
    }
    re
}
