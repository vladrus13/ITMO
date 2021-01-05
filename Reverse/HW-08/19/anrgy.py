def z3main():
    import z3
    v1 = z3.BitVec("v1", 32)
    just = 1 ^ 4 ^ 0x40 ^ 0x400000 ^ 0x40000000
    destructor = 8 ^ 0x20 ^ 0x2000 ^ 0x80000 ^ 0x2000000
    exit_1 = 0x10 ^ 0x400 ^ 0x8000 ^ 0x20000 ^ 0x800000 ^ 0x20000000
    exit_0 = 0x80 ^ 0x10000 ^ 0x200000 ^ 0x4000000 ^ 0x10000000
    s = z3.Solver()
    s.add(v1 ^ just ^ destructor ^ exit_0 == 0x7F89409)
    s.check()
    return s.model()


if __name__ == '__main__':
    print(z3main())
