/// what about the useless text at the beginning
//                               of the program?
///                 I like to do useless things.
//       The program is made by vladrus13 (2020)

// Do you want to optimize?
#define _CRT_SECURE_NO_WARNINGS

#include <iostream>
#include <algorithm>
#include <vector>
#include <string>
#include <list>
#include <unordered_map>
#include <unordered_set>

typedef int32_t int32;

using namespace std;

//////////////////////////////////////////////////////////////////
// SYSTEM STARTS

const int INF = 1000000000;

//////////////////////////////////////////////////////////////////
// UTILS

/////////////////////////////////////////////////////////////////
// MAIN

bool dfs(int32 v, vector<unordered_map<int32, int32>> &g, vector<int32> &par, vector<bool> &used) {
    if (used.at(v)) return false;
    used.at(v) = true;
    for (auto u : g.at(v)) {
        int32 j = u.first;
        if (par.at(j) == -1 || dfs(par.at(j), g, par, used)) {
            par.at(j) = v;
            return true;
        }
    }
    return false;
}

vector<int32> rules1(vector<unordered_map<int32, int32>> &g, int32 n1, int32 m2, vector<int32> &ord) {
    vector<int32> par(m2 + 1, -1);
    vector<bool> used(n1 + 1);
    for (int32 i = 1; i <= n1; i++) {
        fill(used.begin(), used.end(), false);
        dfs(ord.at(i), g, par, used);
    }
    return par;
}

void dfs2(int32 v, int32 iz, int32 parent, vector<unordered_set<int32>> &gpar1, vector<unordered_set<int32>> &gpar2, vector<int32> &path, vector<bool> &used1, vector<bool> &used2) {
    path.push_back(v);
    used1.at(v) = true;
    int32 next = -1;
    for (auto t : gpar1.at(v)) {
        if (t != parent) next = t;
    }
    if (next == -1) return;
    dfs2(next, iz ^ 3, v, gpar2, gpar1, path, used2, used1);
}

void findCyclin(int32 i, vector<unordered_set<int32>> &gpar1, vector<unordered_set<int32>> &gpar2, vector<bool> &used1, vector<bool> &used2) {
    if (used1.at(i)) return;
    used1.at(i) = true;
    int32 pather = *gpar1.at(i).begin();
    used2.at(pather) = true;
    auto it = gpar2.at(pather).begin();
    if ((*it) == i) it++;
    int32 current = *it;
    gpar2.at(pather).erase(current);
    gpar1.at(current).erase(pather);
    findCyclin(current, gpar1, gpar2, used1, used2);
}

inline void helperHelper(vector<unordered_set<int32>> &gpar1, vector<unordered_set<int32>> &gpar2, vector<bool> &used1, vector<bool> &used2, vector<int32> &a1, vector<int32> &path) {
    if (a1.at(path.at(0)) > a1.at(*(--path.end()))) {
        for (int32 j = 1; j < path.size(); j += 2) {
            gpar2.at(path.at(j)).erase(path.at(j + 1));
            gpar1.at(path.at(j + 1)).erase(path.at(j));
        }
    } else {
        for (int32 j = 0; j < path.size() - 1; j += 2) {
            gpar1.at(path.at(j)).erase(path.at(j + 1));
            gpar2.at(path.at(j + 1)).erase(path.at(j));
        }
    }
}

inline void helperGetPath(int32 v, vector<unordered_set<int32>> &gpar1, vector<unordered_set<int32>> &gpar2, vector<bool> &used1, vector<bool> &used2, vector<int32> &a1) {
    vector<int32> path;
    dfs2(v, 1, -10, gpar1, gpar2, path, used1, used2);
    if (path.size() % 2 == 0) {
        for (int32 j = 1; j < path.size() - 1; j += 2) {
            gpar2.at(path.at(j)).erase(path.at(j + 1));
            gpar1.at(path.at(j + 1)).erase(path.at(j));
        }
    } else {
        helperHelper(gpar1, gpar2, used1, used2, a1, path);
    }
}

void getPaths(vector<unordered_set<int32>> &gpar1, vector<unordered_set<int32>> &gpar2, vector<bool> &used1, vector<bool> &used2, vector<int32> &a1) {
    for (int32 i = 1; i < gpar1.size(); i++) {
        if (!used1.at(i)) {
            if (gpar1.at(i).size() == 1) {
                helperGetPath(i, gpar1, gpar2, used1, used2, a1);
            }
        }
    }
}

