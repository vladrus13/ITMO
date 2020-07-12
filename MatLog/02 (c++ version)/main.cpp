#include <fstream>
#include "SolveE.h"

int main() {
#ifdef _DEBUG
    // this is for debug. Set _DEBUG like my CMake, set 0 at "if" if you want "test", 1 else
    // run differ.sh for testing. this is small testing system.
    if (0) {
        setInputName("test");
        solve();
    } else {
        vector<string> inputs = {"test_in/01", "test_in/02", "test_in/A", "test_in/B", "test_in/C", "test_in/D",
                                 "test_in/E", "test_in/intro", "test_in/intro2"};
        for (string &s : inputs) {
            setInputName(s);
            solve();
        }
    }
#else
    solve();
#endif
    return 0;
}
