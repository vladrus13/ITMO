def tracer():
    ROOT = '#ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/'
    ENTER_FOR_ADDR = '0x80484b1\n'
    IF_ADDR = '0x80484ba\n'
    file = open('trace.log', 'r')
    char = 0
    s = file.readline()
    while file.readable():
        while s != ENTER_FOR_ADDR:
            s = file.readline()
        char = 0
        s = file.readline()
        while s != ENTER_FOR_ADDR:
            if s == IF_ADDR:
                char += 1
            s = file.readline()
            #print(s)
        print(ROOT[char], end="")


if __name__ == '__main__':
    print(tracer())
