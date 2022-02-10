//
// Created by Vladislav.Kuznetsov on 09.10.2021.
//

#ifndef INFO_CODING_POLARCODER_H
#define INFO_CODING_POLARCODER_H

#include <vector>
#include <algorithm>
#include <stack>

class PolarCode {
private:
    int m;
    int len;
    int n;
    int crc;
    std::vector<int> reverse_order;
    std::vector<std::vector<int>> matrix;

public:
    PolarCode(int step, int crc, std::vector<int> channel)
            : m(step), len(1 << (step - 1)), crc(crc), channel(channel) {
        n = (1 << step);
        frozen.resize(n, 1);
        reverse_order.resize(n);

        for (int i = 0; i < n; i++) {
            int reversed = i;
            reverse_order[i] = ((reversed & 1) << (m - 1));
            for (int j = m - 1; j; j--) {
                reversed >>= 1;
                reverse_order[i] += (reversed & 1) << (j - 1);
            }
        }

        int record_frozen = len + crc;
        for (int i = 0; i < record_frozen; i++) {
            frozen[channel[i]] = 0;
        }
        matrix.resize(crc);
        for (int bit = 0; bit < crc; bit++) {
            matrix[bit].resize(len);
            for (int info = 0; info < len; info++) {
                matrix[bit][info] = std::rand() % 2;
            }
        }
    }

    std::vector<int> encode(const std::vector<int> &info);

    std::vector<int> decode(const std::vector<double> &p0, const std::vector<double> &p1, int L);

    void initializeDataStructures();

    int LL;

    std::vector<int *> answer;
    std::stack<int> inactivePathIndices;
    std::vector<bool> activePath;
    std::vector<std::vector<double *>> arrayPointer_P;
    std::vector<std::vector<int *>> arrayPointer_C;
    std::vector<std::vector<int>> pathIndexToArrayIndex;
    std::vector<std::stack<int>> inactiveArrayIndices;
    std::vector<std::vector<int>> arrayReferenceCount;

    int assignInitialPath();

    int findMostProbablePath();

    void continuePaths_UnfrozenBit(int phi);

    void continuePaths_FrozenBit(int phi);

    void recursivelyUpdateC(int lambda, int phi);

    void killPath(int l);

    int clonePath(int l);

    void recursivelyCalcP(int lambda, int phi);

    int *getArrayPointer_C(int lambda, int l);

    double *getArrayPointer_P(int lambda, int l);

    bool pathIndexInactive(int l);

    template<class T>
    int getArrayPointer(int lambda, int l, const std::vector<std::vector<T *>> &pointer);

    void setArrayPointer_C(int *C_m, int phi, int l);

    void setArrayPointer_C(int *C_m, int phi, int l, int code);

    std::vector<int> frozen;
    std::vector<int> channel;
};

#endif //INFO_CODING_POLARCODER_H