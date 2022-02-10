#include <iostream>
#include <utility>
#include <vector>
#include <unordered_map>
#include <map>
#include <random>
#include <algorithm>
#include <iomanip>
#include <fstream>
#include <filesystem>

class Input {
public:
    int n, m;

    friend std::istream &operator>>(std::istream &in, Input &input) {
        in >> input.n >> input.m;
        return in;
    }

    friend std::ostream &operator<<(std::ostream &output, const Input &input) {
        std::vector<std::string> real;
        output << input.n << ' ' << input.m;
        return output;
    }
};

class Permutation_Matrix {
public:

    inline static bool produceAOnNumbers(const std::vector<bool> &a, const std::vector<std::pair<bool, int>> &b) {
        bool c = false;
        for (const std::pair<bool, int> &it: b) {
            c = c xor (it.first and (a[it.second]));
        }
        return c;
    }

    inline static std::vector<std::vector<bool>>
    produce(const std::vector<std::vector<bool>> &a, const std::vector<std::vector<bool>> &b) {
        std::vector<std::vector<bool>> result(a.size(), std::vector<bool>(b[0].size(), false));
        for (size_t i = 0; i < a.size(); i++) {
            for (size_t j = 0; j < b[0].size(); j++) {
                for (size_t k = 0; k < a[0].size(); k++) {
                    result[i][j] = result[i][j] xor (a[i][k] and b[k][j]);
                }
            }
        }
        return result;
    }
};

class Binary_Node {

public:
    Binary_Node *on0 = nullptr;
    Binary_Node *on1 = nullptr;
    std::vector<std::pair<bool, int>> code;
    int numberOnLayer = -1;

    Binary_Node() = default;

    inline void setOn0(Binary_Node *on0T) {
        Binary_Node::on0 = on0T;
    }

    inline void setOn1(Binary_Node *on1T) {
        Binary_Node::on1 = on1T;
    }

    inline void setCode(const std::vector<std::pair<bool, int>> &new_code) {
        code = new_code;
    }

    [[nodiscard]] std::string to_string() const {
        if (code.empty()) {
            return "e";
        }
        std::string con;
        for (auto it: code) {
            con += (it.first ? "1" : "0");
        }
        return con;
    }
};

class Binary_Graph {
public:
    Binary_Node *start = nullptr;

    std::vector<std::vector<Binary_Node *>> layers;

    Binary_Graph() = default;

    explicit Binary_Graph(Binary_Node *start) : start(start) {}

    friend std::ostream &operator<<(std::ostream &output, const Binary_Graph &bg) {
        std::vector<std::string> real;
        output << "Paths\n";
        rec(real, "", bg.start);
        for (const auto &it: real) {
            output << it << '\n';
        }
        output << "Layers\n";
        for (const auto &it: bg.layers) {
            output << it.size() << ' ';
        }
        output << '\n';
        return output;
    }

    std::vector<int> levels() {
        std::vector<int> answer;
        for (const auto &it: layers) {
            answer.emplace_back(it.size());
        }
        return answer;
    }

    [[nodiscard]] inline std::vector<bool> decode(const std::vector<double> &a) const {
        std::vector<double> dp(1, 1e17);
        std::vector<std::vector<int>> parent(layers.size());
        for (int i = 0; i < layers.size(); i++) {
            parent[i].resize(layers[i].size());
        }
        dp[start->numberOnLayer] = 0;
        parent[0][start->numberOnLayer] = -1;
        for (int step = 1; step <= a.size(); step++) {
            double symbol = a[step - 1];
            std::vector<double> next_dp(this->layers[step].size(), 1e17);
            for (int i = 0; i < dp.size(); i++) {
                Binary_Node *node = this->layers[step - 1][i];
                if (node->on0 != nullptr) {
                    double distance = std::abs(1.0 - symbol);
                    if (next_dp[node->on0->numberOnLayer] > dp[i] + distance) {
                        next_dp[node->on0->numberOnLayer] = dp[i] + distance;
                        parent[step][node->on0->numberOnLayer] = i;
                    }
                }
                if (node->on1 != nullptr) {
                    double distance = std::abs(-1.0 - symbol);
                    if (next_dp[node->on1->numberOnLayer] > dp[i] + distance) {
                        next_dp[node->on1->numberOnLayer] = dp[i] + distance;
                        parent[step][node->on1->numberOnLayer] = i;
                    }
                }
            }
            dp = next_dp;
        }
        std::vector<bool> answer;
        int number_on_layer = 0;
        for (size_t layer = layers.size() - 1; layer >= 1; layer--) {
            Binary_Node *prev = this->layers[layer - 1][parent[layer][number_on_layer]];
            if (prev->on0 != nullptr && prev->on0->numberOnLayer == number_on_layer) answer.push_back(false);
            else answer.push_back(true);
            number_on_layer = prev->numberOnLayer;
        }
        std::reverse(answer.begin(), answer.end());
        return answer;
    }

private:

