#define _CRT_SECURE_NO_WARNINGS
#include <iostream>
#include <cstdlib>
typedef long long ll;
using namespace std;
 
class Stack {
public:
    ll value, size = 0;
    Stack * next, * prev;
    Stack(ll x, Stack *a)
    {
        value = x;
        next = a;
        prev = a;
    }
};
 
Stack * head = (Stack*)malloc(sizeof(Stack));
Stack * tail = NULL;
 
void push(ll x) {
    Stack *new_head = (Stack*)malloc(sizeof(Stack));
    new_head->value = x;
    new_head->prev = head;
    new_head->size = head->size + 1;
    head->next = new_head;
    head = new_head;
    if (head->size == 1) {
        tail = head;
    }
}
 
ll pop() {
    ll answer = tail->value;
    tail = tail->next;
    head->size--;
    return answer;
}
 
int main() {
#ifdef _DEBUG
    freopen("input.txt", "r", stdin);
    freopen("output.txt", "w", stdout);
#endif
    // NOTE: speed uup!
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    head->size = 0;
    ll M;
    char command;
    int temp;
    cin >> M;
    while (M--) { // or for (int i = 0; i < M; i++) что сьест у нас еще одну переменную.
        cin >> command;
        if (command == '+') {
            cin >> temp;
            push(temp);
        }
        else {
            // гарантируется, что не будет изьятия элемента из пустого стека
            // поэтому не стоит обременяться лишней работой и будем пока радоваться
            cout << pop() << '\n';
        }
    }
}