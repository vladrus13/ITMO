
#include <iostream>

using namespace std;

string exec(const char* cmd) {
    char buffer[128];
    std::string result = "";
    FILE* pipe = popen(cmd, "r");
    if (!pipe) throw std::runtime_error("popen() failed!");
    try {
        while (fgets(buffer, sizeof buffer, pipe) != NULL) {
            result += buffer;
        }
    } catch (...) {
        pclose(pipe);
        throw;
    }
    pclose(pipe);
    return result;
}

int main() {
    unsigned int x = 0xd5283bec;
    x ^= 0xE5E5E5E5;
    x = __builtin_bswap32(x);
    x ^= 0x3A29E87F;
    x = __builtin_bswap32(x);
    x ^= 0x36478241;
    cout << x;
    return 0;
}
