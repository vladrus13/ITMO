#define _CRT_SECURE_NO_WARNINGS
#include <iostream>
#include <cstdlib>
#include <string>
#include <algorithm>
typedef long long ll;
using namespace std;
 
class my_stack {
private:
    pair <int, int> *a;
    int size, num;
public:
    my_stack() {
        a = new pair <int, int>[30000002];
        size = 1;
        num = 0;
    }
    void push(int x) {
        a[num].second = (num == 0 ? x : min(x, a[num - 1].second));
        a[num].first = x;
        num++;
    }
    int pop() {
        num--;
        int answer = a[num].first;
        // NOTE: we cant control error-type (num = -1 and get in a -1 element. hah
 
        return answer;
    }
 
    int ssize() {
        return this->num;
    }
 
    int get_min() {
        if (num > 0) {
            num--;
            int answer = a[num].second;
            num++;
            return answer;
        }
        else return INT_MAX;
    }
 
    int back() {
        num--;
        int answer = a[num].first;
        num++;
        return answer;
    }
};
 
class mega_queue {
public:
    my_stack s1, s2;
    void push(int x) {
        s1.push(x);
    }
 
    int pop() {
        if (!s2.ssize()) {
            while (s1.ssize()) {
                int x = s1.pop();
                s2.push(x);
            }
        }
        return s2.pop();
    }
 
    int get_min() {
        return min(s1.get_min(), s2.get_min());
    }
};
 
mega_queue q;
 
int main() {
#ifdef _DEBUG
    freopen("input.txt", "r", stdin);
    freopen("output.txt", "w", stdout);
#endif
    // NOTE: speed uup!
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    int n, m, k;
    cin >> n >> m >> k;
    int a, b, c;
    cin >> a >> b >> c;
    int i2 = 0, i1 = 0;
    int t;
    ll sum = 0;
    for (int i = 0; i < n; i++) {
        if (k) {
            cin >> t;
            k--;
        }
        else {
            t = (a * i2) + (b * i1) + c;
        }
        q.push((ll)t);
        if (m - 2 < i) {
            sum += q.get_min();
            q.pop();
        }
        i2 = i1;
        i1 = t;
    }
    cout << sum;
}