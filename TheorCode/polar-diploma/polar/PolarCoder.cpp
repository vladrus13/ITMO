//
// Created by Vladislav.Kuznetsov on 09.10.2021.
//

#include <cmath>
#include "PolarCoder.h"

std::vector<int> PolarCode::encode(const std::vector<int> &info) {
    std::vector<int> bits(n, 0);
    std::vector<int> returned(n);
    for (int i = 0; i < len; i++) {
        bits[channel[i]] = info[i];
    }
    for (int i = len; i < len + crc; ++i) {
        int crc_bit = 0;
        for (int j = 0; j < len; ++j) {
            crc_bit = (crc_bit + matrix[i - len][j] * info[j]) % 2;
        }
        bits[channel[i]] = crc_bit;
    }
    for (int nest = 0; nest < m; ++nest) {
        int inc = 1 << nest;
        for (int j = 0; j < inc; j++) {
            for (int i = 0; i < n; i += 2 * inc) {
                bits[i + j] = (bits[i + j] + bits[i + j + inc]) % 2;
            }
        }
    }
    for (int i = 0; i < n; ++i) {
        returned[i] = bits[reverse_order[i]];
    }
    return returned;
}

std::vector<int> PolarCode::decode(const std::vector<double> &p0, const std::vector<double> &p1, int L) {
    LL = L;
    initializeDataStructures();
    int l = assignInitialPath();
    double *P_0 = getArrayPointer_P(0, l);
    for (int beta = 0; beta < n; ++beta) {
        P_0[2 * beta] = p0[beta];
        P_0[2 * beta + 1] = p1[beta];
    }
    for (int phi = 0; phi < n; ++phi) {
        recursivelyCalcP(m, phi);
        if (frozen[phi] == 1)
            continuePaths_FrozenBit(phi);
        else
            continuePaths_UnfrozenBit(phi);
        if (phi % 2 == 1)
            recursivelyUpdateC(m, phi);
    }
    l = findMostProbablePath();
    int *C_0 = answer[l];
    std::vector<int> returned(len);
    for (int beta = 0; beta < len; ++beta)
        returned[beta] = C_0[channel[beta]];
    return returned;
}

int PolarCode::assignInitialPath() {
    int l = inactivePathIndices.top();
    inactivePathIndices.pop();
    activePath[l] = true;
    for (int lambda = 0; lambda <= m; ++lambda) {
        int s = inactiveArrayIndices[lambda].top();
        inactiveArrayIndices[lambda].pop();
        pathIndexToArrayIndex[lambda][l] = s;
        arrayReferenceCount[lambda][s] = 1;
    }
    return l;
}

template<class T>
T *copy(T *first, T *last, T *d_first) {
    while (first != last) {
        *d_first++ = *first++;
    }
    return d_first;
}

template<class T>
int PolarCode::getArrayPointer(int lambda, int l, const std::vector<std::vector<T *>> &pointer) {
    int s = pathIndexToArrayIndex[lambda][l];
    int s_p;
    if (arrayReferenceCount[lambda][s] == 1) {
        s_p = s;
    } else {
        s_p = inactiveArrayIndices[lambda].top();
        inactiveArrayIndices[lambda].pop();
        copy(pointer[lambda][s], pointer[lambda][s] + (1 << (m - lambda + 1)),
             pointer[lambda][s_p]);
        arrayReferenceCount[lambda][s]--;
        arrayReferenceCount[lambda][s_p] = 1;
        pathIndexToArrayIndex[lambda][l] = s_p;
    }
    return s_p;
}

double *PolarCode::getArrayPointer_P(int lambda, int l) {
    return arrayPointer_P[lambda][getArrayPointer(lambda, l, arrayPointer_P)];
}

int *PolarCode::getArrayPointer_C(int lambda, int l) {
    return arrayPointer_C[lambda][getArrayPointer(lambda, l, arrayPointer_C)];
}

