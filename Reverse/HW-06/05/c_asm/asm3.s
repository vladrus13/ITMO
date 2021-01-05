400526: push rbp
400527: mov  rbp,rsp
40052a: mov  DWORD PTR [rbp-0x14],edi
40052d: mov  DWORD PTR [rbp-0x18],esi
400530: mov  eax,DWORD PTR [rbp-0x14]
400533: cmp  eax,DWORD PTR [rbp-0x18]
400536: jge  400540
400538: mov  eax,DWORD PTR [rbp-0x14]
40053b: mov  DWORD PTR [rbp-0x4],eax
40053e: jmp  400546
400540: mov  eax,DWORD PTR [rbp-0x18]
400543: mov  DWORD PTR [rbp-0x4],eax
400546: mov  eax,DWORD PTR [rbp-0x4]
400549: pop  rbp
40054a: ret  


