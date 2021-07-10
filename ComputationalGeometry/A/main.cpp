#include <iostream>
#include <utility>
#include "seg_intersection_tests.h"
#include <gmpxx.h>
#include <cstddef>

// ща помру, почему 4 тест не проходит

template<typename T>
struct point {
    T x;
    T y;
};

template<typename T>
int areaSign(const point<T> &a, const point<T> &b, const point<T> &c) {
    auto current = (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
    double eps = abs(4 * numeric_limits<T>::epsilon() * ((b.x - a.x) * (c.y - a.y) + (b.y - a.y) * (c.x - a.x)));
    if (current > eps) {
        return 1;
    }
    if (current < -eps) {
        return -1;
    }
    mpq_class ax(a.x);
    mpq_class ay(a.y);
    mpq_class bx(b.x);
    mpq_class by(b.y);
    mpq_class cx(c.x);
    mpq_class cy(c.y);
    return sgn((bx - ax) * (cy - ay) - (by - ay) * (cx - ax));
}

namespace segment {

    template<typename T>
    struct segment {
        point<T> a;
        point<T> b;
    };

    template<typename T>
    bool bounding(T a, T b, T c, T d) {
        if (a > b) swap(a, b);
        if (c > d) swap(c, d);
        return max(a, c) <= min(b, d);
    }

    template<typename T>
    bool intersect(const segment<T> &a, const segment<T> &b) {
        return bounding(a.a.x, a.b.x, b.a.x, b.b.x) &&
               bounding(a.a.y, a.b.y, b.a.y, b.b.y) &&
               areaSign(a.a, a.b, b.a) * areaSign(a.a, a.b, b.b) <= 0 &&
               areaSign(b.a, b.b, a.a) * areaSign(b.a, b.b, a.b) <= 0;
    }
}

using namespace std;

int main() {
    int test;
    cin >> test;
    auto points = genTest(test);
    for (size_t i = 0; i < points.size(); i += 8) {
        cout << ((intersect(
                segment::segment<double>{
                        point<double>{points[i], points[i + 1]},
                        point<double>{points[i + 2], points[i + 3]}},
                segment::segment<double>{
                        point<double>{points[i + 4], points[i + 5]},
                        point<double>{points[i + 6], points[i + 7]}})
                 ) ? "Y" : "N");
    }
    return 0;
}