    static inline double getNode(std::map<Binary_Node *, double> &dp, Binary_Node *key) {
        if (dp.count(key) == 0) {
            dp[key] = 1e14;
        }
        return dp[key];
    }


    static void rec(std::vector<std::string> &c, const std::string &p, Binary_Node *node) {
        std::string n = p + node->to_string();
        if (node->on0 == nullptr && node->on1 == nullptr) {
            std::string x = n;
            c.emplace_back(x);
        } else {
            std::string primary = n;
            primary += " -";
            if (node->on0 != nullptr) {
                rec(c, primary + "0- ", node->on0);
            }
            if (node->on1 != nullptr) {
                rec(c, primary + "1- ", node->on1);
            }
        }
    }
};

class Binary_Matrix {
public:
    size_t n, m;
    std::vector<std::vector<bool>> matrix;
    std::vector<std::pair<int, int>> active;

    Binary_Matrix() : n(0), m(0) {}

    void set_matrix(const std::vector<std::vector<bool>> &new_matrix) {
        Binary_Matrix::matrix = new_matrix;
        n = new_matrix[0].size();
        m = new_matrix.size();
    }

    void set_dimension(const Input &input) {
        Binary_Matrix::n = input.n;
        Binary_Matrix::m = input.m;
    }

    friend std::istream &operator>>(std::istream &in, Binary_Matrix &input) {
        std::vector<std::vector<bool>> new_matrix(input.m, std::vector<bool>(input.n));
        int x;
        for (int i = 0; i < input.m; i++) {
            for (int j = 0; j < input.n; j++) {
                in >> x;
                new_matrix[i][j] = x == 1;
            }
        }
        input.set_matrix(new_matrix);
        return in;
    }

    Binary_Matrix &operator=(const Binary_Matrix &binary_matrix) {
        this->set_matrix(binary_matrix.matrix);
        return *this;
    }

    void minimal_span_form() {
        minimal_span_form_down();
        minimal_span_form_up();
    }

    void calculate_active() {
        active.clear();
        active.resize(m);
        for (int row = 0; row < m; row++) {
            int it = 0;
            while (matrix[row][it] == 0) it++;
            int start = it;
            it = (int) n - 1;
            while (matrix[row][it] == 0) it--;
            int finish = it - 1;
            active[row] = {start, finish};
        }
    }

    Binary_Graph *get_graph() {
        auto *start = new Binary_Node();
        start->numberOnLayer = 0;
        auto *graph = new Binary_Graph(start);
        std::vector<Binary_Node *> previous_layer;
        std::vector<int> numbers_of_previous_active_nodes;
        previous_layer.push_back(start);
        graph->layers.resize(n + 1);
        graph->layers[0].push_back(start);
        for (int layer_number = 1; layer_number < n + 1; layer_number++) {
            int matrix_layer_number = layer_number - 1;
            std::vector<bool> column(m);
            for (int i = 0; i < m; i++) {
                column[i] = matrix[i][matrix_layer_number];
            }
            int new_layer = -1;
            std::vector<int> numbers_of_active_nodes;
            for (int i = 0; i < m; i++) {
                if (active[i].first <= matrix_layer_number && matrix_layer_number <= active[i].second) {
                    numbers_of_active_nodes.push_back(i);
                }
                if (active[i].first == matrix_layer_number) {
                    new_layer = i;
                }
            }
            std::map<std::vector<std::pair<bool, int>>, Binary_Node *> next_layer;
            for (Binary_Node *node: previous_layer) {
                if (new_layer == -1) {
                    std::vector<std::pair<bool, int>> new_vector = node->code;
                    bool edge = Permutation_Matrix::produceAOnNumbers(column, new_vector);
                    remove_old(new_vector, matrix_layer_number);
                    Binary_Node *new_node = get_node(next_layer, new_vector);
                    if (edge) {
                        node->setOn1(new_node);
                    } else {
                        node->setOn0(new_node);
                    }
                } else {
                    std::vector<std::pair<bool, int>> on0 = node->code;
                    on0.emplace_back(0, new_layer);
                    std::vector<std::pair<bool, int>> on1 = node->code;
                    on1.emplace_back(1, new_layer);
                    bool edgeOn1 = Permutation_Matrix::produceAOnNumbers(column, on1);
                    remove_old(on0, matrix_layer_number);
                    remove_old(on1, matrix_layer_number);
                    Binary_Node *nodeOn0 = get_node(next_layer, on0);
                    Binary_Node *nodeOn1 = get_node(next_layer, on1);
                    if (edgeOn1) {
                        node->setOn0(nodeOn0);
                        node->setOn1(nodeOn1);
                    } else {
                        node->setOn0(nodeOn1);
                        node->setOn1(nodeOn0);
                    }
                }
            }
            previous_layer.clear();
            int counter = 0;
            for (const std::pair<std::vector<std::pair<bool, int>>, Binary_Node *> it: next_layer) {
                previous_layer.push_back(it.second);
                it.second->numberOnLayer = counter;
                counter++;
            }
            graph->layers[layer_number] = previous_layer;

        }
        return graph;
    }