void PolarCode::recursivelyCalcP(int lambda, int phi) {
    if (lambda == 0) {
        return;
    }
    int psi = phi >> 1;
    if (phi % 2 == 0) {
        recursivelyCalcP(lambda - 1, psi);
    }
    double s = 0.0;
    for (int l = 0; l < LL; ++l) {
        if (pathIndexInactive(l)) {
            continue;
        }
        double *P_l = getArrayPointer_P(lambda, l);
        double *P_l_1 = getArrayPointer_P(lambda - 1, l);
        int *C_l = getArrayPointer_C(lambda, l);
        for (int beta = 0; beta < (1 << (m - lambda)); ++beta) {
            double u = P_l_1[2 * (2 * beta)];
            double u1u = P_l_1[2 * (2 * beta + 1)];
            double uu1 = P_l_1[2 * (2 * beta) + 1];
            double u1u1 = P_l_1[2 * (2 * beta + 1) + 1];
            if (phi % 2 == 0) {
                P_l[2 * beta] = (u * u1u + uu1 * u1u1) / 2;
                P_l[2 * beta + 1] = (uu1 * u1u + u * u1u1) / 2;
            } else {
                int u_p = C_l[2 * beta];
                P_l[2 * beta] = P_l_1[2 * (2 * beta) + (u_p % 2)] * u1u / 2;
                P_l[2 * beta + 1] = P_l_1[2 * (2 * beta) + ((u_p + 1) % 2)] * u1u1 / 2;
            }
            s = std::max(s, P_l[2 * beta]);
            s = std::max(s, P_l[2 * beta + 1]);
        }
    }
    // s == 0?
    for (int l = 0; l < LL; ++l) {
        if (pathIndexInactive(l)) {
            continue;
        }
        double *P_l = getArrayPointer_P(lambda, l);
        for (int beta = 0; beta < (1 << (m - lambda)); ++beta) {
            for (int u = 0; u < 2; u++) {
                P_l[2 * beta + u] = P_l[2 * beta + u] / s;
            }
        }
    }
}

void PolarCode::setArrayPointer_C(int *C_m, int phi, int l, int code) {
    C_m[phi % 2] = code;
    answer[l][phi] = code;
}

void PolarCode::initializeDataStructures() {
    inactivePathIndices = std::stack<int>();
    activePath.resize(LL, false);
    arrayPointer_P.resize(m + 1, std::vector<double *>(LL));
    arrayPointer_C.resize(m + 1, std::vector<int *>(LL));
    pathIndexToArrayIndex.resize(m + 1, std::vector<int>(LL));
    inactiveArrayIndices.resize(m + 1, std::stack<int>());
    arrayReferenceCount.resize(m + 1, std::vector<int>(LL, 0));
    answer.resize(LL);
    for (int lambda = 0; lambda <= m; lambda++) {
        for (int s = 0; s < LL; s++) {
            answer[s] = new int[n]();
            arrayPointer_P[lambda][s] = new double[2 * (1 << (m - lambda))]();
            arrayPointer_C[lambda][s] = new int[2 * (1 << (m - lambda))]();
            inactiveArrayIndices[lambda].push(s);
        }
    }
    for (int l = 0; l < LL; ++l) {
        inactivePathIndices.push(l);
    }
}

int PolarCode::clonePath(int l) {
    int l_ = inactivePathIndices.top();
    inactivePathIndices.pop();
    activePath[l_] = true;
    for (int lambda = 0; lambda <= m; ++lambda) {
        int s = pathIndexToArrayIndex[lambda][l];
        pathIndexToArrayIndex[lambda][l_] = s;
        arrayReferenceCount[lambda][s]++;
    }
    return l_;
}

void PolarCode::killPath(int l) {
    activePath[l] = false;
    inactivePathIndices.push(l);
    for (int lambda = 0; lambda <= m; ++lambda) {
        int s = pathIndexToArrayIndex[lambda][l];
        arrayReferenceCount[lambda][s]--;
        if (arrayReferenceCount[lambda][s] == 0) {
            inactiveArrayIndices[lambda].push(s);
        }
    }
}

