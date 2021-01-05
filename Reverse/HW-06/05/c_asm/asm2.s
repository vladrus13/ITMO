400596: push rbp
400597: mov  rbp,rsp
40059a: mov  QWORD PTR [rbp-0x18],rdi
40059e: mov  DWORD PTR [rbp-0x1c],esi
4005a1: mov  DWORD PTR [rbp-0x20],edx
4005a4: mov  DWORD PTR [rbp-0x4],0x0
4005ab: jmp  4005d1
4005ad: mov  eax,DWORD PTR [rbp-0x4]
4005b2: lea  rdx,[rax*4+0x0]
4005b9:
4005ba: mov  rax,QWORD PTR [rbp-0x18]
4005be: add  rax,rdx
4005c1: mov  eax,DWORD PTR [rax]
4005c3: cmp  eax,DWORD PTR [rbp-0x20]
4005c6: jne  4005cd
4005c8: mov  eax,DWORD PTR [rbp-0x4]
4005cb: jmp  4005de
4005cd: add  DWORD PTR [rbp-0x4],0x1
4005d1: mov  eax,DWORD PTR [rbp-0x4]
4005d4: cmp  eax,DWORD PTR [rbp-0x1c]
4005d7: jl   4005ad
4005d9: mov  eax,0xffffffff
4005de: pop  rbp
4005df: ret  
