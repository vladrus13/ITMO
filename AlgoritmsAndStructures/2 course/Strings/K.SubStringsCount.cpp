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
    int32 lenn, link_suff;
    ll counter = -1;
    map<char, int> tran;
};

class tree {
public:
    std::vector<node> childs;

    tree() {
        childs.emplace_back();
        childs.back().lenn = 0;
        childs.back().link_suff = -1;
    }

    void add(char c) {
        int index = childs.size();
        childs.emplace_back();
        childs.back().lenn = childs[last].lenn + 1;
        int32 parent;
        for (parent = last; parent != -1 && childs[parent].tran.find(c) == childs[parent].tran.end(); parent = childs[parent].link_suff) {
            childs[parent].tran[c] = index;
        }
        if (parent == -1) {
            childs[index].link_suff = 0;
        } else {
            int32 temp = childs[parent].tran[c];
            if (childs[parent].lenn + 1 == childs[temp].lenn) {
                childs[index].link_suff = temp;
            } else {
                int32 copy = childs.size();
                childs.emplace_back();
                childs.back().lenn = childs[parent].lenn + 1;
                childs.back().tran = childs[temp].tran;
                childs.back().link_suff = childs[temp].link_suff;
                while (parent != -1 && childs[parent].tran[c] == temp) {
                    childs[parent].tran[c] = copy;
                    parent = childs[parent].link_suff;
                }
                childs[temp].link_suff = childs[index].link_suff = copy;
            }
        }
        last = index;
    }

    int last = 0;
};

ll answer(tree &st, int32 v = 0) {
    if (st.childs[v].counter != -1) {
        return st.childs[v].counter;
    }
    ll result = 1;
    for (auto i: st.childs[v].tran) {
        result += answer(st, i.second);
    }
    st.childs[v].counter = result;
    return result;
}


int main() {
    // begin of my useless prog
#ifdef MY_DEBUG
    freopen("input.txt", "r", stdin);
    freopen("output.txt", "w", stdout);
#else
    //freopen("search6.in", "r", stdin);
    //freopen("search6.out", "w", stdout);
#endif
    ios_base::sync_with_stdio(NULL);
    cin.tie(NULL);
    cout.tie(NULL);
    tree st;
    string str;
    cin >> str;
    for (auto c : str) {
        st.add(c);
    }
    cout << answer(st) - 1;
    re
}
