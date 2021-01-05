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


def is_variable(s: str) -> bool:
    isVariable = False
    for ch in s:
        isVariable |= ch.isalpha()
    return isVariable


def anti_polish(s: str) -> str:
    stack = []
    iterable = s.split(" ")
    for k in iterable:
        if (k == '+') | (k == '-') | (k == '*') | (k == '='):
            a = stack.pop()
            b = stack.pop()
            if k == '=':
                k = '=='
            stack.append(str('(' + a + ') ' + k + ' (' + b + ')'))
        else:
            if not is_variable(k):
                stack.append(k)
            else:
                stack.append('integers[\'' + k + '\']')
    return stack[0]


def take_variables(s: str):
    a = set()
    for it in s.split(" "):
        if is_variable(it):
            a.add(it)
    return a


def process_equations(eq: [str]) -> str:
    import z3
    variables = set()
    integers = {}
    s = z3.Solver()
    for equ in eq:
        variables = variables.union(take_variables(equ))
    for var in variables:
        integers[var] = z3.Int(var)
        s.add(integers[var] <= 256)
        # s.add(integers[var] >= 32)
    for equ in eq:
        comand = 's.add(%s)' % anti_polish(equ)
        print(comand)
        exec(comand)
    print(s)
    s.check()
    model = s.model()
    result = []
    for m in model:
        result.append([int(str(m)[1:]), int(str(model[m]))])
    result.sort(key=lambda x: x[0])
    answer = ''.join([chr(r[1]) for r in result])
    return answer
    # * eq is list of strings
    # example:
    # [
    #   "99 x1 =",
    #   "x2 2 1 * x1 - =",
    #   "x3 9487 x1 x2 * - ="
    # ]
    # write your solution here and return answer as string
    # return "cat"


def solve_remote():
    import pwn
    r = pwn.remote('109.233.56.90', 11542)
    r.recvline()
    while True:
        print()
        line = r.recvline().decode().strip()
        print(line)
        if not line.startswith("Crossword"):
            return
        count = int(line.split()[4])
        lines = []
        for _ in range(count):
            lines.append(r.recvline().decode().strip())
        print("Equations:", lines)
        result = process_equations(lines)
        print("Result:", result)
        r.sendline(result)


if __name__ == '__main__':
    # print(process_equations(["a 1 ="]))
    # print(process_equations(["a a * 4 = ", "b a * 8 =", "c a b + + 12 ="]))
    print(solve_remote())
