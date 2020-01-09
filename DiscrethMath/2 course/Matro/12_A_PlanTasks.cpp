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

int main() {
    // begin of my useless prog
#ifdef MY_DEBUG
    freopen("input.txt", "r", stdin);
    freopen("output.txt", "w", stdout);
#else
    freopen("schedule.in", "r", stdin);
    freopen("schedule.out", "w", stdout);
#endif
    ios_base::sync_with_stdio(NULL);
    cin.tie(NULL);
    cout.tie(NULL);
    int32 n;
    cin >> n;
    set <pair <int32, int32> > tasks;
    set <int32> time;
    int32 v, u;
    for (int32 i = 0; i < n; i++) {
        cin >> u >> v;
        tasks.insert({v, u});
        time.insert(i + 1);
    }
    ll answer = 0;
    while (!tasks.empty() && !time.empty()) {
        auto it_task = --tasks.end();
        int32 timer = it_task->second;
        auto it = time.lower_bound(timer);
        if (it != time.begin() && *it != timer) {
            --it;
        }
        if (*it > timer) {
            answer += it_task->first;
        } else {
            time.erase(it);
        }
        tasks.erase(it_task);
    }
    cout << answer << endl;
    re
}