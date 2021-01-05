import base64
flag = b'==Qf==QT==AN==gU==QO==AM==gU==AU==wX==AT==AN==Qb==gU==AM==gT==wX==AN==wX==wM==wS==QM==AT==wX==wN==wU==QV==gS==wX==wd==AM==wV==we==gR==AV==wQ==gY==AU==wU'
res = b''
# SPbCTF{W0w_JUS7_L1K3_4_N0Rm4L_PR09R4M}
print(flag[::-1])
for i in base64.b64decode(flag[::-1]):
    print(chr(i))
text = input('Please enter the flag:\n')
for i in text:
    res += base64.b64encode(i.encode())
else:
    print(flag)
    print(res[::-1])
    if flag == res[::-1]:
        print('Flag is corect')
    else:
        print('Flag is incorect')
