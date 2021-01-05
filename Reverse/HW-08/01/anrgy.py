import angr
import claripy


def main():
    proj = angr.Project('./angruments', load_options={"auto_load_libs": False})
    input_size = 8
    argv1 = claripy.BVS("argv", 6 * 8)
    argv2 = claripy.BVS("argv", input_size * 8)
    argv3 = claripy.BVS("argv", input_size * 8)
    initial_state = proj.factory.entry_state(args=["./angruments", argv1, argv2, argv3])

    for argv in [argv2, argv3]:
        for byte in argv.chop(8):
            initial_state.add_constraints(claripy.And(byte >= '0', byte <= '9'))

    for byte in argv1.chop(8):
        initial_state.add_constraints(claripy.Or(claripy.And(byte <= 'z', byte >= 'a'), byte == '\x00'))

    sm = proj.factory.simulation_manager(initial_state)
    import IPython
    IPython.embed()
    sm.explore(find=0x4006C8, avoid=0x4006D4)
    found = sm.found

    if len(found) == 0:
        return "BAN!!!"
    return do_better(found, argv1) + '\n' + do_better(found, argv2) + '\n' + do_better(found, argv3)


def do_better(found, argv):
    return str(found[0].solver.eval(argv, cast_to=bytes))


if __name__ == '__main__':
    print(main())
