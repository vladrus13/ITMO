#define _CRT_SECURE_NO_WARNINGS
#include <iostream>
typedef long long ll;
using namespace std;
 
class Queue {
private:
    int *a;
    ll size, tail, num, head;
public:
    Queue(int x) 
    {
        a = (new int[2]);
        a[0] = x;
        size = (2);
        num = 1;
        head = (1);
        tail = (0);
    }
    void push(int x) {
        if (num == size) {
            int *b = new int[size * 2];
            ll it = 0;
            for (ll i = tail; i != head || it == 0; i = (i + 1) % size, it++) {
                b[it] = a[i];
            }
            a = b;
            size *= 2;
            head = it;
            tail = 0;
        }
        a[head] = x;
        num++;
        head = (head + 1) % size;
    }
    int pop() {
        int answer = a[tail];
        if (((head - tail + size) % size) * 4 < (tail - head + size) % size) {
            int *b = new int[size / 2];
            ll it = 0;
            for (ll i = tail; i != head; i = (i + 1) % size, it++) {
                b[it] = a[i];
            }
            a = b;
            size /= 2;
            head = it;
            tail = 0;
        }
        tail = (tail + 1) % size;
        num--;
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
    ll M;
    char command;
    int temp;
    cin >> M;
    cin >> command >> temp;
    Queue q(temp);
    M--;
    while (M--) { // or for (int i = 0; i < M; i++) что сьест у нас еще одну переменную.
        cin >> command;
        if (command == '+') {
            cin >> temp;
            q.push(temp);
        }
        else {
            // гарантируется, что не будет изьятия элемента из пустого стека
            // поэтому не стоит обременяться лишней работой и будем пока радоваться
            cout << q.pop() << '\n';
        }
    }
}