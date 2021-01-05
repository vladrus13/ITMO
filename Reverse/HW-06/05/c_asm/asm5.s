400526: push rbp
400527: mov  rbp,rsp
40052a: mov  DWORD PTR [rbp-0x14],edi
40052d: mov  eax,DWORD PTR [rbp-0x14]
400531: shr  edx,0x1f
400534: add  eax,edx
400536: and  eax,0x1
400539: sub  eax,edx
40053b: mov  DWORD PTR [rbp-0x4],eax
40053e: mov  eax,DWORD PTR [rbp-0x4]
400541: test eax,eax
400543: je   40054c
400545: cmp  eax,0x1
400548: je   400553
40054a: jmp  40055a
40054c: mov  eax,0x1
400551: jmp  40055a
400553: mov  eax,0x0
400558: jmp  40055a
40055a: pop  rbp
40055b: ret  
