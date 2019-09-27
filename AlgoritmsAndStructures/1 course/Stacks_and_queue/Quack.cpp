#define _CRT_SECURE_NO_WARNINGS
#include <iostream>
#include <cstdlib>
#include <string>
#include <algorithm>
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
 
class Stack_string {
private:
    string *a;
    int size, num;
public:
    Stack_string() {
        a = new string[1];
        size = 1;
        num = 0;
    }
    void push(string x) {
        if (size == num) {
            string *b = new string[size * 2];
            for (ll i = 0; i < num; i++) {
                b[i] = a[i];
            }
            a = b;
            size *= 2;
        }
        a[num] = x;
        num++;
    }
    string pop() {
        num--;
        string answer = a[num];
        // NOTE: we cant control error-type (num = -1 and get in a -1 element. hah
        if (size >= num * 4 && size > 1) {
            string *b = new string[size / 2];
            for (ll i = 0; i < num; i++) {
                b[i] = a[i];
            }
            a = b;
            size /= 2;
        }
        return answer;
    }
 
    string get(int i) {
        return a[i];
    }
 
    int ssize() {
        return size;
    }
};
 
class container {
private:
    pair <string, int> *a;
    int size;
public:
    container(pair <string, int> *b, int s)
        :a(b), size(s)
    {}
 
    void hiper() {
        sort(a, a + size);
    }
 
    int get(string s) {
        int l = 0, r = size - 1, m;
        for (int i = 0; i < 100; i++) {
            m = (l + r) / 2;
            if (a[m].first < s) {
                r = m;
            }
            else {
                l = m;
            }
        }
        while (s > a[m].first) {
            m++;
        }
        while (s < a[m].first) {
            m--;
        }
        if (a[m].first != s) {
            exit(1);
        }
        return a[m].second;
    }
};
 
int mod(int x) {
    while (x < 0) x += 65536;
    return x % 65536;
}
 
int main() {
#ifdef _DEBUG
    freopen("input.txt", "r", stdin);
    freopen("output.txt", "w", stdout);
#endif
    // NOTE: speed uup!
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    // aghhh. i will used commit, but my inglish is very very bad.
    Stack_string commands;
    string reader = "", ttemp;
    while (cin >> reader) {
        commands.push(reader);
    }
    // okeeeey. we read all command aaand we can it parse
    int times_goto = 0;
    for (int i = 0; i < commands.ssize(); i++) {
        if (commands.get(i)[0] == ':') times_goto++;
    }
    pair <string, int> *temp = new pair<string, int>[times_goto];
    for (int i = 0, yk = 0; i < commands.ssize(); i++) {
        if (commands.get(i)[0] == ':') {
            temp[yk] = make_pair("", i);
            ttemp = commands.get(i);
            for (int j = 1; j < (int) ttemp.size(); j++) {
                temp[yk].first += ttemp[j];
            }
            yk++;
        }
    }
    container goto_command(temp, times_goto);
    goto_command.hiper();
    Queue q(1);
    int a, b;
    int regist[27];
    bool flag = false;
    for (int i = 0; i < commands.ssize(); i++) {
        string comm = commands.get(i);
        if (comm[0] == '+') {
            a = q.pop();
            b = q.pop();
            q.push(mod(a + b));
        }
        if (comm[0] == '-' && comm.size() == 1) {
            a = q.pop();
            b = q.pop();
            q.push(mod(a - b));
        }
        if (comm[0] == '*') {
            a = q.pop();
            b = q.pop();
            q.push(mod(a * b));
        }
        if (comm[0] == '/') {
            a = q.pop();
            b = q.pop();
            q.push(mod(b == 0 ? 0 : mod(a / b)));
        }
        if (comm[0] == '%') {
            a = q.pop();
            b = q.pop();
            q.push(mod(b == 0 ? 0 : mod(a % b)));
        }
        if (comm[0] == '>') {
            a = q.pop();
            regist[comm[1] - 'a'] = a;
        }
        if (comm[0] == '<') {
            q.push(regist[comm[1] - 'a']);
        }
        if (comm[0] == 'P' && (comm.size() == 1 || !(comm[1] >= 'a' && comm[1] <= 'z'))) {
            a = q.pop();
            cout << a << endl;
        }
        if (comm[0] == 'P' && (comm.size() == 2 || (comm[1] >= 'a' && comm[1] <= 'z'))) {
            cout << regist[comm[1] - 'a'] << endl;
        }
        if (comm[0] == 'C' && (comm.size() == 1 || !(comm[1] >= 'a' && comm[1] <= 'z'))) {
            a = q.pop();
            cout << (char)(a % 256);
        }
        if (comm[0] == 'C' && (comm.size() == 2 || (comm[1] >= 'a' && comm[1] <= 'z'))) {
            cout << (char)(regist[comm[1] - 'a'] % 256);
        }
        if (comm[0] == 'J') {
            string lab = "";
            for (int i = 1; i < (int)comm.size(); i++) {
                lab += comm[i];
            }
            i = goto_command.get(lab);
        }
        if (comm[0] == 'Z') {
            if (regist[comm[1] - 'a'] == 0) {
                string lab = "";
                for (int i = 2; i < (int)comm.size(); i++) {
                    lab += comm[i];
                }
                i = goto_command.get(lab);
            }
        }
        if (comm[0] == 'E') {
            if (regist[comm[1] - 'a'] == regist[comm[2] - 'a']) {
                string lab = "";
                for (int i = 3; i < (int)comm.size(); i++) {
                    lab += comm[i];
                }
                i = goto_command.get(lab);
            }
        }
        if (comm[0] == 'G') {
            if (regist[comm[1] - 'a'] > regist[comm[2] - 'a']) {
                string lab = "";
                for (int i = 3; i < (int)comm.size(); i++) {
                    lab += comm[i];
                }
                i = goto_command.get(lab);
            }
        }
        if (comm[0] == 'Q') {
            return 0;
        }
        if ((comm[0] <= '9' && comm[0] >= '0') || (comm[0] == '-' && comm.size() != 1 && (comm[1] >= '0' && comm[1] <= '9'))) {
            int ans = 0;
            for (int i = 0; i < (int)comm.size(); i++) {
                ans = ans * 10 + comm[i] - '0';
            }
            //int x = q.pop();
            if (!flag) {
                q = Queue(mod(ans));
                flag = 1;
            }
            else {
                q.push(mod(ans));
            }
        }
    }
}