//
// Created by vladkuznetsov on 02.04.2021.
//

#ifndef A_SEG_INTERSECTION_TESTS_H
#define A_SEG_INTERSECTION_TESTS_H

#include <vector>
#include <cassert>

using namespace std;

vector < double > genTest(int testId) {
    if (testId == 1) {
        vector<double> data{
                0, 2, 2, 1,
                1, 0, 2, 2,
        };
        return data;
    }
    if (testId == 2) {
        vector<double> data{
                1, 1, 1, 1,
                2, 2, 2, 2,

                1, 1, 1, 1,
                1, 1, 1, 1,

                1, 1, 1, 1,
                2, 2, 1, 1,

                1, 0, 2, 0,
                3, 0, 3, 0
        };
        return data;
    }
    assert(false);
}

#endif //A_SEG_INTERSECTION_TESTS_H
