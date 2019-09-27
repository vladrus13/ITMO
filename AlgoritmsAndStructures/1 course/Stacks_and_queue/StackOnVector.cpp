//#define _CRT_SECURE_NO_WARNINGS
#include <iostream>
typedef long long ll;
using namespace std;
 
class my_stack {
private:
    ll *a;
    ll size, num;
public:
    my_stack() {
        a = new ll [1];
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
    //freopen("stack1.in", "r", stdin);
    //freopen("stack1.out", "w", stdout);
    // NOTE: speed uup!
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    ll M;
    char command;
    ll temp;
    cin >> M;
    my_stack s;
    while (M--) { // or for (int i = 0; i < M; i++) ??? ????? ? ??? ??? ???? ??????????.
        cin >> command;
        if (command == '+') {
            cin >> temp;
            s.push(temp);
        }
        else {
            // ?????????????, ??? ?? ????? ??????? ???????? ?? ??????? ?????
            // ??????? ?? ????? ???????????? ?????? ??????? ? ????? ???? ??????????
            cout << s.pop() << '\n';
        }
    }
}