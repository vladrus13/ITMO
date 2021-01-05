def angrmain(task: str, i: int):
    import angr
    import claripy
    FILE_NAME = task
    FIND = ()
    BAN = ()
    NUMBER_SIZE = 8
    CHAR_SIZE = 8
    proj = angr.Project('./' + FILE_NAME)
    input_size_max = 8

    for input_size in range(1, input_size_max):
        print("test: " + str(input_size))
        argv = claripy.BVS("argv", input_size * CHAR_SIZE)

        realm = argv.chop(8)[0] >= 0
        # for i in range(input_size):
        #     temp = argv.chop(8)[i] == 0
        #     for j in range(i):
        #         temp = claripy.And(claripy.And(argv.chop(8)[j] > 31, argv.chop(8)[j] <= 127), temp)
        #     for j in range(i, input_size):
        #         temp = claripy.And(argv.chop(8)[j] == 0, temp)
        #     realm = claripy.Or(temp, realm)
        for i in range(input_size):
            realm = claripy.And(realm, claripy.And(argv.chop(8)[i] > 31, argv.chop(8)[i] <= 127))

        initial_state = proj.factory.entry_state(stdin=argv)
        initial_state.add_constraints(realm)
        sm = proj.factory.simulation_manager(initial_state)
        # import IPython
        # IPython.embed()
        sm.explore()
        for end in sm.deadended:
            out = end.posix.dumps(1)
            if str(out).startswith("b'You win!"):
                print("FOUND!")
                s = end.solver.eval(argv, cast_to=bytes).decode('utf-8')
                print(s)
                return s
    print("BAN!!!")
    return "BAN!!!"


def do_better(found, argv):
    return str(found[0].solver.eval(argv, cast_to=bytes))


def solve_remote():
    import pwn
    import base64
    r = pwn.remote('109.233.56.90', 63175)
    r.recvline()
    for i in range(10):
        print()
        line = r.recvline()
        print(line.decode('utf-8'))
        line = r.recvline()
        f = open('test_' + str(i), 'wb')
        f.write(base64.decodebytes(line))
        result = angrmain('test_' + str(i), i)
        if result == "BAN!!!":
            return
        r.sendline(result)
        line = r.recvline()
        print(line.decode('utf-8'))
    line = r.recvline()
    print(line.decode('utf-8'))
    # auto{x11_aaxxa_binculated}


if __name__ == '__main__':
    print(solve_remote())