    friend std::ostream &operator<<(std::ostream &output, const Binary_Matrix &bm) {
        for (const auto &row: bm.matrix) {
            for (bool x: row) {
                output << (x ? "1" : "0") << ' ';
            }
            output << "\n";
        }
        if (!bm.active.empty()) {
            for (auto pair: bm.active) {
                output << pair.first << ' ' << pair.second << '\n';
            }
        }
        return output;
    }

    [[nodiscard]] std::vector<bool> encode(const std::vector<bool> &a) const {
        return Permutation_Matrix::produce(std::vector<std::vector<bool>>{a}, this->matrix)[0];
    }

private:
    void remove_old(std::vector<std::pair<bool, int>> &a, int matrix_layer_number) {
        for (int i = 0; i < a.size(); i++) {
            if (active[a[i].second].second < matrix_layer_number) {
                a.erase(a.begin() + i);
                break;
            }
        }
    }

    static inline Binary_Node *get_node(std::map<std::vector<std::pair<bool, int>>, Binary_Node *> &next_layer,
                                        const std::vector<std::pair<bool, int>> &key) {
        if (next_layer.count(key) == 0) {
            auto *binaryNode = new Binary_Node();
            binaryNode->setCode(key);
            next_layer[key] = binaryNode;
        }
        return next_layer[key];
    }


    inline void xor_rows(size_t from, size_t to) {
        for (int i = 0; i < n; i++) {
            matrix[to][i] = matrix[to][i] ^ matrix[from][i];
        }
    }

    void minimal_span_form_down() {
        int column = (int) -1;
        for (size_t target_row = 0; target_row < m; target_row++) {
            column++;
            bool isClear = true;
            if (!matrix[target_row][column]) {
                isClear = false;
                for (size_t row = target_row + 1; row < m; row++) {
                    if (matrix[row][column]) {
                        xor_rows(row, target_row);
                        isClear = true;
                        break;
                    }
                }
            }
            if (isClear) {
                for (size_t row = target_row + 1; row < m; row++) {
                    if (matrix[row][column]) {
                        xor_rows(target_row, row);
                    }
                }
            } else {
                target_row--;
            }
        }
    }

    void minimal_span_form_up() {
        std::vector<bool> free(m, true);
        size_t column = n;
        for (size_t step = 0; step < m; step++) {
            int count = 0;
            column--;
            for (size_t i = 0; i < m; i++) {
                if (free[i] && matrix[i][column]) {
                    count++;
                }
            }
            if (count > 0) {
                size_t last = m - 1;
                while (!(matrix[last][column] && free[last])) {
                    last--;
                }
                free[last] = false;
                if (count > 1) {
                    for (size_t i = 0; i < last; i++) {
                        if (matrix[i][column] && free[i]) {
                            xor_rows(last, i);
                        }
                    }
                }
            } else {
                step--;
            }

        }
    }
};

void write(std::ostream &output, const std::vector<bool> &a) {
    for (bool it: a) {
        output << (it ? "1" : "0") << ' ';
    }
}

