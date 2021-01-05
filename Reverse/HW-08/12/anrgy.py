def angrmain():
    import angr
    import claripy
    FILE_NAME = 'filegr.elf'
    IN_FILE_NAME = 'home/vladkuznetsov/Vl/Projects/Reverse/HW-08/12/hehuha.txt'
    FIND = ()
    BAN = ()
    NUMBER_SIZE = 8
    CHAR_SIZE = 8
    proj = angr.Project('./' + FILE_NAME)
    input_size_min = 32
    input_size_max = 32

    for input_size in range(input_size_min, input_size_max + 1):
        print("test: " + str(input_size))
        argv = claripy.BVS("argv", input_size * CHAR_SIZE)

        file = angr.SimFile(IN_FILE_NAME, content=argv)

        initial_state = proj.factory.entry_state(args=['./' + FILE_NAME, IN_FILE_NAME])
        sm = proj.factory.simulation_manager(initial_state)
        # import IPython
        # IPython.embed()
        sm.explore()
        for end in sm.deadended:
            out = end.posix.dumps(1)
            if str(out).startswith("b'Succ"):
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