void PolarCode::recursivelyUpdateC(int lambda, int phi) {
    int psi = phi >> 1;
    for (int l = 0; l < LL; ++l) {
        if (pathIndexInactive(l))
            continue;
        int *C_l = getArrayPointer_C(lambda, l);
        int *C_l_1 = getArrayPointer_C(lambda - 1, l);
        for (int beta = 0; beta < (1 << (m - lambda)); ++beta) {
            C_l_1[2 * (2 * beta) + (psi % 2)] = (C_l[2 * beta] + C_l[2 * beta + 1]) % 2;
            C_l_1[2 * (2 * beta + 1) + (psi % 2)] = C_l[2 * beta + 1];
        }
    }
    if (psi % 2 == 1) {
        recursivelyUpdateC(lambda - 1, psi);
    }
}

void PolarCode::continuePaths_FrozenBit(int phi) {
    for (int l = 0; l < LL; ++l) {
        if (pathIndexInactive(l))
            continue;
        int *C_m = getArrayPointer_C(m, l);
        setArrayPointer_C(C_m, phi, l, 0);
    }
}

void PolarCode::continuePaths_UnfrozenBit(int phi) {
    std::vector<std::vector<double>> probForks(LL, std::vector<double>{0.0, 0.0});
    std::vector<double> largest_entities;
    int i = 0;
    for (int l = 0; l < LL; ++l) {
        if (pathIndexInactive(l)) {
            probForks[l][0] = -1;
            probForks[l][1] = -1;
        } else {
            double *p_m = getArrayPointer_P(m, l);
            probForks[l][0] = p_m[0];
            probForks[l][1] = p_m[1];
            largest_entities.push_back(probForks[l][0]);
            largest_entities.push_back(probForks[l][1]);
            i++;
        }
    }
    std::vector<std::vector<bool>> contForks(LL, std::vector<bool>{false, false});
    if (i != 0) {
        int threadhold = std::min(LL, 2 * i);
        std::sort(largest_entities.begin(), largest_entities.end(), std::greater<>());
        double ban = largest_entities[threadhold - 1];
        int ban_largest = 0;
        bool flag = true;
        for (int l = 0; l < LL && flag; ++l) {
            for (int code = 0; code < 2 && flag; code++) {
                if (probForks[l][code] > ban) {
                    contForks[l][code] = true;
                    ban_largest++;
                }
                if (ban_largest == threadhold) {
                    flag = false;
                }
            }
        }
        flag = true;
        if (ban_largest < threadhold) {
            for (int l = 0; l < LL && flag; ++l) {
                for (int code = 0; code < 2 && flag; code++) {
                    if (probForks[l][code] == ban) {
                        contForks[l][code] = true;
                        ban_largest++;
                    }
                    if (ban_largest == threadhold) {
                        flag = false;
                    }
                }
            }
        }
    }
    for (int l = 0; l < LL; ++l) {
        if (pathIndexInactive(l))
            continue;
        if (!contForks[l][0] && !contForks[l][1])
            killPath(l);
    }
    for (int l = 0; l < LL; ++l) {
        if (!contForks[l][0] && !contForks[l][1])
            continue;
        int *C_m = getArrayPointer_C(m, l);
        if (contForks[l][0] && contForks[l][1]) {
            setArrayPointer_C(C_m, phi, l, 0);
            int l_ = clonePath(l);
            C_m = getArrayPointer_C(m, l_);
            setArrayPointer_C(C_m, phi, l_, 1);
        } else {
            if (contForks[l][0]) {
                setArrayPointer_C(C_m, phi, l, 0);
            } else {
                setArrayPointer_C(C_m, phi, l, 1);
            }
        }
    }
}

bool PolarCode::pathIndexInactive(int l) {
    return !activePath[l];
}

int PolarCode::findMostProbablePath() {
    int l_ = 0;
    double p_ = 0;
    for (int l = 0; l < LL; ++l) {
        if (pathIndexInactive(l))
            continue;
        int *C_m = getArrayPointer_C(m, l);
        double *P_m = getArrayPointer_P(m, l);
        if (p_ < P_m[C_m[1]]) {
            l_ = l;
            p_ = P_m[C_m[1]];
        }
    }
    return l_;
}