inline void read2(int32 &n, int32 &m, int32 &e, vector<int32> &a1, vector<int32> &a2, vector<pair<int32, int32> > &ord1p, vector<pair<int32, int32> > &ord2p, vector<unordered_map<int32, int32>> &g1, vector<unordered_map<int32, int32>> &g2) {
    for (int32 i = 1; i <= n; i++) {
        cin >> a1.at(i);
    }
    for (int32 i = 1; i <= m; i++) {
        cin >> a2.at(i);
    }
    for (int32 i = 1; i <= n; i++) {
        ord1p[i] = {a1[i], i};
    }
    for (int32 i = 1; i <= m; i++) {
        ord2p[i] = {a2[i], i};
    }
    int32 a, b;
    for (int32 i = 1; i <= e; i++) {
        cin >> a >> b;
        g1.at(a).insert({b, i});
        g2.at(b).insert({a, i});
    }
}

inline void presolve(int32 &n, int32 &m, int32 &e, vector<int32> &a1, vector<int32> &a2, vector<pair<int32, int32> > &ord1p, vector<pair<int32, int32> > &ord2p, vector<unordered_map<int32, int32>> &g1, vector<unordered_map<int32, int32>> &g2, vector<unordered_set<int32> > &gpar1, vector<unordered_set<int32> > &gpar2, vector<int32> &ord1, vector<int32> &ord2) {
    sort(ord1p.begin(), ord1p.end());
    sort(ord2p.begin(), ord2p.end());
    for (int32 i = ord1p.size() - 1; i >= 0; i--) {
        ord1[ord1p.size() - 1 - i] = ord1p[i].second;
    }
    for (int32 i = ord2p.size() - 1; i >= 0; i--) {
        ord2[ord2p.size() - 1 - i] = ord2p[i].second;
    }
    vector<int32> par1 = rules1(g1, n, m, ord1);
    vector<int32> par2 = rules1(g2, m, n, ord2);
    for (int32 i = 1; i < par1.size(); i++) {
        if (par1.at(i) != -1) {
            gpar2.at(i).insert(par1.at(i));
            gpar1.at(par1.at(i)).insert(i);
        }
    }
    for (int32 i = 1; i < par2.size(); i++) {
        if (par2.at(i) != -1) {
            gpar1.at(i).insert(par2.at(i));
            gpar2.at(par2.at(i)).insert(i);
        }
    }

}

inline void postsolve(vector<unordered_set<int32> > &gpar1, vector<unordered_set<int32> > &gpar2, vector <bool> &used1, vector <bool> &used2, vector<int32> &a1, vector<int32> &a2) {
    getPaths(gpar1, gpar2, used1, used2, a1);
    getPaths(gpar2, gpar1, used2, used1, a2);
    for (int32 i = 1; i < gpar1.size(); i++) {
        if (gpar1.at(i).size() == 2) {
            findCyclin(i, gpar1, gpar2, used1, used2);
        }
    }
}

inline void write(vector<unordered_set<int32> > &gpar1, vector<unordered_map<int32, int32>> &g1, vector<int32> &a1, vector<int32> &a2) {
    vector<int32> answer;
    int32 sum = 0;
    for (int32 i = 1; i < gpar1.size(); i++) {
        if (gpar1.at(i).size() == 1) {
            answer.push_back(g1.at(i).at(*gpar1.at(i).begin()));
            sum += a1.at(i) + a2.at(*gpar1.at(i).begin());
        }
    }
    cout << sum << endl << answer.size() << endl;
    for (int32 it : answer) {
        cout << it << ' ';
    }
    cout << endl;
}

void solve() {
    int32 n, m, e;
    cin >> n >> m >> e;
    vector<unordered_map<int32, int32>> g1(n + 1, unordered_map<int32, int32>());
    vector<unordered_map<int32, int32>> g2(m + 1, unordered_map<int32, int32>());
    vector<unordered_set<int32> > gpar1(n + 1, unordered_set<int32>());
    vector<unordered_set<int32> > gpar2(m + 1, unordered_set<int32>());
    vector<int32> a1(n + 1);
    vector<int32> a2(m + 1);
    vector<pair<int32, int32> > ord1p(n + 1);
    vector<pair<int32, int32> > ord2p(m + 1);
    vector<int32> ord1(n + 1);
    vector<int32> ord2(m + 1);
    ord1p[0] = {INF, 0};
    ord2p[0] = {INF, 0};
    read2(n, m, e, a1, a2, ord1p, ord2p, g1, g2);
    presolve(n, m, e, a1, a2, ord1p, ord2p, g1, g2, gpar1, gpar2, ord1, ord2);
    vector<bool> used1(n + 1, false);
    vector<bool> used2(m + 1, false);
    postsolve(gpar1, gpar2, used1, used2, a1, a2);
    write(gpar1, g1, a1, a2);
}

int main() {
    // begin of my useless prog
#ifdef MY_DEBUG
    freopen("input.txt", "r", stdin);
    freopen("output.txt", "w", stdout);
#else
#endif
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);
    cout.tie(nullptr);
    solve();
    return 0;
}
