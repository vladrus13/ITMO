def z3main():
    import z3
    v8 = 100
    v7 = 400
    v6 = 60
    v5 = 4
    v4 = 1
    v3 = z3.Int("v3")
    v2 = z3.Int("v2")
    v1 = z3.Int("v1")
    s = z3.Solver()
    s.add(v2 + v3 + v1 == v8)
    s.add(v3 > 0)
    s.add(v2 > 0)
    s.add(v1 > 0)
    s.add(v5 * v2 + v6 * v3 + v4 * v1 == v7)
    s.check()
    return s.model()


def main():
    import angr
    import claripy
    FILE_NAME = 'puzzle'
    FIND = (0x0804859C,)
    BAN = (0x080485AE,)
    NUMBER_SIZE = 8
    proj = angr.Project('./' + FILE_NAME, load_options={"auto_load_libs": False})

    entries = [claripy.BVS("number_%d" % i, 8 * NUMBER_SIZE) for i in range(3)]
    stdin = claripy.Concat(*entries)
    initial_state = proj.factory.entry_state(stdin=stdin)

    for entry in entries:
        for bit in entry.chop(8):
            initial_state.add_constraints(claripy.And(bit >= '0', bit <= '9'))
        initial_state.add_constraints(entry.chop(8)[7] == '\n')

    sm = proj.factory.simulation_manager(initial_state)
    import IPython
    IPython.embed()
    sm.explore(find=FIND, avoid=BAN)
    found = sm.found

    if len(found) == 0:
        return "BAN!!!"
    return found[0].posix.dumps(0)


def do_better(found, argv):
    return str(found[0].solver.eval(argv, cast_to=bytes))


if __name__ == '__main__':
    print(z3main())
