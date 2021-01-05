400526: push   rbp
400527: mov    rbp,rsp
40052a: mov    DWORD PTR [VAR_1],edi
40052d: mov    DWORD PTR [VAR_2],esi
400530: cmp    DWORD PTR [VAR_1],0x0
400534: jns    400539
400536: neg    DWORD PTR [VAR_1]
400539: cmp    DWORD PTR [VAR_2],0x0
40053d: jns    400542
40053f: neg    DWORD PTR [VAR_2]
400542: cmp    DWORD PTR [VAR_2],0x0
400546: je     400569
400549: mov    eax,DWORD PTR [VAR_1]
40054d: idiv   DWORD PTR [VAR_2]
400550: mov    DWORD PTR [VAR_1],edx
400553: cmp    DWORD PTR [VAR_1],0x0
400557: je     400569
400559: mov    eax,DWORD PTR [VAR_2]
40055d: idiv   DWORD PTR [VAR_1]
400560: mov    DWORD PTR [VAR_2],edx
400563: cmp    DWORD PTR [VAR_2],0x0
400567: jne    400549
400569: mov    edx,DWORD PTR [VAR_1]
40056c: mov    eax,DWORD PTR [VAR_2]
40056f: add    eax,edx
400571: pop    rbp
400572: ret    
