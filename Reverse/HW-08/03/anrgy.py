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


def angrmain():
    import angr
    import claripy
    FILE_NAME = 't1_t2_t3'
    FIND = (0x401273,)
    BAN = (0x400500,)
    NUMBER_SIZE = 8
    CHAR_SIZE = 8
    proj = angr.Project('./' + FILE_NAME, load_options={"auto_load_libs": False})

    input_size = 40

    argv = claripy.BVS("argv", CHAR_SIZE * input_size)

    initial_state = proj.factory.entry_state(stdin=argv)

    for bit in argv.chop(8):
        initial_state.add_constraints(bit >= ' ')

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
    print(angrmain())
