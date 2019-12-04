/// what about the useless text at the beginning
//                               of the program?
///                 I like to do useless things.
//       The program is made by vladrus13 (2018)

// Do you want to optimize?
#define _CRT_SECURE_NO_WARNINGS

#include <iostream>
#include <algorithm>
#include <utility>
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

void fase(int32 n, int32** a, vector<int32>* uniq, int32 helper) {
    int32 back_up[n];
    vector<int32> new_uniq = *uniq;
    int32* new_a = *a;
    for (int32 i = 0; i < n; ++i) {
        back_up[new_a[i]] = i;
    }
    sort(new_a, new_a + n, [&back_up, &new_uniq, helper, n](int32 a, int32 b) { return make_pair(new_uniq[back_up[a]], new_uniq[back_up[(a + (1 << (helper - 1))) % n]]) < make_pair(new_uniq[back_up[b]], new_uniq[back_up[(b + (1 << (helper - 1))) % n]]); });
    vector<int32> returned(n);
    returned[0] = 0;
    for (int32 i = 1; i < n; ++i) {
        if (new_uniq[back_up[new_a[i]]] != new_uniq[back_up[new_a[i - 1]]]) {
            returned[i] = returned[i - 1] + 1;
            continue;
        }
        if (new_uniq[back_up[(new_a[i] + (1 << (helper - 1))) % n]] !=
            new_uniq[back_up[(new_a[i - 1] + (1 << (helper - 1))) % n]]) {

            returned[i] = returned[i - 1] + 1;
            continue;
        }
        returned[i] = returned[i - 1];
    }
    *uniq = returned;
}

int32* suffix_array(string const& s) {
    auto result = new int32[s.size()];
    vector<int32> uniq(s.size());
    for (int32 i = 0; i < s.size(); ++i) {
        result[i] = i;
    }
    sort(result, result + s.size(), [&s](int32 a, int32 b) {return s[a] < s[b];});
    uniq[0] = 0;
    for (int32 i = 1; i < s.size(); ++i) {
        uniq[i] = uniq[i - 1] + (s[result[i]] == s[result[i - 1]] ? 0 : 1);
    }
    for (int32 i = 1; (1 << (i - 1)) <= s.size() && uniq[s.size() - 1] != s.size() - 1; ++i) {
        fase(s.size(), &result, &uniq, i);
    }
    return result;
}

int32* lcp(string const& s, int32* suff) {
    auto result = new int32[s.size()];
    int32 reversal_suff[s.size()];
    for (int32 i = 0; i < s.size(); ++i) {
        reversal_suff[suff[i]] = i;
    }
    int32 current = 0;
    for (int32 i = 0; i < s.size(); i++) {
        if (current > 0) {
            current--;
        }
        if (reversal_suff[i] == s.size() - 1) {
            result[s.size() - 1] = -1;
            current = 0;
            continue;
        }
        for (int32 tmp = suff[reversal_suff[i] + 1]; max(i + current, tmp + current) < s.size() && s[i + current] == s[tmp + current]; ++current);
        result[reversal_suff[i]] = current;
    }
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
    string s;
    cin >> s;
    int32* suff = suffix_array(s + '\0');
    for (int32 i = 1; i < s.size() + 1; ++i) {
        cout << suff[i] + 1 << ' ';
    }
    cout << '\n';
    int32* lcpp = lcp(s + '\0', suff);
    for (int32 i = 1; i < s.size(); ++i) {
        cout << lcpp[i] << ' ';
    }
    re
}
