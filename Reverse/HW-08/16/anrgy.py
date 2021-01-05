def z3main():
    import z3
    BASE = 32
    v10 = z3.BitVec("v10", BASE)
    v9 = z3.BitVec("v9", BASE)
    v8 = z3.BitVec("v8", BASE)
    v7 = z3.BitVec("v7", BASE)
    s = z3.Solver()
    s.add(((v10 + 35881) ^ (4286 * (4294932349 + 1167))) == -144734034)
    s.add(((4294960452 - v9) ^ (52074 * (37454 - 60157))) == 1182247522)
    s.add(((4294927469 - 44176) ^ (7606 * (-v8 + 46494))) == -3648869)
    s.add(((4294956380 + 59384) ^ (13921 * (34208 - v7))) == -414417428)
    s.check()
    return s.model()


if __name__ == '__main__':
    print(z3main())
