#include <iostream>
#include <vector>
#include <set>
#include <algorithm>

namespace comparing {

    const double EPS = 1e-7;

    bool is_less(const double &a, const double &b) {
        return a + EPS < b;
    }

    [[maybe_unused]] bool is_bigger(const double &a, const double &b) {
        return a > b + EPS;
    }

    bool is_equals(const double &a, const double &b, const double &eps) {
        return std::abs(a - b) < eps;
    }

    bool is_equals(const double &a, const double &b) {
        return is_equals(a, b, EPS);
    }

    bool is_zero(const double &a) {
        return is_equals(a, 0.0);
    }
}

class Point {
public:
    double x;
    double y;
    int segment;

    Point(double x, double y) : x(x), y(y), segment(-1) {}

    Point() : x(0), y(0), segment() {}

    void set(int segment1) {
        segment = segment1;
    }

    friend Point operator+(const Point &l, const Point &r) {
        return Point(l.x + r.x, l.y + r.y);
    }

    friend Point operator-(const Point &l, const Point &r) {
        return Point(l.x - r.x, l.y - r.y);
    }

    friend bool operator<(const Point &l, const Point &r) {
        return comparing::is_less(l.x, r.x) || (comparing::is_equals(l.x, r.x) && comparing::is_less(l.y, r.y));
    }

    friend double operator*(const Point &l, const Point &r) {
        return l.x * r.y - l.y * r.x;
    }
};

std::istream &operator>>(std::istream &is, Point &p) {
    double x, y;
    is >> x >> y;
    p = Point(x, y);
    return is;
}

int left_rotate(const Point &a, const Point &b, const Point &c) {
    double r = (b - a) * (c - a);
    return comparing::is_zero(r) ? 0 : r > 0 ? 1 : -1;
}

class Segment {
public:
    Point start;
    Point finish;

    int number;

    Segment(Point &start, Point &finish, int number) : start(start), finish(finish), number(number) {
        if (this->finish < this->start) {
            std::swap(this->start, this->finish);
        }
        this->finish.set(number);
        this->start.set(number);
    }

    Segment() : start(), finish(), number(-1) {}

    double operator()(double x) const {
        if (comparing::is_equals(start.x, finish.x)) {
            return start.y;
        }
        return start.y + (finish.y - start.y) * (x - start.x) / (finish.x - start.x);
    }

    friend bool operator<(const Segment &l, const Segment &r) {
        if (l.number == r.number) return false;
        double general = std::max(l.start.x, r.start.x);
        double general_y_l = l(general);
        double general_y_r = r(general);
        return comparing::is_less(general_y_l, general_y_r);
    }

    static bool smol_intersect(double a1, double a2, double a3, double a4) {
        if (a1 > a2) {
            std::swap(a1, a2);
        }
        if (a3 > a4) {
            std::swap(a3, a4);
        }
        return std::max(a1, a3) <= std::min(a2, a4) + comparing::EPS;
    }

    friend bool operator==(const Segment &l, const Segment &r) {
        return smol_intersect(l.start.x, l.finish.x, r.start.x, r.finish.x) &&
               smol_intersect(l.start.y, l.finish.y, r.start.y, r.finish.y) &&
               left_rotate(l.start, l.finish, r.start) * left_rotate(l.start, l.finish, r.finish) <= 0 &&
               left_rotate(r.start, r.finish, l.start) * left_rotate(r.start, r.finish, l.finish) <= 0;
    }
};

int current_number = 1;

std::istream &operator>>(std::istream &is, Segment &p) {
    Point start = Point();
    Point finish = Point();
    is >> start >> finish;
    p = Segment(start, finish, current_number);
    current_number++;
    return is;
}

class Event {
public:
    Point point;
    bool is_open;

    Event(const Point &point, bool isOpen) : point(point), is_open(isOpen) {}

    friend bool operator<(const Event &l, const Event &r) {
        if (l.point < r.point) {
            return true;
        }
        if (r.point < l.point) {
            return false;
        }
        if (l.is_open) return true;
        if (r.is_open) return false;
        return true;
    }
};

std::optional<std::pair<int, int>> intersect(const std::vector<Segment> &segments) {
    std::set<Segment> set_segments;
    std::vector<Event> events;

    for (Segment segment : segments) {
        events.emplace_back(segment.start, true);
        events.emplace_back(segment.finish, false);
    }
    std::sort(events.begin(), events.end());

    for (Event event : events) {
        Segment segment = segments[event.point.segment - 1];
        if (event.is_open) {
            auto lower_iter = set_segments.lower_bound(segment);
            if (lower_iter != set_segments.begin() && lower_iter != set_segments.end()) {
                lower_iter--;
            } else {
                lower_iter = set_segments.end();
            }
            auto upper_iter = set_segments.lower_bound(segment);
            std::optional<Segment> lower =
                    lower_iter == set_segments.end() ? std::nullopt : std::make_optional(*lower_iter);
            std::optional<Segment> upper =
                    upper_iter == set_segments.end() ? std::nullopt : std::make_optional(*upper_iter);
            if (lower.has_value() && (lower.value() == segment)) {
                return std::make_optional(std::make_pair(lower->number, segment.number));
            }
            if (upper.has_value() && (upper.value() == segment)) {
                return std::make_optional(std::make_pair(upper->number, segment.number));
            }
            set_segments.insert(segment);
        } else {
            auto current = set_segments.find(segment);
            auto lower_iter = current;
            if (lower_iter != set_segments.begin() && lower_iter != set_segments.end()) {
                lower_iter--;
            } else {
                lower_iter = set_segments.end();
            }
            auto upper_iter = current;
            if (upper_iter != set_segments.end()) {
                upper_iter++;
            }
            std::optional<Segment> lower =
                    lower_iter == set_segments.end() ? std::nullopt : std::make_optional(*lower_iter);
            std::optional<Segment> upper =
                    upper_iter == set_segments.end() ? std::nullopt : std::make_optional(*upper_iter);
            if (lower.has_value() && (lower.value() == segment)) {
                return std::make_optional(std::make_pair(lower->number, segment.number));
            }
            if (upper.has_value() && (upper.value() == segment)) {
                return std::make_optional(std::make_pair(upper->number, segment.number));
            }
            if (upper.has_value() && lower.has_value() && (upper.value() == lower.value())) {
                return std::make_optional(std::make_pair(lower->number, upper->number));
            }
            set_segments.erase(segment);
        }
    }
    return std::nullopt;
}

int main() {
    std::ios_base::sync_with_stdio(false);
    std::cin.tie(nullptr);
    std::cout.tie(nullptr);
    std::vector<Segment> segments;
    int n;
    std::cin >> n;
    for (int i = 0; i < n; i++) {
        Segment temp = Segment();
        std::cin >> temp;
        segments.push_back(temp);
    }
    std::optional<std::pair<int, int> > result = intersect(segments);
    if (result.has_value()) {
        std::cout << "YES\n" << result.value().first << " " << result.value().second << std::endl;
    } else {
        std::cout << "NO\n";
    }
    return 0;
}
