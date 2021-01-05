def z3main():
    import z3
    a2 = []
    s = z3.Solver()
    for i in range(100):
        a2.append(z3.BitVec(str(i), 32))
        s.add(a2[i] >= 32)
        s.add(a2[i] <= 127)

    s.check()
    model = s.model()
    result = []
    for m in model:
        result.append([int(str(m)), int(str(model[m]))])
    result.sort(key=lambda x: x[0])
    answer = ''.join([chr(r[1]) for r in result])
    return answer


if __name__ == '__main__':
    print(z3main())
