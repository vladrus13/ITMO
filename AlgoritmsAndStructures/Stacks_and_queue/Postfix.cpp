#define _CRT_SECURE_NO_WARNINGS
#include <iostream>
#include <cstdlib>
#include <string>
typedef long long ll;
using namespace std;
 
class my_stack {
private:
    ll *a;
    ll size, num;
public:
    my_stack() {
        a = new ll[1];
        size = 1;
        num = 0;
    }
    void push(ll x) {
        if (size == num) {
            ll *b = new ll[size * 2];
            for (ll i = 0; i < num; i++) {
                b[i] = a[i];
            }
            a = b;
            size *= 2;
        }
        a[num] = x;
        num++;
    }
    ll pop() {
        num--;
        ll answer = a[num];
        // NOTE: we cant control error-type (num = -1 and get in a -1 element. hah
        if (size >= num * 4 && size > 1) {
            ll *b = new ll[size / 2];
            for (ll i = 0; i < num; i++) {
                b[i] = a[i];
            }
            a = b;
            size /= 2;
        }
        return answer;
    }
};
 
int main() {
#ifdef _DEBUG
    freopen("input.txt", "r", stdin);
    freopen("output.txt", "w", stdout);
#endif
    // NOTE: speed uup!
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    char c;
    my_stack s;
    ll a, b;
    while (cin >> c) {
        if (c <= '9' && c >= '0') {
            int i = (int) (c - '0');
            s.push(i);
        }
        if (c == '+') {
            a = s.pop();
            b = s.pop();
            s.push(a + b);
        }
        if (c == '-') {
            b = s.pop();
            a = s.pop();
            s.push(a - b);
        }
        if (c == '*') {
            a = s.pop();
            b = s.pop();
            s.push(a * b);
        }
    }
    cout << s.pop();
}