def angrmain():
    import angr
    import claripy
    FILE_NAME = '100libs_hurt/100libs.elf'
    FIND = ()
    BAN = ()
    NUMBER_SIZE = 8
    CHAR_SIZE = 8
    FORCE_LIBS = []
    for i in range(100):
        FORCE_LIBS.append('100libs_hurt/libs/verify' + str(i) + '.so')
    proj = angr.Project('./' + FILE_NAME, force_load_libs=FORCE_LIBS, auto_load_libs=True)
    input_size_min = 255
    input_size_max = 255

    for input_size in range(input_size_min, input_size_max + 1):
        print("test: " + str(input_size))
        argv = claripy.BVS("argv", input_size * CHAR_SIZE)

        initial_state = proj.factory.entry_state(stdin=argv)
        sm = proj.factory.simgr(initial_state)
        sm.use_technique(angr.exploration_techniques.DFS())

        for byte in argv.chop(8):
            initial_state.add_constraints(claripy.And(byte >= 32, byte <= 127))
        # import IPython
        # IPython.embed()
        sm.run()
        print(sm)
        for end in sm.unconstrained:
            s = end.solver.eval(argv, cast_to=bytes).decode('utf-8')
            print(s)
        for end in sm.deadended:
            out = end.posix.dumps(1)
            if str(out).startswith("b'YES"):
                print(out)
                print(end.posix.dumps(0))
                print("FOUND!")
                s = end.solver.eval(argv, cast_to=bytes).decode('utf-8')
                print(s)
                return s
            else:
                print(out)
    print("BAN!!!")
    return "BAN!!!"


def do_better(found, argv):
    return str(found[0].solver.eval(argv, cast_to=bytes))


if __name__ == '__main__':
    print(angrmain())