void generator() {
    std::random_device rd{};
    std::mt19937 gen{rd()};
    std::uniform_int_distribution<int> uniformBooleanDistribution(0, 1);
    std::uniform_int_distribution<int> uniformNKDistribution(0, 10);
    for (int test = 50; test < 100; test++) {
        std::cout << "=== Test: " << test << std::endl;
        if (std::filesystem::exists("tests/graph" + std::to_string(test) + ".in") &&
            std::filesystem::file_size("tests/graph" + std::to_string(test) + ".in") != 0) {
            std::cout << "Test: " << test << " exists!" << std::endl;
            continue;
        }

        std::ofstream of_input_matrix("tests/input" + std::to_string(test) + ".in");
        std::ofstream of_span_form_matrix("tests/span" + std::to_string(test) + ".in");
        std::ofstream of_graph("tests/graph" + std::to_string(test) + ".in");
        bool _successful = false;
        while (!_successful) {
            try {
                int _m = uniformNKDistribution(gen);
                int _n = _m + uniformNKDistribution(gen);

                Input input{};
                input.m = _m;
                input.n = _n;
                Binary_Matrix matrix;
                Binary_Matrix original;
                original.set_dimension(input);
                std::vector<std::vector<bool>> _matrix(_m, std::vector<bool>(_n));
                for (int i = 0; i < _m; i++) {
                    for (int j = 0; j < _n; j++) {
                        _matrix[i][j] = uniformBooleanDistribution(gen) == 1;
                    }
                }
                original.set_matrix(_matrix);
                std::cout << "Test: " << test << ". Matrix" << std::endl;
                of_input_matrix << input << std::endl << original << std::endl;
                matrix = original;
                matrix.minimal_span_form();
                std::cout << "Test: " << test << ". Matrix. Span" << std::endl;
                matrix.calculate_active();
                std::cout << "Test: " << test << ". Matrix. Active" << std::endl;
                of_span_form_matrix << matrix << std::endl;
                Binary_Graph *graph = matrix.get_graph();
                std::cout << "Test: " << test << ". Graph. Get" << std::endl;
                of_graph << *graph << std::endl;
                std::cout << "Test: " << test << ". Graph. Cout" << std::endl;
                _successful = true;
                of_input_matrix.close();
                of_span_form_matrix.close();
                of_graph.close();
            } catch (const std::exception &e) {
                std::cout << "Failed\n";
            }

        }
    }
}

void solve() {
    // freopen("test1.test", "r", stdin);
    freopen("input.txt", "r", stdin);
    freopen("output.txt", "w", stdout);
    std::ios_base::sync_with_stdio(false);
    std::cin.tie(nullptr);
    std::cout.tie(nullptr);
    Input input{};
    Binary_Matrix matrix;
    Binary_Matrix original;
    std::cout << std::setprecision(10);
    std::cin >> input;
    original.set_dimension(input);
    std::cin >> original;
    matrix = original;
    matrix.minimal_span_form();
    matrix.calculate_active();
    Binary_Graph *graph = matrix.get_graph();
    std::vector<int> dim = graph->levels();
    for (int it: dim) {
        std::cout << it << ' ';
    }
    std::cout << '\n';
    std::string command;
    std::random_device rd{};
    std::mt19937 gen{rd()};
    std::uniform_int_distribution<std::mt19937::result_type> uniformIntDistribution(0, 1);
    std::vector<bool> sim_input_vector(input.m);
    std::vector<bool> sim_encoded(input.n);
    std::vector<double> sim_noise_encoded(input.n);
    std::vector<bool> sim_decoded(input.m);
    while (std::cin >> command) {
        if (command == "Encode") {
            std::vector<bool> input_vector(input.m);
            int x;
            for (int i = 0; i < input.m; i++) {
                std::cin >> x;
                input_vector[i] = x == 1;
            }
            write(std::cout, original.encode(input_vector));
            std::cout << '\n';
        }
        if (command == "Decode") {

            std::vector<double> input_vector(input.n);
            for (int i = 0; i < input.n; i++) {
                std::cin >> input_vector[i];
            }
            write(std::cout, graph->decode(input_vector));
            std::cout << '\n';
        }
        if (command == "Simulate") {
            int noise, iterations, max_errors;
            std::cin >> noise >> iterations >> max_errors;
            int count_wrong = 0;
            int current_iterations = iterations;
            std::normal_distribution<> normalDistribution(0, std::sqrt(
                    0.5 * pow(10, ((double) -noise) / 10) * ((double) input.n / input.m)));
            for (int iteration = 0; iteration < iterations; iteration++) {
                for (int j = 0; j < input.m; j++) {
                    sim_input_vector[j] = uniformIntDistribution(gen) == 1;
                }
                sim_encoded = original.encode(sim_input_vector);
                for (int j = 0; j < input.n; j++) {
                    sim_noise_encoded[j] = (1 - 2 * (sim_encoded[j] ? 1 : 0)) + normalDistribution(gen);
                }
                sim_decoded = graph->decode(sim_noise_encoded);
                bool isError = false;
                for (int j = 0; j < input.m; j++) {
                    if (sim_encoded[j] != sim_decoded[j]) {
                        isError = true;
                        break;
                    }
                }
                if (isError) {
                    count_wrong++;
                }
                if (count_wrong >= max_errors) {
                    current_iterations = iteration + 1;
                    break;
                }
            }
            std::cout << std::fixed << (double) count_wrong / current_iterations << '\n';

        }
    }
}

int main() {
    generator();
    return 0;
}
