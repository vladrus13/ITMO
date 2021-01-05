#include <iostream>
#include <set>
#include <vector>
#include <cstring>
#include <map>
#include <algorithm>

using namespace std;

int main() {
    string user;
    cin >> user;
    string id;
    cin >> id;
    for (int i = 0; i < user.size(); i++) {
        printf("%02x", (char) (user[i] ^ (id[i])) + 7);
    }
    return 0;
}
