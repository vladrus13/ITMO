def angrmain():
    import angr
    import claripy
    FILE_NAME = 'hasher1.elf'
    BAN = (0x400E36,)
    FIND = (0x401004,)
    NUMBER_SIZE = 8
    CHAR_SIZE = 8
    proj = angr.Project('./' + FILE_NAME, load_options={"auto_load_libs": False})

    input_size = 41

    argv = claripy.BVS("argv", CHAR_SIZE * input_size)

    initial_state = proj.factory.entry_state(args=['./' + FILE_NAME, argv])

    for i in range(41):
        if i % 7 == 6:
            initial_state.add_constraints(argv.chop(8)[i] == 45)
        else:
            bit = argv.chop(8)[i]
            initial_state.add_constraints(claripy.Or(claripy.And(bit > 47, bit <= 57), claripy.And(bit > 64, bit <= 90)))

    sm = proj.factory.simulation_manager(initial_state)
    sm.explore(find=FIND, avoid=BAN)
    found = sm.found

    for state in sm.deadended:
        return state.posix.dumps(1)

    if len(found) == 0:
        return "BAN!!!"
    return found[0].posix.dumps(0)


def do_better(found, argv):
    return str(found[0].solver.eval(argv, cast_to=bytes))


if __name__ == '__main__':
    print(angrmain())
