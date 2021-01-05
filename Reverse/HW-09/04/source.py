import os
flag = b'(d=\x01eq\x0c8V1\x10\x03!\x0f 6c\x19fgS\x0f8*?<=%1rfa\x00\x0f\x11;'
res = b''
c = 0
text = input('Insert flag to continue playing:\n').encode()
start = b'SPbCTF'
print('SPbCTF', end='')
for i in flag:
	print(chr(i ^ start[(c % 6)]),end='')
	c += 1
c = 0
flagd = text[:6]
for i in text[6:]:
    res += bytes([i ^ flagd[(c % 6)]])
    c += 1
else:
    if res == flag:
        print('Enjoy!')
    else:
        print('That is definitely not a flag.')
