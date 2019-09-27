#define _CRT_SECURE_NO_WARNINGS
#include <iostream>
#include <cstdlib>
#include <string>
typedef long long ll;
using namespace std;
 
class my_stack {
private:
    char *a;
    ll size, num;
public:
    my_stack() {
        a = new char[1];
        size = 1;
        num = 0;
    }
    ll ssize() {
        return this->num;
    }
    void push(char x) {
        if (size == num) {
            char *b = new char[size * 2];
            for (ll i = 0; i < num; i++) {
                b[i] = a[i];
            }
            a = b;
            size *= 2;
        }
        a[num] = x;
        num++;
    }
    char pop() {
        num--;
        char answer = a[num];
        // NOTE: we cant control error-type (num = -1 and get in a -1 element. hah
        if (size >= num * 4 && size > 1) {
            char *b = new char[size / 2];
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
    string str;
    while (cin >> str) {
        my_stack s;
        bool correct = true;
        for (size_t i = 0; i < str.size(); i++) {
            if (str[i] == '(') {
                s.push(str[i]);
            }
            if (str[i] == '[') {
                s.push(str[i]);
            }
            if (str[i] == ')') {
                if (s.ssize()) {
                    char last = s.pop();
                    if (last != '(') {
                        correct = false;
                    }
                }
                else {
                    correct = false;
                }
            }
            if (str[i] == ']') {
                if (s.ssize()) {
                    char last = s.pop();
                    if (last != '[') {
                        correct = false;
                    }
                }
                else {
                    correct = false;
                }
            }
        }
        if (s.ssize()) {
            correct = false;
        }
        if (correct) {
            cout << "YES\n";
        }
        else {
            cout << "NO\n";
        }
    }
}