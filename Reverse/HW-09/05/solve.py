flag = '35x005x026x034x045x064x0b7x023x056x013x053x053x063x073x073x046x053x083x023x043x056x083x053x056x066x043x013x003x093x043x016x056x036x073x016x083x033x046x036x0d7x0'

def check(text):
    global flag
    res = ''
    superFlag = ''
    for a in flag[::-1].split('x'):
        superFlag += (chr(int(a[0:2], 16)))
    print(superFlag[::-1])
    for i in text:
        res += hex(int.from_bytes(i.encode(), 'big'))[::-1]
    else:
        print(res)
        print(flag)
        return flag == res


if __name__ == '__main__':
    flagin = input('Enter your flag: \n')
    if check(flagin):
        print('[+] You win!')
    else:
        print('[-] You lose!')
